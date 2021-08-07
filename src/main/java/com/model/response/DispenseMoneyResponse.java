package com.model.response;

public class DispenseMoneyResponse extends BaseResponse{

    private String currencyBreakdown;

    public String getCurrencyBreakdown() {
        return currencyBreakdown;
    }

    public void setCurrencyBreakdown(String currencyBreakdown){
        this.currencyBreakdown = currencyBreakdown;
    }

    @Override
    public String toString(){
        return "ResponseWrapper:{" +
                " resultCode=" + getResponseCode() +
                " resultStatus=" + getResponseStatus() +
                " resultDesc=" + getResponseDesc() +
                " resultCurrency = " + getCurrencyBreakdown() +
                " }";
    }
}
