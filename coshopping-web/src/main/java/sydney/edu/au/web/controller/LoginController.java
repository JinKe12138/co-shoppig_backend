package sydney.edu.au.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sydney.edu.au.base.Result;
import sydney.edu.au.web.req.PasswordREQ;
import sydney.edu.au.web.service.UserService;

/**
 * @author king
 * @date 2020-10-01 16:40
 * @description
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * Verify whether the original password is correct
     *
     * @param req
     * @return
     */
    @ApiOperation("Verify whether the original password is correct")
    @PostMapping("/pwd")
    public Result checkPwd(@RequestBody PasswordREQ req) {
        return userService.checkPassword(req);
    }

    /**
     * Submit password change
     *
     * @param req
     * @return
     */
    @ApiOperation("Submit password change")
    @PutMapping("/pwd")
    public Result updatePwd(@RequestBody PasswordREQ req) {
        return userService.updatePassword(req);
    }

    /**
     * login
     *
     * @return
     */
    @GetMapping("/login")
    public Result login(@RequestParam String userName,@RequestParam String password) {
        return userService.login(userName, password);
    }


    @GetMapping("/securityCode")
    public Result sendEmailSecurityCode(@RequestParam String email) {
        return userService.sendEmailSecurityCode(email);

    }

    @GetMapping("/verifyCode")
    Result verifySecurityCode(@RequestParam String securityCode,@RequestParam Long userId){
        return userService.verifySecurityCode(securityCode, userId);
    }

//    /**
//     * Obtain user information through token
//     *
//     * @param token
//     * @return
//     */
//    @ApiOperation("Obtain user information through token")
//    @GetMapping("/info")
//    public Result getUserInfo(@RequestParam("token") String token) {
//        return userService.getUserInfo(token);
//    }

//    @PostMapping("/logout")
//    public Result logout() {
//        return Result.ok();
//    }

}
