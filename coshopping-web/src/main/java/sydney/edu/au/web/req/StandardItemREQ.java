package sydney.edu.au.web.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author king
 * @date 2020-10-23 09:29
 * @description
 */

@Data
@ApiModel(value="StandardItemREQ object", description="Standard Item request object")
public class StandardItemREQ {

    @ApiModelProperty(value = "standard item name")
    private String name;

    @ApiModelProperty(value = "standard first level category name")
    private String catOneName;

    @ApiModelProperty(value = "standard second level category name")
    private String catTwoName;

}
