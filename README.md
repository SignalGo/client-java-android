[![](https://jitpack.io/v/hamedgramzi/signalgoclient.svg)](https://jitpack.io/#hamedgramzi/signalgoclient)

<h1>Setup</h1>

```gradle
allProjects 
{	
	repositories {	
		// required to find the project's artifacts
		maven { url "https://www.jitpack.io" }
	}
}
```
```gradle
dependencies {
	implementation 'com.github.hamedgramzi:signalgoclient:3.0'
}
```

<h1>How to use it</h1>
<h3> android HTTP client </h3>
1. first write a class for initial SignalGoClient Http Core

```java

public class Constants {
   
    public final static String serverUrl = "http://192.168.10.111:233/app";

    public static void initServerApi() {
        HttpCore.instance()
		// if every response from server have pattern and just different from one param you can set this 
		.setResponseClass(MyResponse.class) 
		.setCookieEnabled(true) // when your server support web cookie enable this
		// you can add deserilizer for convert data from server your class
		.addDeserializer(DateTime.class, new StdDeserializer<DateTime>((Class) null) {
                    @Override
                    public DateTime deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                        String s = (String) jsonParser.readValueAs(String.class);
                        long l = Long.parseLong(s);
                        DateTime dateTime = new DateTime().withMillis(l * 1000);
                        return dateTime;
                    }
                })
		// you can add serializer for any class what can't detect from your server like this
                .addSerializer(DateTime.class, new StdSerializer<DateTime>((Class) null) {
                    @Override
                    public void serialize(DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
                        jsonGenerator.writeString(dateTime.getMillis() / 1000 + "");
                    }
                })
		// if set this method, every call method from any where you can monitor them and do something you like
                .setMonitorableMessage(new MonitorableMessage() {

                    @Override
                    public void onMonitor(Object response, GoError[] goErrors) {
                        if (response == null) {
                            Toast.makeText(AppController.context, "server Error!!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
		    // you must overrid this method when you have not any spcefic response class
                    @Override
                    public void onServerResultWithoutResponse(Object response, GoResponseHandler responseHandler) {
                        if (response == null) {
                            responseHandler.onConnectionError();
                        } else {
                            responseHandler.onSuccess(response);
                        }
//                        if (response != null) {
//                            responseHandler.onSuccess(response);
//                        } else {
//                            responseHandler.onConnectionError();
//                        }
                    }
		    // or you must overrid this method when you haveany spcefic response class like 
                    @Override
                    public void onServerResponse(Response response, GoResponseHandler responseHandler) {
			MessageContract mc = (MessageContract) response;
                        if (mc != null) {
                            if (mc.isSuccess) {
                                responseHandler.onSuccess(((MessageContract) response).data);
                            } else if (!mc.isSuccess) {
                                responseHandler.onError(response);
                            }
                        } else {
                            responseHandler.onConnectionError();
                        }
                    }
                })
		// set server url
                .withUrl(serverUrl).init();

    }

}
```

2. add App class
```java
public class MyApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Constants.initServerApi(); // add this to your Application class
    }
}
```

3. write server methods in client

```java
public class ServerMethods {
		
    @GoMethodName(name = "/user/login", type = GoMethodName.MethodType.httpPost_json, multipartKeys = {"mac_address", "phone"})
    public static void login(String phone, String mac, GoResponseHandler<LoginResponse> goResponseHandler) {
        // you must call setTypeToken2 when you have not any sepecific responseClass
	goResponseHandler.setTypeToken2(new TypeToken<LoginResponse>() { 
        });  
	// call method -> pass params to it
        HttpCore.instance().callMethod(goResponseHandler, mac, phone);
    }

    @GoMethodName(name = "/user/confirm-phone", type = GoMethodName.MethodType.httpPost_json, multipartKeys = {"phone", "code"})
    public static void confirmPhone(String phone, String code, GoResponseHandler<VerifyResponse> goResponseHandler) {
        goResponseHandler.setTypeToken2(new TypeToken<VerifyResponse>() {
        });
        HttpCore.instance().callMethod(goResponseHandler, phone, code);
    }

    @GoMethodName(name = "/items/{type}?count={count}&offset={offset}", type = GoMethodName.MethodType.httpGet)
    public static void getLatestSoftware(GoResponseHandler<<List<TestReponse>>> goResponseHandler) {
        goResponseHandler.setTypeToken2(new TypeToken<<List<TestReponse>>>() {
        });
        HttpCore.instance().callMethod(goResponseHandler, "test",10,0);  
    }
		
    @GoMethodName(name = "/Request/FilterRequestInfoes", type = GoMethodName.MethodType.httpPost)
    public static void filterRequestInfo(FilterBaseInfo.IdFilterInfo filterBaseInfo, GoResponseHandler<List<RequestInfo>> responseHandler) {
        // use setTypeToken when we have specefic pattern for reponse and we must write response class and extend it from Response.class
	responseHandler.setTypeToken(new TypeToken<Response<List<RequestInfo>>>() {
        });
        HttpCore.instance().callMethod(responseHandler, filterBaseInfo);
    }

}
```

4. call methods every where you want like this:
```java
  ServerMethods.login(mobile, macAddress, new GoResponseHandler<LoginResponse>() {
	    @Override
	    public void onSuccess(LoginResponse loginResponse) {
		// do success
	    }

	    @Override
	    public void onError(Object response) {
		super.onError(response);
		// recieve dataa and handle every server error here
	    }

	    @Override
	    public void onConnectionError() {
		// do fail connection or timeout
	    }
        });

```
#in addition
if you have specific response class you must write like this
```java
public class MessageContract<T> extends Response<T> {
    public T data;
    public boolean isSuccess;
    public String message;
    public int errorCode;
    public String stackTrace;

}
``` 
