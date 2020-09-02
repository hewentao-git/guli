package com.company.guli.result;

import com.company.guli.enums.ResultCodeEnume;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;


/**
 * 应用统一返回结果数据封装类
 *
 * @param <T> 返回结果数据类型
 * @author Administrator
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class AppResponse<T> {

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;


    /**
     * 快速响应成功
     *
     * @param data
     * @return
     */
    public static <T> AppResponse<T> ok(T data) {
        AppResponse<T> resp = new AppResponse<T>();
        resp.setCode(ResultCodeEnume.SUCCESS.getCode());
        resp.setMessage(ResultCodeEnume.SUCCESS.getMessage());
        resp.setData(data);
        return resp;
    }


    /**
     * 快速响应失败
     */
    public static <T> AppResponse<T> fail(T data) {
        AppResponse<T> resp = new AppResponse<>();
        resp.setCode(ResultCodeEnume.FAIL.getCode());
        resp.setMessage(ResultCodeEnume.FAIL.getMessage());
        resp.setData(data);
        return resp;
    }


}

