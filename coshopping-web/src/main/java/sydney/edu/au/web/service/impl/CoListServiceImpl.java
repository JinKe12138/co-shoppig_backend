package sydney.edu.au.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sydney.edu.au.base.Result;
import sydney.edu.au.web.entity.CoList;
import sydney.edu.au.web.entity.Item;
import sydney.edu.au.web.entity.User;
import sydney.edu.au.web.mapper.CoListMapper;
import sydney.edu.au.web.service.CoListService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * list table service impl
 * </p>
 *
 * @author king
 * @since 2020-11-03
 */
@Service
@Slf4j
public class CoListServiceImpl extends ServiceImpl<CoListMapper, CoList> implements CoListService {

    @Autowired
    UserServiceImpl userService;


    @Override
    public Result createList(CoList coList) {
        if (coList == null || coList.getName() == null) {
            return Result.error("List can not be empty");
        }

        User user = userService.getUserById(coList.getUserId());
        if (user == null || user.getUserName() == null) {
            return Result.error("This user doesn't exist");
        }
        // Verify whether the submitted list already exists
        CoList coList1 = getSameList(coList.getName(), coList.getUserId());
        if (coList1 != null) {
            return Result.error("You can not create two lists with same name");
        }
        coList.setIsLeader(true);
        coList.setBeenDeleted(false);
        boolean b = this.save(coList);
        if (b) {
            return Result.ok();
        }
        return Result.error("Failed to create List");
    }

    private CoList getSameList(String name, Long userId) {

        QueryWrapper<CoList> query = new QueryWrapper<>();
        query.eq("name", name).eq("user_id", userId).eq("is_leader", true);

        return baseMapper.selectOne(query);

    }

    public Long getListLeader(String name, String invitationCode) {
        QueryWrapper<CoList> query = new QueryWrapper<>();
        query.eq("name", name).eq("invitation_code", invitationCode).eq("is_leader", true);

        CoList coList = baseMapper.selectOne(query);
        Long userId = coList.getUserId();

        return userId;
    }


    private ArrayList<Long> getAllTheUsers(String name, String invitationCode) {

        QueryWrapper<CoList> query = new QueryWrapper<>();
        query.eq("name", name).eq("invitation_code", invitationCode).eq("been_deleted", false);


        java.util.List<CoList> coLists = baseMapper.selectList(query);
        ArrayList<Long> users = new ArrayList<>();
        for (CoList coList : coLists) {
            users.add(coList.getUserId());
        }

        return users;

    }

    private ArrayList<Long> getAllTheListIds(String name, String invitationCode) {

        QueryWrapper<CoList> query = new QueryWrapper<>();
        query.eq("name", name).eq("invitation_code", invitationCode).eq("been_deleted", false);


        java.util.List<CoList> coLists = baseMapper.selectList(query);
        ArrayList<Long> listIds = new ArrayList<>();
        for (CoList coList : coLists) {
            listIds.add(coList.getId());
        }

        return listIds;

    }

    private Integer getListTypeByNameAndInvitationCode(String name, String invitationCode) {

        QueryWrapper<CoList> query = new QueryWrapper<>();
        query.eq("name", name).eq("invitation_code", invitationCode).eq("is_leader", true);

        return baseMapper.selectOne(query).getType();
    }

    @Override
    public Result joinList(Long userId, String name, String invitationCode) {

        ArrayList<Long> users = getAllTheUsers(name, invitationCode);
        if (users.isEmpty()) {
            return Result.error("CoList doesn't exist");
        }
        for (Long Id : users) {
            if (Id.equals(userId)) {
                return Result.error("You are already in this list");
            }
        }
        CoList coList = new CoList(userId, name, invitationCode, getListTypeByNameAndInvitationCode(name, invitationCode));
        boolean b = this.save(coList);
        if (b) {
            return Result.ok();
        }
        return Result.error("Failed to join the List");
    }

    @Autowired
    ItemServiceImpl ItemServiceImpl;

    @Override
    @Transactional
    public Result deleteList(Long listId) {

        CoList coList = baseMapper.selectById(listId);
        ArrayList<Long> listIds = getAllTheListIds(coList.getName(), coList.getInvitationCode());
        if (listIds.isEmpty()) {
            return Result.error("CoList doesn't exist");
        }
        boolean b = this.removeByIds(listIds);
        if (!b) {
            return Result.error("Failed to delete the List");
        }
        List<Item> allItems = ItemServiceImpl.getAllItems(coList.getInvitationCode());
        if (!allItems.isEmpty()) {
            boolean b1 = ItemServiceImpl.deleteAll(coList.getInvitationCode());
            if (!b1) {
                return Result.error("Failed to delete items in the list");
            }
        }
        return Result.ok();
    }

    @Override
    public Result quitList(Long listId) {

        CoList coList = baseMapper.selectById(listId);
        if (coList == null) {
            return Result.error("CoList doesn't exist");
        }
        boolean b = this.removeById(coList.getId());
        if (b) {
            return Result.ok();
        }
        return Result.error("Failed to quit the List");

    }


    @Override
    public Result removeUser(Long listId) {
        CoList coList = baseMapper.selectById(listId);
        if (coList == null) {
            return Result.error("CoList doesn't exist");
        }
        coList.setBeenDeleted(true);
        boolean b = this.updateById(coList);
        if (b) {
            return Result.ok();
        }
        return Result.error("Failed to quit the List");


    }

    public Integer fakeUpdate(CoList coList){
        coList.setIsLeader(true);

        return baseMapper.updateById(coList);
    }
    @Override
    @Transactional
    public Result updateInvitationCode(Long listId, String invitationCode) {
        CoList coList = baseMapper.selectById(listId);
        java.util.List<Item> items = ItemServiceImpl.getAllItems(coList.getInvitationCode());
        ArrayList<Long> listIds = getAllTheListIds(coList.getName(), coList.getInvitationCode());
        for (Long Id : listIds) {
            CoList coList1 = baseMapper.selectById(Id);
            coList1.setInvitationCode(invitationCode);
            boolean b = this.updateById(coList1);
            if (!b) {
                return Result.error("Failed to update the invitationCode");
            }
        }
        for (Item item : items) {
            item.setListCode(invitationCode);
            ItemServiceImpl.updateItem(item);
        }
        return Result.ok();
    }

    @Override
    public Result queryAllTheUserNames(Long listId) {
        CoList coList = baseMapper.selectById(listId);
        ArrayList<Long> allTheUsers = getAllTheUsers(coList.getName(), coList.getInvitationCode());
        if (allTheUsers.isEmpty()) {
            return Result.error("No such List");
        }
        ArrayList<String> userNames = new ArrayList<>();
        for (Long userId : allTheUsers) {
            userNames.add(userService.getUserNameById(userId));
        }
        return Result.ok(userNames);
    }

    @Override
    public Result queryAllTheLists(String userId) {
        QueryWrapper<CoList> query = new QueryWrapper<>();
        query.eq("user_id", userId).eq("been_deleted", false);
        java.util.List<CoList> coLists = baseMapper.selectList(query);
        if (coLists.isEmpty()) {
            return Result.error("You don't have any list");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<HashMap> result = new ArrayList<>();
        for (CoList coList : coLists) {
            HashMap<String, String> hashMap = new HashMap<>();
            Long leaderId = getListLeaderId(coList.getName(), coList.getInvitationCode());
            CoList leaderCoList = baseMapper.selectById(leaderId);
            hashMap.put("listId", String.valueOf(coList.getId()));
            hashMap.put("listName", coList.getName());
            hashMap.put("leaderId", String.valueOf(getLeaderUserId(leaderId)));
            hashMap.put("invitationCode", coList.getInvitationCode());
            hashMap.put("time", formatter.format(leaderCoList.getTime()));
            hashMap.put("type",leaderCoList.getType().toString());
            result.add(hashMap);
        }
        return Result.ok(result);
    }

    private Long getListLeaderId(String name, String invitationCode) {
        QueryWrapper<CoList> query = new QueryWrapper<>();
        query.eq("name", name).eq("invitation_code", invitationCode).eq("is_leader", true);

        CoList coList = baseMapper.selectOne(query);
        Long leaderId = coList.getId();

        return leaderId;
    }

    private Long getLeaderUserId(Long leaderListId) {
        CoList coList = baseMapper.selectById(leaderListId);


        return coList.getUserId();
    }

    @Override
    public Result isLeader(Long listId) {
        CoList coList = baseMapper.selectById(listId);
        if (coList == null) {
            return Result.error("List doesn't exist");
        }
        return Result.ok(coList.getIsLeader());
    }

    @Override
    public Result queryListNameAndLeaderByInvitationCode(String invitationCode) {

        CoList coList = getLeaderByInvitationCode(invitationCode);
        if (coList == null) {
            return Result.error("List doesn't exist");
        }
        String leaderName = userService.getUserNameById(coList.getUserId());
        HashMap<String, String> result = new HashMap<>();
        result.put("ListName", coList.getName());
        result.put("leaderName", leaderName);
        return Result.ok(result);

    }

    public CoList getLeaderByInvitationCode(String invitationCode) {
        QueryWrapper<CoList> query = new QueryWrapper<>();
        query.eq("invitation_code", invitationCode).eq("is_leader", true);

        return baseMapper.selectOne(query);
    }

}
