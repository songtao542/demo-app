package com.aperise;

import java.util.HashMap;

public class Result extends HashMap<String, Object> {

    public static interface STATUS {
        int OK = 1000;
        int ERROR = 1001;
    }

    private Result(int status, String errorMessage) {
        put("status", status);
        put("data", "");
        put("error", errorMessage);
    }

    private Result(int status, Object data) {
        put("status", status);
        put("data", data);
        put("error", "");
    }


    public static Result OK(Object data) {
        return new Result(STATUS.OK, data);
    }

    public static Result ERROR(String errorMessage) {
        return new Result(STATUS.ERROR, errorMessage);
    }

    public static Result ERROR(int error, String errorMessage) {
        return new Result(error, errorMessage);
    }
}
