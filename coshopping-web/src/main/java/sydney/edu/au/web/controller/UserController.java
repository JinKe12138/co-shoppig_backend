package sydney.edu.au.web.controller;


import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sydney.edu.au.base.Result;
import sydney.edu.au.web.entity.User;
import sydney.edu.au.web.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * user info table controller
 * </p>
 *
 * @author king
 * @since 2020-10-01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * add user
     *
     * @param
     * @return
     */
    @ApiOperation("Register(add user)")
    @PostMapping
    public Result add(@RequestBody User user) {
        return userService.add(user);
    }

    /**
     * delete user
     *
     * @param id user id
     * @return
     */
    @ApiOperation("Delete user")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable int id) {
        boolean b = userService.removeById(id);
        if (b) {
            return Result.ok();
        }

        return Result.error("Failed to delete employee information");
    }

    /**
     * Query user info
     *
     * @param id
     * @return
     */
    @ApiOperation("Query user info")
    @GetMapping("/{id}")
    public Result get(@PathVariable int id) {
        User user = userService.getById(id);

        if (user!=null) {
            return Result.ok(user);
        }
         return Result.error("No such user");
    }

    /**
     * Query all users with similar names
     * @param userName
     * @return
     */
    @ApiOperation("Query all users with similar names")
    @GetMapping("/list/similar")
    public Result getUsers(@RequestParam("userName") String userName) {
        return userService.queryUsers(userName);

    }

    /**
     * Get all the users
     * @return
     */
    @ApiOperation("Get all the users")
    @GetMapping("/list/all")
    public Result getAllUserInfo(){
        return userService.getAllUserInfo();
    }

//    /**
//     * Get user Id by user Name
//     * @param userName
//     * @return
//     */
//    @ApiOperation("Get user Id by user Name")
//    @GetMapping("/userId")
//    public Result getUserIdByName(@RequestParam("userName") String userName){
//        return userService.getUserIdByName(userName);
//    }

    @ApiOperation("Get user Id by email address")
    @GetMapping("/userId/email")
    public Result getUserIdByEmail(@RequestParam("email") String email){
        return userService.getUserIdByEmail(email);
    }

    @ApiOperation("Get user Id by user name")
    @GetMapping("/userId/userName")
    public Result getUserIdByName(@RequestParam("name") String name){
        return userService.getUserIdByName(name);
    }

    @GetMapping("bigReq")
    public String bigReqList() {
        List<String> result = new ArrayList<>(4096);
        for (int i = 0; i < 4096; i++) {
            result.add(UUID.randomUUID().toString());
        }
        return JSON.toJSONString(result);
    }

//    /**
//     * update user info
//     *
//     * @param id
//     * @param user
//     * @return
//     */
//    @ApiOperation("update user info")
//    @PutMapping("/{id}")
//    public Result update(@PathVariable int id,
//                         @RequestBody User user) {
//
//        return userService.update(id, user);
//    }

}

