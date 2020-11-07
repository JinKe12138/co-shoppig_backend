package sydney.edu.au.base;

/**
 * @author king
 * @date 2020-09-30 11:40
 * @description
 */

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * Custom response structure
 */
@Data
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    // business status of response
    private Integer code;

    // response message
    private String message;

    // response data
    private Object data;

    /**
     * whether success
     */
    private Boolean flag;

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.flag = code.equals(ResultEnum.SUCCESS.getCode());
    }

    public static Result ok() {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), null);
    }

    public static Result ok(Object data) {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), data);
    }

    public static Result ok(String message, Object data) {
        return new Result(ResultEnum.SUCCESS.getCode(), message, data);
    }

    public static Result error(String message) {

        return new Result(ResultEnum.ERROR.getCode(), message, null);
    }

    public static Result build(int code, String message) {

        return new Result(code, message, null);
    }

    public static Result build(ResultEnum resultEnum) {

        return new Result(resultEnum.getCode(), resultEnum.getDesc(), null);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


    /**
     * Converts the JSON string into a Result object
     *
     * @param json
     * @return
     */
    public static Result format(String json) {
        try {
            return JSON.parseObject(json, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
