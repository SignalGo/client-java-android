/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.atitec.signalgo.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import ir.atitec.signalgo.models.JSOGRef;

/**
 * @author mehdi akbarian
 */
public class GoConvertorHelper {
    private static String CHARSET = "UTF-8";

    public GoConvertorHelper() {
    }
    private ObjectMapper mapper;

    public synchronized ObjectMapper getObjectMapper() {
            if (mapper == null) {
                mapper = new ObjectMapper();
                mapper.configure(MapperFeature.USE_ANNOTATIONS, true);
                mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

                mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
                mapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.setTimeZone(DateTimeZone.getDefault().toTimeZone());
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

//            mapper.setAnnotationIntrospector(new MyJacksonAnnotationIntrospector());

            }
            return mapper;
    }

    public String serialize(Object object) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(object);
    }

    public <T extends Object> T deserialize(String data, Class<T> s) throws IOException {
//        RefrenceAnalysor refrenceAnalysor = new RefrenceAnalysor(data);
//        String str = refrenceAnalysor.getFinalJson();

        return getObjectMapper().readValue(data, s);
    }

    public <T extends Object> T deserialize(String data, Type type) throws IOException {
        RefrenceAnalysor refrenceAnalysor = new RefrenceAnalysor(data);
        String str = refrenceAnalysor.getFinalJson();

        return getObjectMapper().readValue(str, getObjectMapper().constructType(type));
    }

    public byte[] byteConvertor(Object object) throws JsonProcessingException, UnsupportedEncodingException {
        String raw = serialize(object);
        return raw.getBytes(CHARSET);
    }

    public boolean configureMapper(MapperFeature feature, boolean activate) {
        if (mapper != null) {
            mapper.configure(feature, activate);
            return true;
        }
        return false;
    }



}
