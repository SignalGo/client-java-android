package ir.atitec.signalgoApp;

import android.content.Context;

import ir.atitec.signalgo.Connector;
import ir.atitec.signalgo.util.GoSocketListener;

/**
 * Created by mehdi akbarian on 2016-08-06.
 */
public class Main {
    public static Connector connector;
    static boolean a = true;
    public static TestService service;
//    public static void Main(String[] args) {
//        start();
//    }


    public static void start(Context context){
        if(connector != null)
            return;

        connector=new Connector();
        service = new TestService(context);
        connector.registerService(service);

        connector.setTimeout(20000);
        connector.connectAsync("http://offsee.org:4710/OffSeeServices/SignalGo");
//        service.hello();
        connector.onSocketChangeListener(new GoSocketListener() {
            public void onSocketChange(GoSocketListener.SocketState lastState, GoSocketListener.SocketState currentState) {
                if(lastState==SocketState.Disconnected && currentState==SocketState.Connected){
                    //for(int i=0;i<100;i++){
                    //service.hello();
                    //System.err.println(""+i);
                    //}
                    //a=false;
                }
            }
            public void socketExeption(Exception e) {
                e.printStackTrace();
            }
        });

    }

    public static void doHello(){
        service.hello2();
    }

    public static void stop(){
//        service.hello2();
        try {
            connector.forceClose();
            connector = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
