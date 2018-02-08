
package ir.atitec.signalgo.util;

import android.util.Log;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;

import ir.atitec.signalgo.Connector;
import ir.atitec.signalgo.Core;
import ir.atitec.signalgo.annotations.GoError;
import ir.atitec.signalgo.annotations.GoMethodName;
import ir.atitec.signalgo.models.Response;
import needle.Needle;

/**
 * Created by whiteman on 7/12/2016.
 */
public abstract class GoResponseHandler<T> {
    //    private Connector connector;
    private Core core;
    private GoMethodName goMethodName;

    private TypeToken<Response<T>> typeToken;
    private TypeToken<T> typeToken2;


    public GoResponseHandler() {
//        typeToken = new TypeToken<Response<T>>(getClass()) {};
    }


    public Core getCore() {
        return core;
    }

    public void setCore(Core core) {
        this.core = core;
    }

    //    public void postResponse(final Response<T> messageContract) {
//        Needle.onMainThread().execute(new Runnable() {
//            @Override
//            public void run() {
//                if (messageContract != null && messageContract.isSuccess) {
//                    onSuccess(messageContract.data);
//                } else if (messageContract != null && !messageContract.isSuccess) {
//                    onError(messageContract.errorCode, messageContract.message, errorMessage(goMethodName.errors(), messageContract.errorCode));
//                } else {
//                    onConnectionError();
//                }
//            }
//        });
//    }

    public void onServerResponse(Object response) {
        if (getCore().getMonitorableMessage() == null)
            onSuccess((T) response);
        else if (response instanceof Response) {
            getCore().getMonitorableMessage().onServerResponse((Response) response, this);
        } else {
            getCore().getMonitorableMessage().onServerResultWithoutResponse(response, this);
        }
    }

    public void setGoMethodName(GoMethodName goMethodName) {
        this.goMethodName = goMethodName;
    }

//    public void setConnector(Connector connector) {
//        this.connector = connector;
//    }

//    private String errorMessage(GoError[] goError, int errorCode) {
//        for (int i = 0; i < goError.length; i++) {
//            if (goError[i].errorCode() == errorCode) {
//                return goError[i].message();
//            }
//        }
//        return null;
//    }

    public Type getType() {
        if (typeToken != null) {
            if (getCore() != null && getCore().getResponseClass() != null)
                return typeToken.getSubtype(getCore().getResponseClass()).getType();
            return typeToken.getType();
        } else {
            return typeToken2.getType();
        }
    }

    public void setTypeToken(TypeToken<Response<T>> typeToken) {
//        if (getCore().getResponseClass() != null) {
//            this.typeToken = (TypeToken<Response<T>>) typeToken.getSubtype(getCore().getResponseClass());
//        } else {
        this.typeToken = typeToken;
//        }
    }

    public void setTypeToken2(TypeToken<T> typeToken2) {
        this.typeToken2 = typeToken2;
    }

    public abstract void onSuccess(T t);

    public void onError(Object response) {
        Log.d("responseHandler", response.toString());
        if (!goMethodName.doMonitor()) {
            return;
        }
        if (getCore().getMonitorableMessage() != null) {
            getCore().getMonitorableMessage().onMonitor(response, goMethodName.errors());
        }

    }

    public void onConnectionError() {
        Log.e("responseHandler", "onConnectionError");
        if (!goMethodName.doMonitor()) {
            return;
        }
        if (getCore().getMonitorableMessage() != null) {
            getCore().getMonitorableMessage().onMonitor(-1, goMethodName.errors());
        }
//        if (connector.getMonitorableErrorMessage() == null)
//            return;
//        connector.getMonitorableErrorMessage().onMonitor("خطا در ارتباط با سرور!",-1,goMethodName.printErrors());
    }


    public void onAbort() {
        Log.e("responseHandler", "onAbort");
        if (!goMethodName.doMonitor()) {
            return;
        }

        if (getCore().getMonitorableMessage() != null) {
            getCore().getMonitorableMessage().onMonitor(-2, goMethodName.errors());
        }
//        if (connector.getMonitorableErrorMessage() == null)
//            return;
//        connector.getMonitorableErrorMessage().onMonitor("کمی صبر کنید، سپس درخواستتان را مجدد ارسال کنید!",-2,goMethodName.printErrors());
    }
}
