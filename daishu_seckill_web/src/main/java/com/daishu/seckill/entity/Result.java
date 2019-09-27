package com.daishu.seckill.entity;

import java.io.Serializable;

/**
 * @author TigerLi
 * @Version 1.0
 * 2019/9/21  14:28
 */
public class Result implements Serializable {


    private boolean success;
    private boolean flag;
    private String message;

    public Result(boolean success, String message) {
        super();
        this.success=success;
        this.flag = success;
        this.message = message;
    }
    public boolean isFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
