package sydney.edu.au.web.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author king
 * @date 2020-10-01 15:29
 * @description
 */
@Data
@ApiModel(value="PasswordREQ object", description="Password request object")
public class PasswordREQ implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "user Id")
    private Integer userId;

    @ApiModelProperty(value = "user password")
    private String password;

}