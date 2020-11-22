package cn.edu.zucc.se2020g11.apiserver.exception;

public class BaseException extends RuntimeException {
    private String code;
    private String message;

    public BaseException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
