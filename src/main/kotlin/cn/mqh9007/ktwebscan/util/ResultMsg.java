package cn.mqh9007.ktwebscan.util;


import lombok.Data;

import java.io.Serializable;

/**
 * 全局统一返回对象
 *
 * @author Tom
 * @date 2020-12-14
 */

@Data
public class ResultMsg implements Serializable {

    /**
     * 返回状态(成功:true 失败:false)
     */
    private boolean success;
    /**
     * 状态码(成功:200 失败:500)
     */
    private Integer code;
    /**
     * 异常提示消息
     */
    private String msg;
    /**
     * 返回参数
     */
    private Object data;

    public ResultMsg() {
    }

    public ResultMsg(boolean success) {
        this.success = success;
    }

    public ResultMsg(boolean success, Integer code) {
        this.success = success;
        this.code = code;
    }

    public ResultMsg(boolean success, Integer code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public ResultMsg(boolean success, Integer code, String msg, Object data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultMsg(boolean success, Integer code, Object data) {
        this.success = success;
        this.code = code;
        this.data = data;
    }
}

