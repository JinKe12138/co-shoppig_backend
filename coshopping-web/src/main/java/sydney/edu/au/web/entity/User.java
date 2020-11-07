package sydney.edu.au.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * user info table
 * </p>
 *
 * @author king
 * @since 2020-10-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User object", description="user info table")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "user ID")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @ApiModelProperty(value = "user name")
    private String userName;

    @ApiModelProperty(value = "password")
    private String password;

    @ApiModelProperty(value = "email address")
    private String email;

    @ApiModelProperty(value = "security code for retrieving password")
    private String securityCode;




}
