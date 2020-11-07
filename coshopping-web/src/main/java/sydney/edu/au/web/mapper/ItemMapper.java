package sydney.edu.au.web.mapper;

import org.apache.ibatis.annotations.Param;
import sydney.edu.au.web.entity.Item;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;

/**
 * <p>
 * item info table Mapper 接口
 * </p>
 *
 * @author king¬
 * @since 2020-10-01
 */
public interface ItemMapper extends BaseMapper<Item> {

    ArrayList<Item> searchItems(@Param("userId") Long userId, @Param("month") String month, @Param("catName") String catName);

}
