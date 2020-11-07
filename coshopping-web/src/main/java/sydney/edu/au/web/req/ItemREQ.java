package sydney.edu.au.web.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author king
 * @date 2020-10-23 09:10
 * @description
 */

@Data
@ApiModel(value="ItemREQ object", description="Item request object")
public class ItemREQ implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "the user ID to which the item belongs")
    private Long userId;

    @ApiModelProperty(value = "item name")
    private String name;

    @ApiModelProperty(value = "first level category name")
    private String catOneName;

    @ApiModelProperty(value = "second level category name")
    private String catTwoName;
}
