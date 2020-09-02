package com.company.guli.enums;

public enum ResultCodeEnume {

    SUCCESS(20000, "成功"),
    FAIL(20001, "失败"),
    EDU_ID_ERROR(20002, "查询失败");

    private int code;
    private String message;

    private ResultCodeEnume(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
