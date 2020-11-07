package sydney.edu.au.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author king
 * @date 2020-09-30 15:35
 * @description
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {

    // 成功
    SUCCESS(2000, "success"),
    // 错误
    ERROR(999, "fail");

    private Integer code;

    public String desc;

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

}
