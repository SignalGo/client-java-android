package ir.atitec.signalgo;

import android.util.Pair;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.atitec.signalgo.interfaces.MonitorableMessage;
import ir.atitec.signalgo.models.Response;
import ir.atitec.signalgo.util.GoConvertorHelper;
import ir.atitec.signalgo.util.GoResponseHandler;

/**
 * Created by hamed on 2/6/2018.
 */

public abstract class Core {
    private SimpleModule module = new SimpleModule();
    private ObjectMapper objectMapper;
    private GoConvertorHelper goConvertorHelper;
    protected static HashMap<Class<? extends Core>, Core> map = new HashMap<>();
    private String url;

    public synchronized static Core instance(Class<? extends Core> cls) {
        Core c;
        if ((c = map.get(cls)) == null) {
            try {
                c = (Core) cls.getMethod("instance").invoke(null);
                map.put(cls, c);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return c;
    }

    private Class<? extends Response> responseClass;
    private MonitorableMessage monitorableMessage;

    protected Core() {

    }


    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public GoConvertorHelper getGoConvertorHelper() {
        return goConvertorHelper;
    }

    public Class<? extends Response> getResponseClass() {
        return responseClass;
    }

    public Core setResponseClass(Class<? extends Response> responseClass) {
        this.responseClass = responseClass;
        return this;
    }

    public MonitorableMessage getMonitorableMessage() {
        return monitorableMessage;
    }

    public Core setMonitorableMessage(MonitorableMessage monitorableMessage) {
        this.monitorableMessage = monitorableMessage;
        return this;
    }

    public Core addSerializer(Class cls, StdSerializer serializer) {
        module.addSerializer(cls, serializer);
        return this;
    }

    public Core addDeserializer(Class cls, StdDeserializer deserializer) {
        module.addDeserializer(cls, deserializer);
        return this;
    }


    public String getUrl() {
        return url;
    }

    public Core withUrl(String url) {
        this.url = url;
        return this;
    }

    public void destroyMe(Class<? extends Core> cls) {
        map.remove(cls);
    }


    public void init() {
        goConvertorHelper = new GoConvertorHelper();
        objectMapper = goConvertorHelper.getObjectMapper();
        objectMapper = objectMapper.registerModule(module);
    }

    public abstract void callMethod(GoResponseHandler responseHandler, Object... params);


}
