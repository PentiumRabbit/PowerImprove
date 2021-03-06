/*
 * @Title:  NetworkMessage.java
 * @package_name:  com.stormtv.market.network
 * @Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 * @author:  ----zhaoruyang----
 * @data:  2014-7-24 下午5:25:52
 * @version:  V1.0
 *
 */

package com.zry.net.http;


/**
 * NetworkMessage
 * 
 * @author ----zhaoruyang----
 * @version: V1.0
 * @data: 2014-7-24 下午5:25:52
 */

public class NetMessage {

    private int httpStatusCode;

    private String httpResult;

    private INetCallBack callBack;

    private Exception exception;

    private boolean isObtain;

    /**
     * @return the isObtain
     */
    public boolean isObtain() {
        return this.isObtain;
    }

    /**
     * @param isObtain the isObtain to set
     */
    public void setObtain(boolean isObtain) {
        this.isObtain = isObtain;
    }

    /**
     * @return the httpStatusCode
     */
    public int getHttpStatusCode() {
        return this.httpStatusCode;
    }

    /**
     * @param httpStatusCode the httpStatusCode to set
     */
    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    /**
     * @return the httpResult
     */
    public String getHttpResult() {
        return this.httpResult;
    }

    /**
     * @param httpResult the httpResult to set
     */
    public void setHttpResult(String httpResult) {
        this.httpResult = httpResult;
    }

    /**
     * @return the callBack
     */
    public INetCallBack getCallBack() {
        return this.callBack;
    }

    /**
     * @param callBack the callBack to set
     */
    public void setCallBack(INetCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * @return the exception
     */
    public Exception getException() {
        return this.exception;
    }

    /**
     * @param exception the exception to set
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }

}
