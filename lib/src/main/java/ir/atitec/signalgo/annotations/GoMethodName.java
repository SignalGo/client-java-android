/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.atitec.signalgo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mehdi akbarian
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GoMethodName {
    public enum MethodType {
        invoke(0), emit(1),httpGet(2),httpPost(3),httpUploadFile(4),httpPost_json(5),httpPost_formData(6),httpPut_json(7),httpPut_formData(8),httpDelete(9);

        private int id;

        private MethodType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public enum PriorityType {
        low(1), medium(2), high(3), veryHigh(4);

        private int value;

        private PriorityType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    String name();

    MethodType type();

    GoError[] errors() default {};

    PriorityType priority() default PriorityType.low;

    boolean doMonitor() default true;

    String[] multipartKeys() default {};

    String serverUrl() default "";
}
