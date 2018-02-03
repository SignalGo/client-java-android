package ir.atitec.signalgo.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ir.atitec.signalgo.models.JSOGGenerator;

/**
 * Created by hamed on 2/1/2018.
 */

@JsonDeserialize(using=GoConvertorHelper.JSOGRefDeserializer.class)
public class JSOGRef
{
    @JsonProperty(JSOGGenerator.REF_KEY)
    public int ref;

    public JSOGRef() { }

    public JSOGRef(int val) {
        ref = val;
    }

    @Override
    public String toString() { return "[JSOGRef#"+ref+"]"; }

    @Override
    public int hashCode() {
        return ref;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof JSOGRef)
                && ((JSOGRef) other).ref == this.ref;
    }
}
