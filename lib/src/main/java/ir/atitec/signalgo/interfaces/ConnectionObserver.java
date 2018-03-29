package ir.atitec.signalgo.interfaces;


import ir.atitec.signalgo.util.GoSocketListener;

/**
 * Created by white on 2016-08-18.
 */
public interface ConnectionObserver {

    void onServerChange(GoSocketListener.SocketState lState, GoSocketListener.SocketState cState, boolean isConnected);
}
