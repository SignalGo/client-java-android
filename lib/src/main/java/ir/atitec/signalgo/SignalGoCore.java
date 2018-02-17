package ir.atitec.signalgo;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ir.atitec.signalgo.annotations.GoMethodName;
import ir.atitec.signalgo.interfaces.ConnectionObserver;
import ir.atitec.signalgo.interfaces.NetworkObserver;
import ir.atitec.signalgo.interfaces.SessionResponse;
import ir.atitec.signalgo.interfaces.ClientDuplex;
import ir.atitec.signalgo.util.GoBackStackHelper;
import ir.atitec.signalgo.util.GoResponseHandler;
import ir.atitec.signalgo.util.GoSocketListener;
import ir.atitec.signalgo.util.NetworkManager;

/**
 * Created by hamed on 12/27/2017.
 */

public class SignalGoCore extends Core implements NetworkObserver, SessionResponse {
    private final String TAG = "Core";

    //    private static SignalGoCore core;
    private ClientDuplex invokeDuplex;
    private ClientDuplex emitDuplex;
    private GoSocketListener goSocketListener;
    private Connector connector;
    private GoSocketListener.SocketState lState, cState;
    private Timer reconnectTimer;
    private ArrayList<ConnectionObserver> connectionObservers;
    private int socketTimeOut = 20000;
    private boolean hasSession = false;
    private boolean autoConnection = false;
    private SessionManager sessionManager;
    private boolean trying = false;
    public boolean hasNet = false;
    public boolean isCoreRunning = false;

    private SignalGoCore() {
        lState = null;
        cState = null;
        reconnectTimer = new Timer();
    }


    public synchronized void startSignalGo() {
        if (isCoreRunning) {
            return;
        }
        isCoreRunning = true;
        if (isConnected()) {
            return;
        }
        if (connector != null) {
            try {
                connector.forceClose();
            } catch (Exception e) {
                e.printStackTrace();
            }
            connector = null;
        }
        connector = new Connector(getGoConvertorHelper());
        connector.setTimeout(socketTimeOut);
        lState = cState = GoSocketListener.SocketState.Disconnected;
        if (invokeDuplex != null)
            connector.registerService(invokeDuplex);
        if (emitDuplex != null) {
            connector.initForCallback(emitDuplex);
        }

        connector.onSocketChangeListener(socketChangeListener());
        connector.connectAsync(getUrl());

        if (autoConnection) {
            trySchcduler();
        }
    }

    public void stopSignalGo() {
        isCoreRunning = false;
        try {
            connector.onSocketChangeListener(null);
            connector.forceClose();
            cState = null;
            lState = null;
            connector = null;
//            destroyMe(this.getClass());
            notifyConnectionObservers();
//            core = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private GoSocketListener socketChangeListener() {
        if (goSocketListener == null) {
            goSocketListener = new GoSocketListener() {
                @Override
                public void onSocketChange(SocketState socketState, SocketState socketState1) {
                    lState = socketState;
                    cState = socketState1;
                    if (cState == SocketState.Connected) {
                        Log.e(TAG, "onConnected!");
                        if (sessionManager != null) {
                            sessionManager.getSession(SignalGoCore.this);
                        } else {
                            notifyConnectionObservers();
                        }
                    } else if (cState == SocketState.Disconnected) {
                        Log.e(TAG, "onDisconnected");
                        trySchcduler();
                        hasSession = false;
                        notifyConnectionObservers();
                    }
                }

                @Override
                public void socketExeption(Exception e) {
                    e.printStackTrace();
                }
            };
        }
        return goSocketListener;
    }

    private void notifyConnectionObservers() {
        if (connectionObservers == null)
            return;
        for (int i = 0; i < connectionObservers.size(); i++) {
            connectionObservers.get(i).onServerChange(lState, cState, isConnected());
        }
    }


    public SignalGoCore registerInvokeDuplex(ClientDuplex clientDuplex) {
        invokeDuplex = clientDuplex;
        if (connector != null) {
            connector.registerService(clientDuplex);
        }
        return this;
    }

    public SignalGoCore registerEmitDuplex(ClientDuplex clientDuplex) {
        emitDuplex = clientDuplex;
        if (connector != null) {
            connector.initForCallback(clientDuplex);
        }
        return this;
    }


    public synchronized static SignalGoCore instance() {
        Core c = Core.map.get(SignalGoCore.class);
        if (c == null) {
            c = new SignalGoCore();
            Core.map.put(SignalGoCore.class, c);
        }
        return (SignalGoCore) c;
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        hasNet = isConnected;
    }

    public boolean isConnected() {
        return cState == GoSocketListener.SocketState.Connected;
    }

    public int getSocketTimeOut() {
        return socketTimeOut;
    }

    public SignalGoCore setSocketTimeOut(int socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
        return this;
    }


    public SignalGoCore setAutoSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        return this;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public SignalGoCore setAutoConnection(boolean autoConnection) {
        this.autoConnection = autoConnection;
        trySchcduler();
        return this;
    }

    public SignalGoCore enableNetworkObserver(Context context) {
        NetworkManager.init(this, context);
        return this;
    }

    public boolean isHasSession() {
        return hasSession;
    }

    public void setHasSession(boolean hasSession) {
        this.hasSession = hasSession;
    }

    public void initObserver(ConnectionObserver connectionObserver) {
        if (connectionObservers == null)
            connectionObservers = new ArrayList<>();
        if (!connectionObservers.contains(connectionObserver))
            connectionObservers.add(connectionObserver);
        connectionObserver.onServerChange(lState, cState, isConnected());
    }

    public boolean destroyObserver(ConnectionObserver connectionObserver) {
        if (connectionObservers != null) {
            return connectionObservers.remove(connectionObserver);
        }
        return false;
    }

    private synchronized void trySchcduler() {
        if (trying) {
            return;
        }
        trying = true;
        reconnectTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                trying = true;
                if (!isCoreRunning || !autoConnection) {
                    trying = false;
                    this.cancel();
                } else if (isConnected()) {
                    Log.e("Core", "tryReconnect connected ");
//                    trying = false;
//                    this.cancel();
                } else {
                    tryConnecting();
                    Log.e("Core", "tryReconnect tryyinnnnng");
                }
            }
        }, socketTimeOut + 1000, socketTimeOut);
    }


    public synchronized void tryConnecting() {
        if (!isConnected() && isCoreRunning) {
            try {
                connector.forceClose();
            } catch (Exception e) {
                e.printStackTrace();
            }
            connector = null;
            isCoreRunning = false;
            startSignalGo();
        }
    }

    @Override
    public void onSessionResponse(boolean hasSession) {
        this.hasSession = hasSession;
        notifyConnectionObservers();
    }


    public interface SessionManager {
        void getSession(SessionResponse response);
    }


    @Override
    public void init() {
        super.init();
    }

    @Override
    public void callMethod(GoResponseHandler responseHandler, Object... params) {
        responseHandler.setCore(this);
        try {
            GoMethodName methodName = GoBackStackHelper.getMethodName();
            if (methodName.type().getId() > GoMethodName.MethodType.emit.getId()) {
                throw new RuntimeException("method type is wrong");
            }
            responseHandler.setGoMethodName(methodName);
            if (connector != null)
                connector.autoInvokeAsync(methodName, responseHandler, params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("can't find GoMethodName Annotaion on your method");
        }
    }


}
