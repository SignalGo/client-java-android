package ir.atitec.signalgo.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

import ir.atitec.signalgo.models.JSOGGenerator;
import ir.atitec.signalgo.models.JSOGRef;

/**
 * Created by hamed on 2/5/2018.
 */

public class SignalGoSerializer {

    public static class DateTimePHPSerializer extends StdSerializer<DateTime> {
        public DateTimePHPSerializer() {
            this(null);
        }

        DateTimeFormatter formatter;

        protected DateTimePHPSerializer(Class<DateTime> t) {
            super(t);
            formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        }

        @Override
        public void serialize(DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            String str = dateTime.toString(formatter);
            jsonGenerator.writeObject(str);
        }
    }

    public static class DateTimePHPDeserializer extends StdDeserializer<DateTime> {

        protected DateTimePHPDeserializer(Class<?> vc) {
            super(vc);
        }

        DateTimeFormatter formatter;

        public DateTimePHPDeserializer() {
            this(null);
            formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        }

        @Override
        public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            try {
                MyDate s = jsonParser.readValueAs(MyDate.class);
                DateTime dateTime = formatter.parseDateTime(s.date);
                return dateTime;
            } catch (Exception e) {
                return null;
            }
        }
    }

    private static class MyDate {
        public String date;
        public int timezone_type;
        public String timezone;
    }


    public static class DateTimeSerializer extends StdSerializer<DateTime> {
        public DateTimeSerializer() {
            this(null);
        }

        protected DateTimeSerializer(Class<DateTime> t) {
            super(t);
        }

        @Override
        public void serialize(DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(dateTime.toString());
        }
    }

    public static class DateTimeDeserializer extends StdDeserializer<DateTime> {

        protected DateTimeDeserializer(Class<?> vc) {
            super(vc);
        }

        public DateTimeDeserializer() {
            this(null);
        }

        @Override
        public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            String s = jsonParser.readValueAs(String.class);
            DateTime dateTime = new DateTime(s);
            return dateTime;
        }
    }


    public static class JSOGRefDeserializer extends JsonDeserializer<JSOGRef> {
        @Override
        public JSOGRef deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
            JsonNode node = p.readValueAsTree();
            if (node.isTextual()) {
                return new JSOGRef(node.asInt());
            }
            JsonNode n = node.get(JSOGGenerator.REF_KEY);
            if (n == null) {
                throw new JsonMappingException(p, "Could not find key '" + JSOGGenerator.REF_KEY
                        + "' from (" + node.getClass().getName() + "): " + node);
            }
            return new JSOGRef(n.asInt());
        }
    }

    public static class JSOGRefSerializer extends JsonSerializer<JSOGRef> {
//        @Override
//        public JSOGRef deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
//            JsonNode node = p.readValueAsTree();
//            if (node.isTextual()) {
//                return new JSOGRef(node.asInt());
//            }
//            JsonNode n = node.get(REF_KEY);
//            if (n == null) {
//                throw new JsonMappingException(p, "Could not find key '" + REF_KEY
//                        + "' from (" + node.getClass().getName() + "): " + node);
//            }
//            return new JSOGRef(n.asInt());
//        }

        @Override
        public void serialize(JSOGRef value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeObject(null);
        }
    }


}
