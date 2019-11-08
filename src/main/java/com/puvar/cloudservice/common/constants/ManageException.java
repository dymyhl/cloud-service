package com.puvar.cloudservice.common.constants;

/***
 * 自定义异常类
 * @Auther: dingyuanmeng
 * @Date: 2019/8/13
 * @version : 1.0
 */
public class ManageException extends Exception {

    public ManageException() {
        super();
    }

    public ManageException(String message) {
        super(message);
    }

    public ManageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ManageException(Throwable cause) {
        super(cause);
    }
}
