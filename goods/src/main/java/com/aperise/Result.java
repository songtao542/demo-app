package com.aperise;

import java.util.HashMap;

public class Result extends HashMap<String, Object> {

    public interface Status {
        int OK = 1000;
        int ERROR = 1001;
        int PARAMETER_MISSING = 1002;
        int PARAMETER_ERROR = 1003;
        int DATA_NOT_EXIST = 1004;
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
        return new Result(Status.OK, data);
    }

    public static Result ERROR(String errorMessage) {
        return new Result(Status.ERROR, errorMessage);
    }

    public static Result ERROR(int error, String errorMessage) {
        return new Result(error, errorMessage);
    }
}
