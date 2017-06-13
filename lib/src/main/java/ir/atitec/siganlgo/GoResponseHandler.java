
package ir.atitec.siganlgo;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by whiteman on 7/12/2016.
 */
public abstract  class GoResponseHandler<T> {


    public abstract void onResponse(T t);

    public TypeToken<T> typeToken;
    public Type type; // or getRawType() to return Class<? super T>
    public Class<T> clazz;

    public void setTypeToken(TypeToken<T> typeToken) {
        this.typeToken = typeToken;
    }

    public Type getType() {
        if(typeToken != null){
            type = typeToken.getType();
            return type;
        }

//        Class c = ((Object) this).getClass();
//        System.out.println("goResponseHandler1 : " + c.getName() + " " + c.getSimpleName() + " " + c.getComponentType() + " " + c.getCanonicalName());
//        Class cl = c.getGenericSuperclass().getClass();
//        System.out.println("goResponseHandler2 : " + cl.getName());
//
//        //return (Class<T>) ((Object) this).getClass();
//        Type[] types = ((ParameterizedType) (getClass().getGenericSuperclass())).getActualTypeArguments();
//        for (Type t : types) {
//            try {
//
//                clazz = (Class<T>) t;
//                return clazz;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

//        System.out.println("goResponseHandler3 : " + clazz.getName());
//        return (Class<T>) getClass();
        typeToken = new TypeToken<T>(MessageContract.class) {};
        type = typeToken.getType();

        return type;
    }

}
