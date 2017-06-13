package ir.atitec.lib.models;

/**
 * Created by mehdi akbarian on 2016-08-06.
 */
public enum GoDataType {
    UnKown(0),CallMethod(1),ResponseCallMethod(2),File(3),Ping_Pong(5);

    private final int id;
    GoDataType(int id) {
        this.id=id;
    }
    public int getValue(){
        return id;
    }

    public static GoDataType getInstance(int type){
        switch (type){
            case 0:
                return UnKown;
            case 1:
                return CallMethod;
            case 2:
                return ResponseCallMethod;
            case 3:
                return File;
            case 5:
                return Ping_Pong;
            default:
                return UnKown;
        }
    }
}
