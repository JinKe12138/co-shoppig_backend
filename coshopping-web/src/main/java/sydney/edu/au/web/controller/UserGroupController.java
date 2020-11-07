package sydney.edu.au.web.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * user group table controller
 * </p>
 *
 * @author king
 * @since 2020-10-01
 */
@RestController
@RequestMapping("/userGroup")
public class UserGroupController {

//
//    @Autowired
//    UserGroupService userGroupService;
//
//    /**
//     * Create or join a user group
//     *
//     * @param userGroup
//     * @return
//     */
//    @ApiOperation("Create or join a user group")
//    @PostMapping
//    public Result create(@RequestBody UserGroup userGroup, @RequestParam String type) {
//        if ("create".equals(type)) {
//            return userGroupService.createUserGroup(userGroup);
//        }
//        if ("join".equals(type)) {
//            return userGroupService.joinUserGroup(userGroup);
//        }
//        return Result.error("Please choose type");
//    }
//
//
//    /**
//     * Delete,quit or remove a user group
//     *
//     * @param id user group Id
//     * @return
//     */
//    @ApiOperation("Delete,quit or remove a user group")
//    @DeleteMapping("/{id}")
//    public Result delete(@PathVariable Long id, @RequestParam String type) {
//        if ("delete".equals(type)) {
//            return userGroupService.deleteUserGroup(id);
//        }
//        if ("quit".equals(type)) {
//            return userGroupService.quitUserGroup(id);
//        }
//        if ("remove".equals(type)) {
//            return userGroupService.removeUser(id);
//        }
//        return Result.error("Please choose type");
//    }
//
//
//    @ApiOperation("Update a user group info(change group name,invitation code)")
//    @PutMapping
//    public Result updateUserGroup(@RequestParam Long userGroupId,@RequestParam(required = false) String groupName,@RequestParam(required = false) String invitationCode) {
//
//        return userGroupService.updateUserGroup(userGroupId, groupName, invitationCode);
//
//    }
//
//
//    @ApiOperation("Query a user group's id through its name and user Id")
//    @GetMapping("/groupId")
//    public Result queryUserGroupIdByNameAndUserId(@RequestParam String groupName, @RequestParam Long userId) {
//        return userGroupService.queryUserGroupIdByNameAndUserId(groupName, userId);
//    }
//
//
//    @ApiOperation("Query a user group name through invitation code")
//    @GetMapping("/groupName")
//    public Result queryUserGroupNameByInvitationCode(@RequestParam String invitationCode) {
//        return userGroupService.queryUserGroupNameByInvitationCode(invitationCode);
//    }
//
//
//    @ApiOperation("Query all the users in the user group")
//    @GetMapping("/users")
//    public Result queryAllUsers(@RequestParam String groupName) {
//        return userGroupService.queryAllUsers(groupName);
//    }
//
//
//    @ApiOperation("Query all the groups that have remove the user from the group")
//    @GetMapping("/BeenDeletedGroups")
//    public Result queryAllHaveBeenDeletedGroups(@RequestParam Long userId) {
//        return userGroupService.queryAllHaveBeenDeletedGroups(userId);
//    }
//
//    @ApiOperation("Query all the groups that the user joined")
//    @GetMapping("/groups")
//    public Result queryUserGroupsByUserId(@RequestParam Long userId) {
//        return userGroupService.getUserGroupsByUserId(userId);
//    }


}

