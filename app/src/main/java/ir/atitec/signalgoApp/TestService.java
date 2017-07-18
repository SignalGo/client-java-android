/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.atitec.signalgoApp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import ir.atitec.signalgo.models.MonitorableErrorMessage;
import ir.atitec.signalgo.util.ClientDuplex;
import ir.atitec.signalgo.Connector;
import ir.atitec.signalgo.util.GoResponseHandler;
import ir.atitec.signalgo.annotations.GoError;
import ir.atitec.signalgo.annotations.GoMethodName;
import ir.atitec.signalgo.annotations.GoServiceName;

/**
 * @author mehdi
 */
@GoServiceName(name = "TransportService", type = GoServiceName.GoClientType.Java, usage = GoServiceName.GoUsageType.both)
public class TestService implements ClientDuplex {
    static int a = 0;
    Context context;

    public TestService(final Context context) {
//        super(connector);
        this.context = context;
        Main.connector.setMonitorableErrorMessage(new MonitorableErrorMessage() {
            @Override
            public void onMonitor(String message,int errorCode,boolean b) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                if(errorCode == 0){
                    hello();
                }
            }
        });
    }

    @GoMethodName(name = "LoginPassenger", type = GoMethodName.MethodType.invoke,priority = GoMethodName.PriorityType.veryHigh,
            errors = {@GoError(errorCode = 34, message = "نام کاربری و یا کلمه عبور اشتباه است!"), @GoError(errorCode = 10, message = "hello")}
    )
    public void hello() {
        GoResponseHandler goResponseHandler = new GoResponseHandler<PassengerInfo>() {

            @Override
            public void onSuccess(PassengerInfo p) {
                Log.e("loginPassenger", "success");
            }
        };
//        goResponseHandler.setTypeToken(new TypeToken<PassengerInfo>(PassengerInfo.class) {
//        });
        Main.connector.autoInvokeAsync(goResponseHandler, "09354218678", "4af47d46-6bc4-4996-985c-0604e69af943");
    }

    @Override
    public void getConnector(Connector c) {

    }

    @GoMethodName(name = "CancelService", type = GoMethodName.MethodType.invoke, priority = GoMethodName.PriorityType.high,
            errors = {@GoError(errorCode = 30, message = "کلید وارد شده نامعتبر است"), @GoError(errorCode = 0, message = "hello")}
    )
    public void hello2() {
        Main.connector.autoInvokeAsync(new GoResponseHandler<Object>() {

            @Override
            public void onSuccess(Object o) {

            }
        }, 2);
    }

//    @GoMethodName(name = "GetUserName", type = GoMethodName.MethodType.emit)
//    public String bye() {
//        System.err.println("hhhhhhh");
//        //hello();
//        return "bye " + "mehdi";
//    }

//    @GoMethodName(name = "GetData",type = GoMethodName.MethodType.invoke)
//    public void getData(DateTime dateTime){
//        System.err.println("time = " + dateTime);
//        connector.autoInvokeAsync(new GoResponseHandler<MyClass>() {
//            @Override
//            public void onResponse(MyClass t) {
//                System.out.println("myClass :  "+t.dateTime);
//            }
//        }, dateTime);
//    }


//    void destroy() {
//        connector.destroyCallBack(this);
//    }
//
//
//    public void getConnector(Connector c) {
//        connector = c;
//
//    }


}



