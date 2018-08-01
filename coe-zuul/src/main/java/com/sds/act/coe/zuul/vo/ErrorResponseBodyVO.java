package com.sds.act.coe.zuul.vo;

public class ErrorResponseBodyVO {
    private String code;

    private String errorMessage;

    private String invalidParam;

    public ErrorResponseBodyVO() {
    }

    public ErrorResponseBodyVO(String code, String errorMessage, String invalidParam) {
        this.code = code;
        this.errorMessage = errorMessage;
        this.invalidParam = invalidParam;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getInvalidParam() {
        return invalidParam;
    }

    public void setInvalidParam(String invalidParam) {
        this.invalidParam = invalidParam;
    }

    public String toJSONString() {
        return "{\"errors\": [{ " +
                (code != null ? "\"code\": \"" + code + "\"," : "\"code\": null,") +
                (errorMessage != null ? "\"errorMessage\": \"" + errorMessage + "\"," : "\"errorMessage\": null,") +
                (invalidParam != null ? "\"invalidParam\": \"" + invalidParam + "\"" : "\"invalidParam\": null") +
                "}]}";
    }


}
