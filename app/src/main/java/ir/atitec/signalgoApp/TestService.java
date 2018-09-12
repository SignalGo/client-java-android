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
import ir.atitec.signalgo.annotations.GoHeader;
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
@GoServiceName(name = "healthfamilyserviceserverservice", usage = GoServiceName.GoUsageType.emit)
public class TestService implements ClientDuplex {
    final static String str = "";

    public TestService() {
    }

    @GoMethodName(name = "HelloWorld", type = GoMethodName.MethodType.invoke, priority = GoMethodName.PriorityType.veryHigh)
    public static void hello(String username, String password, GoResponseHandler<Boolean> goResponseHandler) {
        goResponseHandler.setTypeToken2(new TypeToken<Boolean>() {
        });
        SignalGoCore.instance().callMethod(goResponseHandler, username, password);
    }

    @GoMethodName(name = "Test", type = GoMethodName.MethodType.invoke)
    public static void test(GoResponseHandler<String> goResponseHandler) {
        goResponseHandler.setTypeToken2(new TypeToken<String>() {
        });
        SignalGoCore.instance().callMethod(goResponseHandler);
    }

    @GoMethodName(name = "/{type}/recommendation/launcher?count={count}&offset={offset}", type = GoMethodName.MethodType.httpGet, multipartKeys = {"mac_address", "phone", "device"})
    public static void login(GoResponseHandler<Object> goResponseHandler) {
        goResponseHandler.setTypeToken2(new TypeToken<Object>() {
        });
//        goResponseHandler.addHeader(new GoHeader("Adgdfg", "ASdfdg"));
        HttpCore.instance().callMethod(goResponseHandler, "99:00:23:23:64:34", "0934323433", "tv");
    }

}



