package sydney.edu.au.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * user group table
 * </p>
 *
 * @author king
 * @since 2020-10-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserGroup object", description="user group table")
public class UserGroup implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "user group ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "user ID")
    private Long userId;

    @ApiModelProperty(value = "group name")
    private String groupName;

    @ApiModelProperty(value = "1 means you are the group leader")
    private Boolean isGroupLeader;

    @ApiModelProperty(value = "1 means you have been deleted by the group leader")
    private Boolean beenDeleted;

    @ApiModelProperty(value = "invitation code for joining the group")
    private String invitationCode;

}
