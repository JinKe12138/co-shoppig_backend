package sydney.edu.au.web.service;

import sydney.edu.au.base.Result;
import sydney.edu.au.web.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import sydney.edu.au.web.req.PasswordREQ;

/**
 * <p>
 * user info table service
 * </p>
 *
 * @author king
 * @since 2020-10-01
 */
public interface UserService extends IService<User> {
    Result add(User user);




    /**
     * Query all users with similar names
     * @param userName
     * @return
     */
    Result queryUsers(String userName);

    /**
     * Verify whether the original password is correct
     * @param req
     * @return
     */
    Result checkPassword(PasswordREQ req);

    /**
     * Update password
     * @param req
     * @return
     */
    Result updatePassword(PasswordREQ req);

    Result login(String username, String password);


    /**
     * get user info through token
     * @param token
     * @return
     */
    Result getUserInfo(String token);

    /**
     * get all the users
     * @return
     */
    Result getAllUserInfo();


    Result getUserIdByName(String name);

    Result getUserIdByEmail(String email);


    Result sendEmailSecurityCode(String email);


    Result verifySecurityCode(String securityCode,Long userId);



}
