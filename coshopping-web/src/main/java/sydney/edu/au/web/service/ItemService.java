package sydney.edu.au.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sydney.edu.au.base.Result;
import sydney.edu.au.web.entity.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * <p>
 * item info table service
 * </p>
 *
 * @author king
 * @since 2020-10-01
 */
public interface ItemService extends IService<Item> {

    /**
     * query all the user's items through list code
     * @param listCode
     * @return
     */
    Result queryAllItems(String listCode);

    /**
     * query items through different conditions
     * @param userId
     * @param itemName
     * @param catOneName
     * @param catTwoName
     * @param lowPrice
     * @param highPrice
     * @param location
     * @param url
     * @param description
     * @param sharable
     * @param complete
     * @param type
     * @param displayDate
     * @return
     */
    Result queryItemsByConditions(Long userId, String itemName, String catOneName, String catTwoName, BigDecimal lowPrice,BigDecimal highPrice,
                                  String location, String url, String description, Integer sharable, Integer complete, Integer type, Date displayDate);

    /**
     * query all the user's shopping list items through user Id
     * @param userId
     * @return
     */
    Result queryAllShoppingListItems(Long userId);


    /**
     * query all the user's wish list items through user Id
     * @param userId
     * @return
     */
    Result queryAllWishListItems(Long userId);


    /**
     * Get an item object through item name
     * @param itemName
     * @return
     */
    Result getItemByName(String itemName);

    /**
     * Get all items with similar name
     * @param itemName
     * @return
     */
    Result getAllItemsByName(String itemName);

    /**
     * add an new item
     * @param item
     * @return
     */
    Result addItem(Item item);

    /**
     * delete an item by its id
     * @param id
     * @return
     */
    Result deleteItem(Long id);

    /**
     * delete items by id
     * @param id
     * @return
     */
    Result deleteItems(ArrayList<Long> id);



    Result deleteAllItemsInList(String listCode);

    /**
     * get an item Id by its name
     * @param itemName
     * @return
     */
    Result getItemIdByName(String itemName);

    /**
     * update an item
     * @param item
     * @return
     */
    Result updateItem(Item item);

    Result queryItemsByMonthAndCat(Long userId,String month,String catName);

    Result isComplete(Long itemId);



}
