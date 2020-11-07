package sydney.edu.au.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sydney.edu.au.base.Result;
import sydney.edu.au.util.JwtUtil;
import sydney.edu.au.web.entity.User;
import sydney.edu.au.web.mapper.UserMapper;
import sydney.edu.au.web.req.PasswordREQ;
import sydney.edu.au.web.service.UserService;

import java.util.List;
import java.util.Map;

import static sydney.edu.au.web.util.VerCodeGenerateUtil.generateVerCode;

/**
 * <p>
 * user info table service impl
 * </p>
 *
 * @author king
 * @since 2020-10-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Result add(User user) {
        if (user == null || user.getUserName() == null) {
            return Result.error("Username can not be empty");
        }
        // 1. Verify whether the submitted account already exists
        User s = getByUsername(user.getUserName());
        if (s != null) {
            return Result.error("Username already exists");
        }
        User user1 = getUserByEmail(user.getEmail());
        if (user1 != null) {
            return Result.error("Email already exists");
        }

        // Encrypt the submitted password
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);
        // save data
        boolean b = this.save(user);
        if (b) {
            return Result.ok();
        }
        return Result.error("Failed to add user");
    }


    @Override
    public Result queryUsers(String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("user_name", userName);
        List<Map<String, Object>> maps = baseMapper.selectMaps(wrapper);
        if (maps.isEmpty()) {
            return Result.error("No such user");
        }

        return Result.ok(maps);
    }

    @Override
    public Result checkPassword(PasswordREQ req) {
        if (req == null || req.getPassword() == null) {
            return Result.error("The original password cannot be empty");
        }

        // Query user information by user id (correct password)
        User user = baseMapper.selectById(req.getUserId());

        // Verify whether the password is correct
        boolean b = new BCryptPasswordEncoder().matches(req.getPassword(), user.getPassword());
        if (b) {
            return Result.ok();
        }
        return Result.error("The original password is wrong");
    }

    @Override
    public Result updatePassword(PasswordREQ req) {
        if (req == null || req.getPassword() == null) {
            return Result.error("New password cannot be empty");
        }

        // Encrypt the new password
        String password = new BCryptPasswordEncoder().encode(req.getPassword());

        // Update operation
        User user = baseMapper.selectById(req.getUserId());
        user.setPassword(password);
        baseMapper.updateById(user);
        return Result.ok();
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Result login(String username, String password) {
        Result error = Result.error("Wrong user name or password");

        if (username == null
                || password == null) {
            return error;
        }

        // 1. Query data by username
        User user = getByUsername(username);
        // User does not exist
        if (user == null) {
            return error;
        }

        // 2. Exist, judge whether the entered password is consistent with the database password
        boolean b = new BCryptPasswordEncoder().matches(password, user.getPassword());
        if (!b) {
            return error;
        }

//        // 3. generate token
//        String jwt =
//                jwtUtil.createJWT(user.getUserId() + "", user.getUserName(), true);
//        // 4. Respond to the client
//        Map<String, String> map = new HashMap<>();
//        map.put("token", jwt);

        return Result.ok("Login success");
    }

    @Override
    public Result getUserInfo(String token) {
        // Parse the token
        Claims claims = jwtUtil.parseJWT(token);
        // If it cannot be obtained, or if the user name is empty, it will return to get failed
        if (claims == null ||
                claims.getSubject() == null) {
            return Result.error("Failed to obtain user token");
        }

        // Get username
        String username = claims.getSubject();
        // Query corresponding user information by user name
        User user = getByUsername(username);
        if (user == null) {
            return Result.error("User does not exist");
        }

        // Set the password to null, for security
        user.setPassword(null);

        return Result.ok(user);
    }

    @Override
    public Result getAllUserInfo() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.isNotNull("user_id");
        List<User> users = baseMapper.selectList(query);
        return Result.ok(users);
    }

    @Override
    public Result getUserIdByName(String name) {
        User user = getByUsername(name);

        if (user != null) {
            return Result.ok(user.getUserId());
        }

        return Result.error("user not exist");
    }

    @Override
    public Result getUserIdByEmail(String email) {
        User user = getUserByEmail(email);

        if (user != null) {
            return Result.ok(user.getUserId());
        }

        return Result.error("user not exist");
    }




    @Autowired
    JavaMailSenderImpl mailSender;

    /**
     * 从配置文件中获取发件人
     */
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    @Transactional
    @Async
    public Result sendEmailSecurityCode(String email) {

        User user = getUserByEmail(email);
        if (user == null) {
            return Result.error("User doesn't exists");
        }

        try {
            String verCode = generateVerCode();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("security code");    //设置邮件标题
            message.setText("Dear "+user.getUserName()+"，Hello:\n"
                    + "\nThe email verification code for this request is: " + verCode + "（Do not disclose this code）\n"
                    + "\nIf you are not doing it yourself, please ignore the email.\n(This is an automatically sent email, please do not reply directly）");    //设置邮件正文
            message.setTo(email);    //设置收件人
            message.setFrom(sender);    //设置发件人
            mailSender.send(message);    //发送邮件

            user.setSecurityCode(verCode);
            boolean b = this.updateById(user);
            if (b) {
                return Result.ok(user.getUserId());
            }
            return Result.error("Fail to save security code");
        } catch (Exception e) {
            return Result.error("Fail to send Email");
        }


    }

    @Override
    public Result verifySecurityCode(String securityCode, Long userId) {

        User user = baseMapper.selectById(userId);
        if (user == null) {
            return Result.error("User doesn't exist");
        }
        if (user.getSecurityCode().equals(securityCode)) {
            return Result.ok("verify success");
        }
        return Result.error("verify fail");
    }

    public User getUserByEmail(String email) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("email", email);
        return baseMapper.selectOne(query);

    }


    public User getUserById(Long userId) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("user_id", userId);
        return baseMapper.selectOne(query);

    }


    public User getByUsername(String username) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("user_name", username);
        return baseMapper.selectOne(query);
    }
    public String getUserNameById(Long userId){
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("user_id", userId);
        return baseMapper.selectOne(query).getUserName();
    }


}
