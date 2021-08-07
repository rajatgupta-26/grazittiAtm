package com.model.response;

public class BaseResponse {

    private String responseCode;
    private String responseDesc;
    private String responseStatus;

    public void setResponseCode(String responseCode){
        this.responseCode = responseCode;
    }

    public void setResponseDesc(String responseDesc){
        this.responseDesc = responseDesc;
    }

    public void setResponseStatus(String responseStatus){
        this.responseStatus = responseStatus;
    }

    public String getResponseCode(){
        return responseCode;
    }

    public String getResponseDesc(){
        return responseDesc;
    }

    public String getResponseStatus(){
        return responseStatus;
    }

    @Override
    public String toString(){
        return "ResponseWrapper:{" +
                " resultCode=" + getResponseCode() +
                " resultStatus=" + getResponseStatus() +
                " resultDesc=" + getResponseDesc() +
                " }";
    }
}
