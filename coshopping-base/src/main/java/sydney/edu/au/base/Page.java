package sydney.edu.au.base;

import java.util.List;

/**
 * @author king
 * @date 2020-10-23 09:54
 * @description 因为前端分页数据是rows，不是records，在这里转换
 */
public class Page<T> extends
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> {

    public List<T> getRows() {
        // 调用父类的方法将数据传递给rows
        return super.getRecords();
    }

    // 设置为null,响应后就不会有数据
    @Override
    public List<T> getRecords() {
        return null;
    }

    public Page(long current, long size) {
        super(current, size);
    }


}
