package ir.atitec.signalgo;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import org.joda.time.DateTimeZone;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import ir.atitec.signalgo.annotations.GoHeader;
import ir.atitec.signalgo.annotations.GoMethodName;
import ir.atitec.signalgo.interfaces.MonitorableMessage;
import ir.atitec.signalgo.models.Response;
import ir.atitec.signalgo.util.GoBackStackHelper;
import ir.atitec.signalgo.util.GoConvertorHelper;
import ir.atitec.signalgo.util.GoResponseHandler;

/**
 * Created by hamed on 12/13/2017.
 */

public class HttpCore extends Core {

    private boolean cookieEnabled = false;
    private RestTemplate restTemplate;
    private List<String> cookie;


    private HttpCore() {


//
//        objectMapper = new ObjectMapper();
//        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, true);
//        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
//        objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        //mapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.setTimeZone(DateTimeZone.getDefault().toTimeZone());

    }

    public synchronized static HttpCore instance() {
        Core c = Core.map.get(HttpCore.class);
        if (c == null) {
            c = new HttpCore();
            Core.map.put(HttpCore.class, c);
        }
        return (HttpCore) c;
    }


//    private void post(String url, GoResponseHandler responseHandler, Object... params) {
//
//        if (Build.VERSION.SDK_INT > 16)
//            new MyAsync(getUrl() + url, responseHandler, HttpMethod.POST).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
//        else
//            new MyAsync(getUrl() + url, responseHandler, HttpMethod.POST).execute(params);
//    }

    private void send(String url, String[] keys, GoResponseHandler responseHandler, GoMethodName.MethodType methodType, Object... params) {
        String link = responseHandler.getGoMethodName().serverUrl().equals("") ? getUrl() + url : responseHandler.getGoMethodName().serverUrl() + url;
        if (Build.VERSION.SDK_INT > 16)
            new MyAsync(link, responseHandler, methodType).setKeys(keys).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        else
            new MyAsync(link, responseHandler, methodType).setKeys(keys).execute(params);
    }

//    private void get(String url, GoResponseHandler responseHandler) {
//        if (Build.VERSION.SDK_INT > 16)
//            new MyAsync(getUrl() + url, responseHandler, HttpMethod.GET).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        else
//            new MyAsync(getUrl() + url, responseHandler, HttpMethod.GET).execute();
//    }

//    private void uploadFile(String url, GoResponseHandler responseHandler, File file) {
//        if (Build.VERSION.SDK_INT > 16)
//            new MyAsync(getUrl() + url, responseHandler, HttpMethod.POST).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, file);
//        else
//            new MyAsync(getUrl() + url, responseHandler, HttpMethod.POST).execute(file);
//    }


    private GoMethodName findMethod(GoResponseHandler responseHandler) {
        try {
            GoMethodName methodName = GoBackStackHelper.getHttpMethodName();
            responseHandler.setCore(this);
            responseHandler.setGoMethodName(methodName);
            return methodName;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class MyAsync extends AsyncTask<Object, Void, Object> {
        GoResponseHandler responseHandler;
        String url;
        HttpMethod httpMethod;
        GoMethodName.MethodType methodType;
        String[] keys;

        public MyAsync(String url, GoResponseHandler responseHandler, GoMethodName.MethodType methodType) {
            this.responseHandler = responseHandler;
            this.url = url;
            this.methodType = methodType;
            setHttpMethod(methodType);
        }

        public MyAsync setKeys(String[] keys) {
            this.keys = keys;
            return this;
        }

        private void setHttpMethod(GoMethodName.MethodType methodType) {
            if (methodType.getId() == GoMethodName.MethodType.httpGet.getId()) {
                httpMethod = HttpMethod.GET;
            } else {
                httpMethod = HttpMethod.POST;
            }
        }

        @Override
        protected Object doInBackground(Object... objects) {
            try {

                ResponseEntity responseEntity =
                        restTemplate.exchange(url, httpMethod, getEntity(objects, keys, responseHandler.getGoHeaders()), String.class);
                if (responseEntity.getStatusCode() != HttpStatus.OK) {
                    return responseEntity;
                }
                if (responseHandler.getType() == null) {
                    return responseEntity;
                }
                Object response = getGoConvertorHelper().deserialize((String) responseEntity.getBody(), getObjectMapper().constructType(responseHandler.getType()));
                if (cookieEnabled) {
                    Object o = responseEntity.getHeaders().get("Set-Cookie");
                    if (o != null) {
                        cookie = (List<String>) o;
                    }
                }
                Log.d("HttpCore", url + "  " + response);
                //if (response != null)
                //  Log.e("Core", "response : " + url + "  " + response.message + " " + response.stack);
                return response;
            } catch (Exception e) {
                Log.e("Core", "exception : " + url + "  " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object response) {
            super.onPostExecute(response);
            responseHandler.onServerResponse(response);
        }

        private HttpEntity getEntity(Object[] objects, String[] keys, List<GoHeader> goHeaders) {
            HttpHeaders httpHeaders = new HttpHeaders();
            for (int i = 0; i < goHeaders.size(); i++) {
                httpHeaders.add(goHeaders.get(i).header, goHeaders.get(i).value);
            }
            if (cookie != null && cookieEnabled) {
                httpHeaders.put("Cookie", cookie);
            }
            HttpEntity httpEntity = null;
            if (objects != null && objects.length > 0) {
                if (methodType == GoMethodName.MethodType.httpUploadFile) {
                    httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                    LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
                    FileSystemResource value = new FileSystemResource((File) objects[0]);
                    map.add("file", value);
                    httpEntity = new HttpEntity(map, httpHeaders);
                } else if (methodType == GoMethodName.MethodType.httpPost_formData) {
                    httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                    LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
                    for (int i = 0; i < keys.length; i++) {
                        if (objects[i] != null) {
                            try {
                                String str = "";
                                if (objects[i] instanceof String) {
                                    str = (String) objects[i];
                                } else {
                                    str = getObjectMapper().writeValueAsString(objects[i]);
                                }
                                map.add(keys[i], str);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    httpEntity = new HttpEntity(map, httpHeaders);
                } else if (methodType == GoMethodName.MethodType.httpPost) {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpEntity = new HttpEntity(objects[0], httpHeaders);
                } else if (methodType == GoMethodName.MethodType.httpPost_json) {
                    if (keys != null) {
                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                        JSONObject jsonObject = new JSONObject();
                        for (int i = 0; i < keys.length; i++) {
                            try {
                                jsonObject.put(keys[i], objects[i]);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        httpEntity = new HttpEntity(jsonObject.toString(), httpHeaders);
                    }
                }
            } else {
                httpEntity = new HttpEntity(httpHeaders);
            }
            return httpEntity;
        }
    }


    public boolean isCookieEnabled() {
        return cookieEnabled;
    }

    public Core setCookieEnabled(boolean cookieEnabled) {
        this.cookieEnabled = cookieEnabled;
        return this;
    }

    @Override
    public void init() {
        super.init();
        restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter m = new MappingJackson2HttpMessageConverter();
        m.setObjectMapper(getObjectMapper());
        FormHttpMessageConverter form = new FormHttpMessageConverter();
        form.addPartConverter(m);
        restTemplate.getMessageConverters().add(form);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(m);
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                Log.d("Core", response.toString());
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                Log.d("Core", response.toString());
            }
        });
    }

    @Override
    public void callMethod(GoResponseHandler responseHandler, Object... params) {
        GoMethodName methodName = findMethod(responseHandler);
        responseHandler.setGoMethodName(methodName);
        if (methodName == null) {
            throw new RuntimeException("can't find Annotaion GoMethodName on method");
        }
        if (methodName.type().getId() < GoMethodName.MethodType.httpGet.getId()) {
            throw new RuntimeException("method type is wrong");
        }
        int i = 0;
        String url = methodName.name();
        int index = 0, index2 = 0;
        do {
            index = url.indexOf("{", index + 1);
            index2 = url.indexOf("}", index2 + 1);
            if (index == -1 || index2 == -1) {
                break;
            }

            try {
                String str;
                if (params[i] instanceof String) {
                    str = (String) params[i];
                } else {
                    str = getObjectMapper().writeValueAsString(params[i]);
                }
                url = url.replace("{" + url.substring(index + 1, index2) + "}", str + "");
                int x = 0;
                if ((x = str.indexOf("}")) != -1) {
                    index += x;
                    index2 += x;
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            i++;
        } while (true);

        Object[] pa = {};

        if (params.length > i) {
            pa = new Object[params.length - i];
            for (int j = i; j < params.length; j++) {
                pa[j - i] = params[j];
            }
        }

        if (methodName.type().getId() == GoMethodName.MethodType.httpGet.getId()) {
            send(url, null, responseHandler, methodName.type());
        } else if (methodName.type().getId() == GoMethodName.MethodType.httpPost.getId()) {
            if (pa.length == 1)
                send(url, null, responseHandler, methodName.type(), pa);
            else
                throw new RuntimeException("if you have more than one param, you must choose jsonPost or formDataPost");
        } else if (methodName.type().getId() == GoMethodName.MethodType.httpPost_json.getId()) {
            send(url, methodName.multipartKeys(), responseHandler, methodName.type(), pa);
        } else if (methodName.type().getId() == GoMethodName.MethodType.httpPost_formData.getId()) {
            send(url, methodName.multipartKeys(), responseHandler, methodName.type(), pa);
        } else if (methodName.type().getId() == GoMethodName.MethodType.httpUploadFile.getId()) {
            if (params.length == 1) {
                File f = (File) params[i];
                send(url, null, responseHandler, methodName.type(), f);
            } else {
                throw new RuntimeException("upload file must have just one param for post and other param must send with GET!!");
            }
        }
    }


}
