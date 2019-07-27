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

//    @GoMethodName(name = "Test", type = GoMethodName.MethodType.invoke)
//    public static void test(GoResponseHandler<String> goResponseHandler) {
//        goResponseHandler.setTypeToken2(new TypeToken<String>() {
//        });
//        SignalGoCore.instance().callMethod(goResponseHandler);
//    }

    @GoMethodName(name = "/{type}/recommendation/launcher?count={count}&offset={offset}", type = GoMethodName.MethodType.httpGet, multipartKeys = {"mac_address", "phone", "device"})
    public static void login(GoResponseHandler<Object> goResponseHandler) {
        goResponseHandler.setTypeToken2(new TypeToken<Object>() {
        });
//        goResponseHandler.addHeader(new GoHeader("Adgdfg", "ASdfdg"));
        HttpCore.instance().callMethod(goResponseHandler, "99:00:23:23:64:34", "0934323433", "tv");
    }

    @GoMethodName(name = "/user/tag/follow", serverUrl = "http://185.105.239.40:4949/app", type = GoMethodName.MethodType.httpPost_json, multipartKeys = {"tag"})
    public static void followTag(GoResponseHandler<Object> goResponseHandler) {
        goResponseHandler.setTypeToken2(new TypeToken<Object>() {
        });
        goResponseHandler.addHeader(new GoHeader("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJob3N0bmFtZSI6ImFwaS5odW1hLmlyIiwidXNlcl9pZCI6NTEyNTYsInVzZXJfcHJpdmlsZWdlIjoidXNlciIsImRldmljZSI6InR2IiwiZGV2aWNlX2lkIjoyMn0.v08fmyLHA_XCrcIaY2ooigtmu9R6fysXKgRoAWur7jc"));

        HttpCore.instance().callMethod(goResponseHandler, "سیاسی");
    }

    @GoMethodName(name = "/api/Token", serverUrl = "http://52.72.248.62/MealsyIdentityServices", type = GoMethodName.MethodType.httpPost_json, multipartKeys = {"username", "password"})
    public static void getToken(String username, String password, GoResponseHandler<Object> responseHandler) {
        responseHandler.setTypeToken2(new TypeToken<Object>() {
        });

        HttpCore.instance().callMethod(responseHandler, username, password);
    }


    @GoMethodName(name = "/type/{recommendation}/launcher?count={count}&offset={offset}", type = GoMethodName.MethodType.httpGet)
    public static void testNull(GoResponseHandler<Object> goResponseHandler) {
        goResponseHandler.setTypeToken2(new TypeToken<Object>() {
        });
//        goResponseHandler.addHeader(new GoHeader("Adgdfg", "ASdfdg"));
        HttpCore.instance().callMethod(goResponseHandler, null, 10, null);
    }


    @GoMethodName(name = "/Authentication/TestUtf8", serverUrl = "http://dev.atitec.ir:6578", type = GoMethodName.MethodType.httpPost_formData,multipartKeys = {"userInfo"})
    public static void test(UserInfo userInfo, GoResponseHandler<TestService.UserInfo> goResponseHandler) {
        goResponseHandler.setTypeToken2(new TypeToken<TestService.UserInfo>() {
        });
//        goResponseHandler.addHeader(new GoHeader("Authorization", SharedPreferencesHelper.getToken()));
        HttpCore.instance().callMethod(goResponseHandler, userInfo);
    }

    public static class UserInfo {
        public String username;

        public UserInfo() {
        }

        public UserInfo(String username) {
            this.username = username;
        }
    }

}



