package ir.atitec.signalgo.models;

/**
 * Created by hamed on 7/17/2017.
 */

public interface MonitorableErrorMessage {
    void onMonitor(String message,int errorCode);
}
