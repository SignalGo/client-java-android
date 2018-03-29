/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.atitec.signalgoApp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.common.reflect.TypeToken;

import java.util.List;

import ir.atitec.signalgo.HttpCore;
import ir.atitec.signalgo.SignalGoCore;
import ir.atitec.signalgo.interfaces.ClientDuplex;
import ir.atitec.signalgo.Connector;
import ir.atitec.signalgo.models.Response;
import ir.atitec.signalgo.util.GoResponseHandler;
import ir.atitec.signalgo.annotations.GoError;
import ir.atitec.signalgo.annotations.GoMethodName;
import ir.atitec.signalgo.annotations.GoServiceName;

/**
 * @author mehdi
 */
@GoServiceName(name = "TransportService", usage = GoServiceName.GoUsageType.emit)
public class TestService implements ClientDuplex {

    public TestService() {
    }

    @GoMethodName(name = "LoginPassenger", type = GoMethodName.MethodType.invoke, priority = GoMethodName.PriorityType.veryHigh,
            errors = {@GoError(errorCode = 34, message = "نام کاربری و یا کلمه عبور اشتباه است!"), @GoError(errorCode = 10, message = "hello")}
    )
    public void hello(String username, String password, GoResponseHandler<List<MyClass>> goResponseHandler) {

        goResponseHandler.setTypeToken(new TypeToken<Response<List<MyClass>>>() {
        });
        SignalGoCore.instance().callMethod(goResponseHandler, username, password);
    }

    @GoMethodName(name = "/posts/{index}", type = GoMethodName.MethodType.httpGet)
    public static void getPost(MyClass index, GoResponseHandler<MyClass> goResponseHandler) {
        goResponseHandler.setTypeToken2(new TypeToken<MyClass>() {});
        HttpCore.instance().callMethod(goResponseHandler, 2);
    }

    @GoMethodName(name = "/comments?postId={postId}", type = GoMethodName.MethodType.httpGet)
    public static void getComments(int postId, GoResponseHandler<List<TestComment>> goResponseHandler) {
        goResponseHandler.setTypeToken2(new TypeToken<List<TestComment>>() {
        });
        HttpCore.instance().callMethod(goResponseHandler, postId);
    }

}



