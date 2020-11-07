package sydney.edu.au.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sydney.edu.au.base.Result;
import sydney.edu.au.web.entity.CoList;

/**
 * <p>
 * list table service
 * </p>
 *
 * @author king
 * @since 2020-11-03
 */
public interface CoListService extends IService<CoList> {

    /**
     * Create a coList
     * @param coList
     * @return
     */
    Result createList(CoList coList);

    /**
     * Join a list
     * @param name
     * @return
     */
    Result joinList(Long userId,String name, String invitationCode);

    /**
     * delete the entire list
     * @param listId
     * @return
     */
    Result deleteList(Long listId);

    /**
     * quit a list
     * @param listId
     * @return
     */
    Result quitList(Long listId);


    /**
     * remove a user from a list(set been_delete to 1)
     * @param listId
     * @return
     */
    Result removeUser(Long listId);


    Result updateInvitationCode(Long listId ,String invitationCode);

    Result queryAllTheUserNames(Long listId);

    Result queryAllTheLists(String userId);

    Result isLeader(Long listId);

    Result queryListNameAndLeaderByInvitationCode(String invitationCode);
}
