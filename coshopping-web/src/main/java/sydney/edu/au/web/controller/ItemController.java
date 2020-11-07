package sydney.edu.au.web.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sydney.edu.au.base.Result;
import sydney.edu.au.web.entity.Item;
import sydney.edu.au.web.service.ItemService;

import java.util.ArrayList;

/**
 * <p>
 * item info table controller
 * </p>
 *
 * @author king
 * @since 2020-10-01
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    ItemService itemService;


    @ApiOperation("query all the user's items through list code")
    @GetMapping("/list/all")
    public Result queryAllItems(@RequestParam String listCode) {
        return itemService.queryAllItems(listCode);
    }

//    @ApiOperation("query items through different conditions")
//    @GetMapping("/list/conditions")
//    public Result queryItemsByConditions(
//                                         @RequestParam long userId,
//                                         @RequestParam(required = false) String itemName,
//                                         @RequestParam(required = false) String catOneName,
//                                         @RequestParam(required = false) String catTwoName,
//                                         @RequestParam(required = false) BigDecimal lowPrice,
//                                         @RequestParam(required = false) BigDecimal highPrice,
//                                         @RequestParam(required = false) String location,
//                                         @RequestParam(required = false) String url,
//                                         @RequestParam(required = false) String description,
//                                         @RequestParam(required = false) Integer sharable,
//                                         @RequestParam(required = false) Integer complete,
//                                         @RequestParam(required = false) Integer type,
//                                         @RequestParam(required = false) Date displayDate) {
//
//        return itemService.queryItemsByConditions(userId, itemName, catOneName, catTwoName, lowPrice, highPrice,
//                location, url, description, sharable, complete, type, displayDate);
//
//    }

    @ApiOperation("query items through month and cat name")
    @GetMapping("/list/conditions")
    public Result queryItemsByCatNameAndMonth(@RequestParam Long userId, @RequestParam(required = false) String month, @RequestParam(required = false) String catName) {
        return itemService.queryItemsByMonthAndCat(userId, month, catName);
    }

//    @ApiOperation("query all the user's shopping list items through user Id")
//    @GetMapping("/list/shopping")
//    public Result queryAllShoppingListItems(
//            @RequestParam long userId) {
//        return itemService.queryAllShoppingListItems(userId);
//    }
//
//    @ApiOperation("query all the user's wish list items through user Id")
//    @GetMapping("/list/wish")
//    public Result queryAllWishListItems(@RequestParam long userId) {
//        return itemService.queryAllWishListItems(userId);
//    }

//    @ApiOperation("Get an item object through item name")
//    @GetMapping()
//    public Result getItemByName(@RequestParam String itemName) {
//        return itemService.getItemByName(itemName);
//    }
//
//    @ApiOperation("Get all items with similar name")
//    @GetMapping("/list/similar")
//    public Result getAllItemsByName(@RequestParam String itemName) {
//        return itemService.getAllItemsByName(itemName);
//    }

    /**
     * add item
     *
     * @param
     * @return
     */
    @ApiOperation("Add item")
    @PostMapping
    public Result addItems(@RequestBody Item item) {
        return itemService.addItem(item);

    }

    /**
     * delete item
     *
     * @param id
     * @return
     */
    @ApiOperation("Delete item")
    @DeleteMapping("/{id}")
    public Result deleteItem(@PathVariable("id") long id) {
        return itemService.deleteItem(id);
    }

    /**
     * delete items in bulk
     *
     * @param ids
     * @return
     */
    @ApiOperation("Delete items")
    @DeleteMapping()
    public Result deleteItems(@RequestParam ArrayList<Long> ids) {
        return itemService.deleteItems(ids);
    }

//    /**
//     * Get user Id by user Name
//     *
//     * @param itemName
//     * @return
//     */
//    @ApiOperation("Get item Id by item Name")
//    @GetMapping("/itemId")
//    public Result getItemIdByName(@RequestParam String itemName) {
//        return itemService.getItemIdByName(itemName);
//    }

    /**
     * update an item
     *
     * @param item
     * @return
     */
    @ApiOperation("update an item")
    @PutMapping()
    public Result updateItem(@RequestBody Item item) {
        return itemService.updateItem(item);
    }

    @ApiOperation("check whether the item is completed")
    @GetMapping("isComplete")
    public Result isComplete(@RequestParam Long itemId) {
        return itemService.isComplete(itemId);
    }


}

