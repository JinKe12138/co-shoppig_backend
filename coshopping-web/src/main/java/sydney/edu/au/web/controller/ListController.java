package sydney.edu.au.web.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sydney.edu.au.base.Result;
import sydney.edu.au.web.entity.CoList;
import sydney.edu.au.web.service.CoListService;

/**
 * <p>
 * list table controller
 * </p>
 *
 * @author king
 * @since 2020-11-03
 */
@RestController
@RequestMapping("/list")
public class ListController {


    @Autowired
    CoListService listService;

    @ApiOperation("Create a List")
    @PostMapping("/create")
    public Result createList(@RequestBody CoList coList) {
        return listService.createList(coList);
    }

    @ApiOperation("Join a list")
    @GetMapping("/join")
    public Result joinList(@RequestParam Long userId, @RequestParam String name, @RequestParam String invitationCode) {
        return listService.joinList(userId, name, invitationCode);
    }

    @ApiOperation("Delete ,quit or remove a user from a list")
    @DeleteMapping("/{listId}")
    public Result delete(@PathVariable Long listId, @RequestParam String type) {
        if ("delete".equals(type)) {
            return listService.deleteList(listId);
        }
        if ("quit".equals(type)) {
            return listService.quitList(listId);
        }
        if ("remove".equals(type)) {
            return listService.removeUser(listId);
        }
        return Result.error("Please choose type");
    }




    @ApiOperation("Update the invitation code of the list")
    @GetMapping("/updateInvitationCode")
    public Result updateInvitationCode(@RequestParam Long listId, @RequestParam String invitationCode) {
        return listService.updateInvitationCode(listId, invitationCode);
    }

    @ApiOperation("get all users' name of the list")
    @GetMapping("/userNames")
    public Result queryAllTheUserNames(@RequestParam Long listId) {
        return listService.queryAllTheUserNames(listId);
    }

    @ApiOperation("get all lists info of the user")
    @GetMapping("/lists")
    public Result queryAllTheLists(@RequestParam String userId) {
        return listService.queryAllTheLists(userId);
    }

    @ApiOperation("check whether current user is the creator of the list")
    @GetMapping("/isCreator")
    public Result isLeader(@RequestParam Long listId) {
        return listService.isLeader(listId);

    }
    @ApiOperation("query the name and leader of the list through invitation code")
    @GetMapping("/queryListInfo")
    public Result queryListNameAndLeaderByInvitationCode(@RequestParam String invitationCode){
        return listService.queryListNameAndLeaderByInvitationCode(invitationCode);
    }
}

