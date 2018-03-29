package ir.atitec.signalgoApp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
//
//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.common.reflect.TypeToken;
//
//import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

import ir.atitec.signalgo.HttpCore;
import ir.atitec.signalgo.SignalGoCore;
import ir.atitec.signalgo.annotations.GoError;
import ir.atitec.signalgo.interfaces.MonitorableMessage;
import ir.atitec.signalgo.interfaces.SessionResponse;
import ir.atitec.signalgo.models.JSOGGenerator;
import ir.atitec.signalgo.models.Response;
import ir.atitec.signalgo.util.GoConvertorHelper;
import ir.atitec.signalgo.util.GoResponseHandler;
import ir.atitec.signalgo.util.SignalGoSerializer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        GoConvertorHelper goConvertorHelper = new GoConvertorHelper();
//        try {
//            String json = goConvertorHelper.serialize(new MyClass());
//            Log.e("log", json);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

//        SignalGoCore.instance()
//                .registerEmitDuplex(new TestService())
//                .registerInvokeDuplex(new TestService())
//                .setAutoConnection(true)
//                .setAutoSessionManager(new SignalGoCore.SessionManager() {
//                    @Override
//                    public void getSession(SessionResponse response) {
//
//                    }
//                })
//                .setSocketTimeOut(10000)
//                .enableNetworkObserver(this)
//                .setResponseClass(MessageContract.class)
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
//
//                    }
//
//                    @Override
//                    public void onServerResponse(Response response, GoResponseHandler responseHandler) {
//
//                    }
//                })
//                .withUrl("hello.com").init();

//
//        HttpCore.instance()
//                .setCookieEnabled(false)
//                .setResponseClass(MessageContract.class)
//                .addDeserializer(DateTime.class, new SignalGoSerializer.DateTimePHPDeserializer())
//                .addSerializer(DateTime.class, new SignalGoSerializer.DateTimePHPSerializer())
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
////                        MessageContract messageContract = (MessageContract) response;
//                    }
//                })
//                .withUrl("https://jsonplaceholder.typicode.com").init();
//
//
//        TestService.getPost(new MyClass(), new GoResponseHandler<MyClass>() {
//            @Override
//            public void onSuccess(MyClass myClass) {
//                Toast.makeText(MainActivity.this, myClass.title, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        TestService.getComments(1, new GoResponseHandler<List<TestComment>>() {
//            @Override
//            public void onSuccess(List<TestComment> testComments) {
//                Toast.makeText(MainActivity.this, testComments.size() + "", Toast.LENGTH_SHORT).show();
//            }
//        });


    }


}
