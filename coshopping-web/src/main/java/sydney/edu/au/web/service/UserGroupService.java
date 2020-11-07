package sydney.edu.au.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sydney.edu.au.base.Result;
import sydney.edu.au.web.entity.UserGroup;

/**
 * <p>
 * user group table service
 * </p>
 *
 * @author king
 * @since 2020-10-01
 */
public interface UserGroupService extends IService<UserGroup> {


    /**
     * Create a user group
     * @param userGroup
     * @return
     */
    Result createUserGroup(UserGroup userGroup);

    /**
     * Join a user group
     * @param userGroup
     * @return
     */
    Result joinUserGroup(UserGroup userGroup);

    /**
     * delete the entire user group
     * @param userGroupId
     * @return
     */
    Result deleteUserGroup(Long userGroupId);

    /**
     * quit a user group
     * @param userGroupId
     * @return
     */
    Result quitUserGroup(Long userGroupId);


    /**
     * remove a user from a group(set been_delete to 1)
     * @param userGroupId
     * @return
     */
    Result removeUser(Long userGroupId);



    Result updateUserGroup(Long userGroupId,String groupName,String invitationCode);


    /**
     * query a user group's id through its name and user Id
     * @param groupName
     * @return
     */
    Result queryUserGroupIdByNameAndUserId(String groupName,Long userId);


    /**
     * query a user group name through invitation code
     * @param invitationCode
     * @return
     */
    Result queryUserGroupNameByInvitationCode(String invitationCode);


    /**
     * query all the users in the user group
     * @param groupName
     * @return
     */
    Result queryAllUsers(String groupName);


    /**
     * query all the groups that have remove the user from the group
     * @param userId
     * @return
     */
    Result queryAllHaveBeenDeletedGroups(Long userId);



    Result getUserGroupsByUserId(Long userId);






}
