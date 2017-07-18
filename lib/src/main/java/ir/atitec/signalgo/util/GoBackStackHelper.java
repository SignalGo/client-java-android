/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.atitec.signalgo.util;

import ir.atitec.signalgo.annotations.GoMethodName;
import ir.atitec.signalgo.annotations.GoServiceName;

import java.lang.reflect.Method;

/**
 * @author white
 */
public class GoBackStackHelper {

    public static String getServiceName() throws ClassNotFoundException {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (StackTraceElement ste : elements) {
            Class c = Class.forName(ste.getClassName());
            if (c.getAnnotation(GoServiceName.class) != null) {
                GoServiceName gsn = (GoServiceName) c.getAnnotation(GoServiceName.class);
                return gsn.name();
            }
        }
        return null;
    }

    public static GoMethodName getMethodName() throws ClassNotFoundException {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (StackTraceElement ste : elements) {
            Class c = Class.forName(ste.getClassName());
            if (c.getAnnotation(GoServiceName.class) != null) {
                Method[] methods = c.getMethods();
                for (Method m : methods) {
                    if (m.getAnnotation(GoMethodName.class) != null && m.getName().equals(ste.getMethodName()))
                        return m.getAnnotation(GoMethodName.class);
                }
            }
        }
        return null;
    }


//    public static int getPriority() throws ClassNotFoundException {
//        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
//        for (StackTraceElement ste : elements) {
//            Class c = Class.forName(ste.getClassName());
//            if (c.getAnnotation(GoServiceName.class) != null) {
//                Method[] methods = c.getMethods();
//                for (Method m : methods) {
//                    if (m.getAnnotation(GoMethodName.class) != null && m.getName().equals(ste.getMethodName()))
//                        return m.getAnnotation(GoMethodName.class).priority();
//                }
//            }
//        }
//        return 0;
//    }
}
