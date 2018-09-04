package ir.atitec.signalgo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hamed on 7/17/2017.
 */
public class GoHeader {
    public String header;
    public String value;

    public GoHeader(String header, String value) {
        this.header = header;
        this.value = value;
    }
}
