package cn.edu.zucc.se2020g11.apiserver.util;

public class ResponseMsg
{
    private int code;
    private String message;
    private Object payload;

    public ResponseMsg() {}

    public ResponseMsg(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public ResponseMsg setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseMsg setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getPayload() {
        return payload;
    }

    public ResponseMsg setPayload(Object payload) {
        this.payload = payload;
        return this;
    }
}
