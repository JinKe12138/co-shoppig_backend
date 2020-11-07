package sydney.edu.au.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sydney.edu.au.base.Result;
import sydney.edu.au.web.entity.User;
import sydney.edu.au.web.entity.UserGroup;
import sydney.edu.au.web.mapper.UserGroupMapper;
import sydney.edu.au.web.service.UserGroupService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * user group table service impl
 * </p>
 *
 * @author king
 * @since 2020-10-01
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {

    @Autowired
    UserServiceImpl userService;

    @Override
    public Result createUserGroup(UserGroup userGroup) {

        if (userGroup == null || userGroup.getGroupName() == null) {
            return Result.error("User group can not be empty");
        }

        User user = userService.getUserById(userGroup.getUserId());
        if (user == null || user.getUserName() == null) {
            return Result.error("This user doesn't exist");
        }
        // Verify whether the submitted user group already exists
        Integer userGroupCount = getUserGroupByName(userGroup.getGroupName());
        if (userGroupCount != 0) {
            return Result.error("User group already exists");
        }
        userGroup.setIsGroupLeader(true);
        userGroup.setBeenDeleted(false);
        boolean b = this.save(userGroup);
        if (b) {
            return Result.ok();
        }
        return Result.error("Failed to create user group");
    }

    private Integer getUserGroupByName(String groupName) {
        QueryWrapper<UserGroup> query = new QueryWrapper<>();
        query.eq("group_name", groupName);
        return baseMapper.selectCount(query);

    }

    private UserGroup getUserGroupByNameAndUserId(String groupName, Long userId) {

        QueryWrapper<UserGroup> query = new QueryWrapper<>();
        query.eq("group_name", groupName).eq("user_id", userId);

        return baseMapper.selectOne(query);

    }

    private UserGroup getUserGroupById(Long userGroupId) {
        QueryWrapper<UserGroup> query = new QueryWrapper<>();
        query.eq("id", userGroupId);
        return baseMapper.selectOne(query);
    }

    private List<UserGroup> getAllUsersByName(String groupName) {
        QueryWrapper<UserGroup> query = new QueryWrapper<>();
        query.eq("group_name", groupName).eq("been_deleted", false);

        return baseMapper.selectList(query);

    }

    @Override
    public Result joinUserGroup(UserGroup userGroup) {

        if (userGroup == null || userGroup.getGroupName() == null) {
            return Result.error("User group can not be empty");
        }

        User user = userService.getUserById(userGroup.getUserId());
        if (user == null || user.getUserName() == null) {
            return Result.error("This user doesn't exist");
        }
        // Verify whether the submitted user group already exists
        Integer userGroupCount = getUserGroupByName(userGroup.getGroupName());
        if (userGroupCount == 0) {
            return Result.error("User group doesn't exist");
        }
        UserGroup userGroup1 = getUserGroupByNameAndUserId(userGroup.getGroupName(), userGroup.getUserId());
        if (userGroup1 != null) {
            return Result.error("This user is already in this group");
        }
        userGroup.setIsGroupLeader(false);
        userGroup.setBeenDeleted(false);
        userGroup.setInvitationCode(null);
        boolean b = this.save(userGroup);
        if (b) {
            return Result.ok();
        }
        return Result.error("Failed to join the user group");
    }

    @Override
    @Transactional
    public Result deleteUserGroup(Long userGroupId) {

        UserGroup userGroup = this.getById(userGroupId);
        if (userGroup == null) {
            return Result.error("User group doesn't exist");
        }
        List<UserGroup> users = getAllUsersByName(userGroup.getGroupName());
        for (UserGroup user : users) {
            boolean b = this.removeById(user);
            if (!b) {
                return Result.error("Failed to delete the user group");
            }
        }

        return Result.ok();

    }

    @Override
    public Result quitUserGroup(Long userGroupId) {

        UserGroup userGroup = this.getById(userGroupId);
        if (userGroup == null) {
            return Result.error("User group doesn't exist");
        }
        boolean b = this.removeById(userGroupId);
        if (b) {
            return Result.ok();
        }
        return Result.error("Failed to join the user group");
    }

    @Override
    public Result removeUser(Long userGroupId) {
        UserGroup userGroup = this.getById(userGroupId);

        if (userGroup == null) {
            return Result.error("User group doesn't exist");
        }
        UserGroup userGroup1 = userGroup.setBeenDeleted(true);
        boolean b = this.updateById(userGroup1);
        if (b) {
            return Result.ok();
        }
        return Result.error("Failed to remove the user");
    }

    @Override
    @Transactional
    public Result updateUserGroup(Long userGroupId, String groupName, String invitationCode) {


        // Verify whether the submitted user group already exists
        UserGroup userGroup = this.getById(userGroupId);
        if (userGroup == null) {
            return Result.error("User group doesn't exist");
        }


        if (invitationCode != null && userGroup.getIsGroupLeader()) {
            userGroup.setInvitationCode(invitationCode);
            boolean b = this.updateById(userGroup);
            if (!b) {
                Result.error("Failed to update the user group");
            }
        }
        if (groupName != null) {
            List<UserGroup> userGroups = getAllUsersByName(userGroup.getGroupName());
            for (UserGroup group : userGroups) {
                group.setGroupName(groupName);
                boolean b = this.updateById(group);
                if (!b) {
                    Result.error("Failed to update the user group");
                }
            }


        }

        return Result.ok();
    }

    @Override
    public Result queryUserGroupIdByNameAndUserId(String groupName, Long userId) {

        UserGroup userGroup = getUserGroupByNameAndUserId(groupName, userId);

        if (userGroup != null) {
            return Result.ok(userGroup.getId());
        }

        return Result.error("No such user group info");


    }

    @Override
    public Result queryUserGroupNameByInvitationCode(String invitationCode) {
        QueryWrapper<UserGroup> query = new QueryWrapper<>();
        query.eq("invitation_code", invitationCode);
        UserGroup userGroup = baseMapper.selectOne(query);
        if (userGroup != null) {
            return Result.ok(userGroup.getGroupName());
        }
        return Result.error("No such user group");
    }

    @Override
    public Result queryAllUsers(String groupName) {

        Integer userGroupCount = getUserGroupByName(groupName);
        if (userGroupCount == 0) {
            return Result.error("User group doesn't exist");
        }

        List<UserGroup> allUsers = getAllUsersByName(groupName);

        if (allUsers != null) {
            ArrayList<Long> userIds = new ArrayList<>();
            for (UserGroup userGroup : allUsers) {
                userIds.add(userGroup.getUserId());
            }
            return Result.ok(userIds);
        }
        return Result.error("This group is empty");
    }

    @Override
    public Result queryAllHaveBeenDeletedGroups(Long userId) {
        QueryWrapper<UserGroup> query = new QueryWrapper<>();
        query.eq("user_id", userId).eq("been_deleted", 1);

        List<UserGroup> userGroups = baseMapper.selectList(query);
        if (userGroups == null) {
            return Result.ok("No group have remove the user");
        }
        ArrayList<String> userGroupNames = new ArrayList<>();
        for (UserGroup userGroup : userGroups) {
            userGroupNames.add(userGroup.getGroupName());
        }
        return Result.ok(userGroupNames);
    }

    @Override
    public Result getUserGroupsByUserId(Long userId) {
        QueryWrapper<UserGroup> query = new QueryWrapper<>();
        query.eq("user_id", userId).eq("been_deleted", 0);
        List<UserGroup> userGroups = baseMapper.selectList(query);
        if (userGroups == null) {
            return Result.ok("This user doesn't join any group");
        }
        ArrayList<String> userGroupNames = new ArrayList<>();
        for (UserGroup userGroup : userGroups) {
            userGroupNames.add(userGroup.getGroupName());
        }
        return Result.ok(userGroupNames);
    }
}