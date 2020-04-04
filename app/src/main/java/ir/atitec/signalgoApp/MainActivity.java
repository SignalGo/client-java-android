package ir.atitec.signalgoApp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
//
//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.common.reflect.TypeToken;
//
//import org.joda.time.DateTime;

import ir.atitec.signalgo.HttpCore;
import ir.atitec.signalgo.annotations.GoError;
import ir.atitec.signalgo.interfaces.MonitorableMessage;
import ir.atitec.signalgo.models.Response;
import ir.atitec.signalgo.util.GoResponseHandler;

public class MainActivity extends AppCompatActivity {

    public String str = "{\"$id\":\"1\",\"Data\":[{\"$id\":\"2\",\"Id\":20,\"Title\":\"رخرهزهرخررخ\",\"Description\":\"خلخلخرخد ا ا ا ع هر ت ت  هرر\",\"Status\":2,\"CreatedDateTime\":\"2018-04-22T14:11:14.0072489\",\"ConfirmedDateTime\":\"2018-04-22T14:16:38.150459\",\"PriceType\":1,\"UserPrice\":20000,\"UserId\":19,\"RegionId\":2,\"AdminDescription\":\"dsfgnjmhgdfgsf\",\"IsImmediate\":false,\"RegionInfo\":{\"$id\":\"3\",\"Id\":2,\"Name\":\"مشهد\"},\"FileInfoes\":[{\"$id\":\"4\",\"Id\":7,\"FileName\":\"cropped1858790963.jpg\",\"PostId\":20,\"CreatedDateTime\":\"2018-04-22T14:11:14.8662973\",\"PostInfo\":{\"$ref\":\"2\"}}]},{\"$id\":\"5\",\"Id\":19,\"Title\":\"hbh\",\"Description\":\"tccrcrcrcr\",\"Status\":2,\"CreatedDateTime\":\"2018-04-15T14:32:50.1217229\",\"ConfirmedDateTime\":\"2018-04-15T14:32:50.1217229\",\"PriceType\":1,\"UserPrice\":3500,\"UserId\":19,\"RegionId\":2,\"IsImmediate\":false,\"RegionInfo\":{\"$ref\":\"3\"},\"FileInfoes\":[{\"$id\":\"6\",\"Id\":5,\"FileName\":\"cropped404233259.jpg\",\"PostId\":19,\"CreatedDateTime\":\"2018-04-15T14:32:50.9707676\",\"PostInfo\":{\"$ref\":\"5\"}},{\"$id\":\"7\",\"Id\":6,\"FileName\":\"cropped652627641.jpg\",\"PostId\":19,\"CreatedDateTime\":\"2018-04-15T14:33:02.2003925\",\"PostInfo\":{\"$ref\":\"5\"}}]},{\"$id\":\"8\",\"Id\":2,\"Title\":\"تست\",\"Description\":\"یسینبسیب شنیس\",\"Status\":2,\"CreatedDateTime\":\"2018-04-08T12:44:25.9148661\",\"ConfirmedDateTime\":\"2018-04-08T12:44:25.9148661\",\"PriceType\":0,\"UserPrice\":3000,\"UserId\":3,\"RegionId\":1,\"IsImmediate\":false,\"RegionInfo\":{\"$id\":\"9\",\"Id\":1,\"Name\":\"تلور\"},\"FileInfoes\":[]},{\"$id\":\"10\",\"Id\":1,\"Title\":\"test\",\"Description\":\"sioaihod\",\"Status\":2,\"CreatedDateTime\":\"2018-04-08T12:43:39.5313528\",\"PriceType\":0,\"UserPrice\":3000,\"UserId\":3,\"RegionId\":1,\"IsImmediate\":false,\"RegionInfo\":{\"$ref\":\"9\"},\"FileInfoes\":[{\"$id\":\"11\",\"Id\":1,\"FileName\":\"0-images-about-doctor-tools-clip-art-on-vector-2.jpg\",\"PostId\":1,\"CreatedDateTime\":\"2018-04-11T09:37:28.8680552\",\"PostInfo\":{\"$ref\":\"10\"}}]},{\"$id\":\"12\",\"Id\":3,\"Title\":\"تست۲\",\"Description\":\"یسینبسیب شنیس۳۴۳\",\"Status\":2,\"CreatedDateTime\":\"2018-04-08T12:44:31.6461702\",\"PriceType\":0,\"UserPrice\":3000,\"UserId\":3,\"RegionId\":1,\"IsImmediate\":false,\"RegionInfo\":{\"$ref\":\"9\"},\"FileInfoes\":[]}],\"IsSuccess\":true,\"ErrorCode\":0}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        GoConvertorHelper goConvertorHelper = new GoConvertorHelper();
//        try {
//            String json = goConvertorHelper.serialize(new MyClass());
//            Log.e("log", json);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

//        SignalGoCore.instance()
//                .setAutoConnection(true)
//                .setAutoSessionManager(new SignalGoCore.SessionManager() {
//                    @Override
//                    public void getSession(SessionResponse response) {
//
//                    }
//                })
//                .setSocketTimeOut(10000)
//                .enableNetworkObserver(this)
//
//                .addDeserializer(DateTime.class, new SignalGoSerializer.DateTimeDeserializer())
//                .addSerializer(DateTime.class, new SignalGoSerializer.DateTimeSerializer())
//                .setMonitorableMessage(new MonitorableMessage() {
//
//                    @Override
//                    public void onMonitor(Object response, GoError[] goErrors) {
//
//                    }
//
//                    @Override
//                    public void onServerResultWithoutResponse(Object response, GoResponseHandler responseHandler) {
//                        if (response != null) {
//                            responseHandler.onSuccess(response);
//                        } else {
//                            responseHandler.onConnectionError();
//                        }
//                    }
//
//                    @Override
//                    public void onServerResponse(Response response, GoResponseHandler responseHandler) {
//
//                    }
//                })
//                .withUrl("http://192.168.10.65:3284/TestServices/SignalGo").init();
//        SignalGoCore.instance().startSignalGo();
//        SignalGoCore.instance().initObserver(new ConnectionObserver() {
//            @Override
//            public void onServerChange(GoSocketListener.SocketState lState, GoSocketListener.SocketState cState, boolean isConnected) {
//                Toast.makeText(MainActivity.this, "state " + isConnected, Toast.LENGTH_SHORT).show();
//            }
//        });


//
        HttpCore.instance()
                .setMonitorableMessage(new MonitorableMessage() {

                    @Override
                    public void onMonitor(Object response, GoError[] goErrors) {

                    }

                    @Override
                    public void onServerResultWithoutResponse(Object response, GoResponseHandler responseHandler) {
                        if (response != null) {
                            responseHandler.onSuccess(response);
                        } else {
                            responseHandler.onConnectionError();
                        }
                    }

                    @Override
                    public void onServerResponse(Response response, GoResponseHandler responseHandler) {
//                        MessageContract messageContract = (MessageContract) response;
                    }
                })
                .withUrl("http://185.105.239.40:4949/app").init();

//        TestService.getToken("username", "password", new GoResponseHandler<Object>() {
//            @Override
//            public void onSuccess(Object o) {
//
//            }
//        });
        TestService.test(new TestService.UserInfo("علی"), new GoResponseHandler<TestService.UserInfo>() {
            @Override
            public void onSuccess(TestService.UserInfo userInfo) {
                Toast.makeText(MainActivity.this, userInfo.username, Toast.LENGTH_SHORT).show();
            }
        });

//
//        TestService.getPost(new MyClass(), new GoResponseHandler<MyClass>() {
//            @Override
//            public void onSuccess(MyClass myClass) {
//                Toast.makeText(MainActivity.this, myClass.title, Toast.LENGTH_SHORT).show();
//            }
//        });

//        TestService.login(new GoResponseHandler<Object>() {
//            @Override
//            public void onSuccess(Object o) {
////                Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        TestService.followTag(new GoResponseHandler<Object>() {
//            @Override
//            public void onSuccess(Object o) {
//                Toast.makeText(MainActivity.this, "hello!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    public void onButtonClick(View view) {
        TestService.hello("hamed", "1234", new GoResponseHandler<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                Toast.makeText(MainActivity.this, "response :" + aBoolean, Toast.LENGTH_SHORT).show();
//                TestService.test(new GoResponseHandler<String>() {
//                    @Override
//                    public void onSuccess(String s) {
//                        Toast.makeText(MainActivity.this, "response2 : " + s, Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });
    }

    @Override
    protected void onDestroy() {
//        SignalGoCore.instance().stopSignalGo();
        super.onDestroy();

    }
}
