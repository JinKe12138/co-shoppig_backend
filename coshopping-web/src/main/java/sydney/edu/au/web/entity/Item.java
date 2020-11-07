package sydney.edu.au.web.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * item info table
 * </p>
 *
 * @author king
 * @since 2020-10-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Item object", description="item info table")
public class Item implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "item info ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "the user ID to which the item belongs")
    private Long userId;

    @ApiModelProperty(value = "the invitation code of the list")
    private String listCode;

    @ApiModelProperty(value = "item name")
    private String name;

    @ApiModelProperty(value = "category name")
    private String catName;

    @ApiModelProperty(value = "item price")
    private BigDecimal price;

    @ApiModelProperty(value = "where to buy this item")
    private String location;

    @ApiModelProperty(value = "purchase link")
    private String purchaseUrl;

    @ApiModelProperty(value = "item description")
    private String description;

    @ApiModelProperty(value = "item quantity")
    private Integer quantity;

    @ApiModelProperty(value = "logic delete")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "whether visible when be shared")
    private Integer sharable;

    @ApiModelProperty(value = "whether the item has been completed")
    private Integer complete;

    @ApiModelProperty(value = "optimistic locking")
    @Version
    private Integer version;

    @ApiModelProperty(value = "type(1 shopping list，2 wish list)")
    private Integer type;

    @ApiModelProperty(value = "date displayed on the UI interface")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date date;

    @ApiModelProperty(value = "Picture URL for this item")
    private String pictureUrl;


}
