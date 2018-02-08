package ir.atitec.signalgo.interfaces;

import ir.atitec.signalgo.annotations.GoError;
import ir.atitec.signalgo.models.Response;
import ir.atitec.signalgo.util.GoResponseHandler;

/**
 * Created by hamed on 7/17/2017.
 */

public interface MonitorableMessage<T> {
    void onMonitor(Object response, GoError[] goErrors);

    void onServerResultWithoutResponse(T response, GoResponseHandler<T> responseHandler);

    void onServerResponse(Response response, GoResponseHandler<T> responseHandler);
}
