package sydney.edu.au.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sydney.edu.au.base.Result;
import sydney.edu.au.web.entity.CoList;
import sydney.edu.au.web.entity.Item;
import sydney.edu.au.web.mapper.ItemMapper;
import sydney.edu.au.web.service.ItemService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * item info table service impl
 * </p>
 *
 * @author king
 * @since 2020-10-01
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

    @Override
    public Result queryAllItems(String listCode) {

        List<Item> items = getAllItems(listCode);
        if (items != null) {
            return Result.ok(items);
        }
        return Result.error("no such items");

    }

    public List<Item> getAllItems(String listCode) {
        QueryWrapper<Item> query = new QueryWrapper();
        query.eq("list_code", listCode);

        return baseMapper.selectList(query);
    }

    @Override
    public Result queryItemsByConditions(Long userId, String itemName, String catOneName, String catTwoName, BigDecimal lowPrice, BigDecimal highPrice,
                                         String location, String url, String description, Integer sharable, Integer complete, Integer type, Date displayDate) {
        QueryWrapper<Item> query = new QueryWrapper();
        query.eq("user_id", userId);

        if (itemName != null) {
            query.like("name", itemName);
        }
        if (catOneName != null) {
            query.eq("cat_one_name", catOneName);
        }
        if (catTwoName != null) {
            query.eq("cat_two_name", catTwoName);
        }
        if (lowPrice != null && highPrice != null) {
            query.between("price", lowPrice, highPrice);
        }
        if (location != null) {
            query.like("location", location);
        }
        if (url != null) {
            query.like("url", url);
        }
        if (description != null) {
            query.like("description", description);
        }
        if (sharable != null) {
            query.eq("sharable", sharable);
        }
        if (complete != null) {
            query.eq("complete", complete);
        }
        if (type != null) {
            query.eq("type", type);
        }
        if (displayDate != null) {
            query.eq("display_date", displayDate);
        }
        List<Item> items = baseMapper.selectList(query);
        if (items != null) {
            return Result.ok(items);
        }
        return Result.error("no such items");
    }


    @Override
    public Result queryAllShoppingListItems(Long userId) {
        QueryWrapper<Item> query = new QueryWrapper();
        query.eq("user_id", userId).eq("type", 1);

        List<Item> items = baseMapper.selectList(query);
        if (items != null) {
            return Result.ok(items);
        }
        return Result.error("no such items");
    }

    @Override
    public Result queryAllWishListItems(Long userId) {

        QueryWrapper<Item> query = new QueryWrapper();
        query.eq("user_id", userId).eq("type", 2);

        List<Item> items = baseMapper.selectList(query);
        if (items != null) {
            return Result.ok(items);
        }
        return Result.error("no such items");
    }

    private Item queryItemByName(String itemName) {
        QueryWrapper<Item> query = new QueryWrapper();
        query.eq("name", itemName);

        Item item = baseMapper.selectOne(query);
        return item;
    }

    @Override
    public Result getItemByName(String itemName) {

        Item item = queryItemByName(itemName);
        if (item != null) {
            return Result.ok(item);
        }
        return Result.error("this item doesn't exist");
    }

    @Override
    public Result getAllItemsByName(String itemName) {
        QueryWrapper<Item> query = new QueryWrapper();
        query.like("name", itemName);

        List<Item> items = baseMapper.selectList(query);
        if (items != null) {
            return Result.ok(items);
        }
        return Result.error("no such items ");
    }

    @Autowired
    CoListServiceImpl coListService;

    @Override
    @Transactional
    public Result addItem(Item item) {
        if (item == null || item.getName() == null) {
            return Result.error("Item can not be empty");
        }
//        // Verify whether the item already exists
//        Item item1 = queryItemByName(item.getName());
//        if (item1 != null) {
//            return Result.error("Item already exists");
//        }

        CoList list = coListService.getLeaderByInvitationCode(item.getListCode());
        if (list == null) {
            return Result.error("No such list");
        }
        boolean b = this.save(item);
        if (!b) {
            return Result.error("Failed to add item");
        }
        Integer integer = coListService.fakeUpdate(list);
        if (integer == 0) {
            return Result.error("Failed to update time");
        }
        return Result.ok();

    }

    @Override
    public Result deleteItem(Long id) {

        Item item1 = this.getById(id);

        if (item1 != null) {
            boolean b = this.removeById(id);
            if (b) {
                CoList list = coListService.getLeaderByInvitationCode(item1.getListCode());

                Integer integer = coListService.fakeUpdate(list);
                if (integer == 0) {
                    return Result.error("Failed to update time");
                }

                return Result.ok();
            }
            return Result.error("Failed to delete item");
        }

        return Result.error("this Item doesn't exist");


    }

    @Override
    @Transactional
    public Result deleteItems(ArrayList<Long> id) {

        Long itemId = id.get(0);
        Item item = this.getById(itemId);

        int i = baseMapper.deleteBatchIds(id);
        if (i == id.size()) {


            CoList list = coListService.getLeaderByInvitationCode(item.getListCode());
            Integer integer = coListService.fakeUpdate(list);

            if (integer == 0) {
                return Result.error("Failed to update time");
            }

            return Result.ok("all items delete success");
        }


        return Result.ok(i + "items delete success");

    }

    @Override
    public Result deleteAllItemsInList(String listCode) {
        if (deleteAll(listCode)) {
            CoList list = coListService.getLeaderByInvitationCode(listCode);
            Integer integer = coListService.fakeUpdate(list);
            if (integer == 0) {
                return Result.error("Failed to update time");
            }
            return Result.ok();
        }
        return Result.error("Delete failed");
    }

    public boolean deleteAll(String listCode) {

        List<Item> items = getAllItems(listCode);
        if (items.isEmpty()) {
            return false;
        }
        for (Item item : items) {
            boolean b = this.removeById(item);
            if (!b) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Result getItemIdByName(String itemName) {
        QueryWrapper<Item> query = new QueryWrapper();
        query.eq("name", itemName);

        Item item = baseMapper.selectOne(query);
        if (item != null) {
            return Result.ok(item.getId());
        }
        return Result.error("this item doesn't exist");
    }

    @Override
    @Transactional
    public Result updateItem(Item item) {
        if (item == null || item.getName() == null) {
            return Result.error("Item can not be empty");
        }
        // Verify whether the item already exists
        Item item1 = this.getById(item.getId());
        CoList list = coListService.getLeaderByInvitationCode(item1.getListCode());
        if (list== null) {
            return Result.error("No such list");
        }

        item1.setName(item.getName());
        item1.setCatName(item.getCatName());
        item1.setType(item.getType());
        item1.setLocation(item.getLocation());
        item1.setPictureUrl(item.getPictureUrl());
        item1.setDescription(item.getDescription());
        item1.setSharable(item.getSharable());
        item1.setComplete(item.getComplete());
        item1.setPrice(item.getPrice());
        item1.setDate(item.getDate());
        item1.setQuantity(item.getQuantity());


        boolean b = this.updateById(item1);
        if (b) {
            if (list != null) {
                Integer integer = coListService.fakeUpdate(list);
                if (integer == 0) {
                    return Result.error("Failed to update item");
                }
            }

            return Result.ok();
        }
        return Result.error("Failed to update item");

    }

    @Override
    public Result queryItemsByMonthAndCat(Long userId, String month, String catName) {
        ArrayList<Item> items = baseMapper.searchItems(userId, month, catName);

        if (items.size() > 0) {
            return Result.ok(items);
        }
        return Result.error("No such items");
    }

    @Override
    public Result isComplete(Long itemId) {
        Item item = this.getById(itemId);


        return Result.ok(item.getComplete());
    }
}
