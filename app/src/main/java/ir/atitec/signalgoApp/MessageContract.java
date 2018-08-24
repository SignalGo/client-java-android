/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.atitec.signalgoApp;

import ir.atitec.signalgo.models.Response;

/**
 * @author white
 */
public class MessageContract<T> extends Response<T> {
    public T data;
    public boolean isSuccess;
    public String message;
    public int errorCode;
    public String stackTrace;
}
