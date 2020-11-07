package sydney.edu.au.web.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * list table
 * </p>
 *
 * @author king
 * @since 2020-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CoList object", description="list table")
public class CoList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "list ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "user ID")
    private Long userId;

    @ApiModelProperty(value = "list name")
    private String name;

    @ApiModelProperty(value = "1 means you are the list leader")
    private Boolean isLeader;

    @ApiModelProperty(value = "1 means you have been deleted by the leader")
    private Boolean beenDeleted;

    @ApiModelProperty(value = "invitation code for joining the group")
    private String invitationCode;

    @ApiModelProperty(value = "type(1 shopping list，2 wish list)")
    private Integer type;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date time;

    public CoList(Long userId, String name, String invitationCode, Integer type) {
        this.userId = userId;
        this.name = name;
        this.invitationCode = invitationCode;
        this.type = type;
    }
}
