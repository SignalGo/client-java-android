package ir.atitec.signalgo.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

/**
 * Created by whiteman on 7/12/2016.
 */
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class Response<T> {

//    public T data;
//    public boolean isSuccess;
//    public String message;
//    public int errorCode;
//    public String stackTrace;
//
//    @Override
//    public String toString() {
//        return "success: " + isSuccess + " ErrorMessage: " + message + " errorCode: " + errorCode + " stackTrace : " + stackTrace;
//    }
}