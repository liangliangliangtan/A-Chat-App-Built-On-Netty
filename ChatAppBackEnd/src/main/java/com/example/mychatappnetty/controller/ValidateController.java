package com.example.mychatappnetty.controller;


import com.example.mychatappnetty.entity.Users;
import com.example.mychatappnetty.entity.Validates;
import com.example.mychatappnetty.entity.dto.JsonResult;
import com.example.mychatappnetty.enums.StateEnum;
import com.example.mychatappnetty.enums.TimeEnum;
import com.example.mychatappnetty.service.MailService;
import com.example.mychatappnetty.service.UserService;
import com.example.mychatappnetty.service.ValidateService;

import com.example.mychatappnetty.util.MD5Util;
import com.example.mychatappnetty.util.TimeUtil;
import com.example.mychatappnetty.util.UUIDUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.concurrent.Future;

@RestController
@RequestMapping(value = "/validate")
public class ValidateController {


    private static final int MAX_REQUEST_PER_DAY = 3;

    @Autowired
    private ValidateService validateService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;


    /**
     * Send validation email for  user password reset
     * @param user  only contains field user email
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/sendvalidationemail")
    @ResponseBody
    public JsonResult sendValidationEmail(@RequestBody Users user, HttpServletRequest httpServletRequest) {

        user.setStatus(StateEnum.ACTIVE.getStateCode());
        Users userTarget = userService.searchUserByCondition(user);
        if (userTarget == null) {
            return JsonResult.errorMsg("This email has no account");
        }

        user.setId(userTarget.getId());
        // An user can only reset password for 3 times and the effective time for each email is one Hour
        if (validateService.validateLimitation(user.getUserEmail(), MAX_REQUEST_PER_DAY, TimeEnum.ONE_HOUR)) {

            Validates newValidates = new Validates();
            String token = UUIDUtil.generateUUID();
            newValidates.setResetToken(token);

            String baseURL = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName();

            String subject = "[FancyChatAPP] Reset-Password: ";

            String content = "Please click this link to reset your password : " + "\n" +
                              baseURL + "/validate/reset?token=" + token + "\n" +
                              "Please reset your Password in one hour";

            Future<Boolean> mailFuture = mailService.sendSimpleMail(user.getUserEmail(),subject,content);
            validateService.insertNewResetRecord(newValidates, user, token);

            while(!mailFuture.isDone());

            try {
                if(mailFuture.get()){
                    return JsonResult.ok();
                }else{
                    return JsonResult.errorMsg("Send email failed");
                }
            } catch (Exception e) {
                return JsonResult.errorMsg(e.getMessage());
            }
        } else {
            return JsonResult.errorMsg("Your operation is too frequent. Please try again later");
        }
    }

    
    @PostMapping("/resetpassword")
    @ResponseBody
    public JsonResult resetPassword(@RequestParam("password") String password,
                                    @RequestParam("token") String token,
                                    @RequestParam("confirmedPassword") String confirmedPassword) throws Exception {
        if(password == null || password.isEmpty()
                || confirmedPassword == null || confirmedPassword.isEmpty()){
            return JsonResult.errorMsg("Password cannot be empty");
        }

        if(!confirmedPassword.equals(password)){
            return JsonResult.errorMsg("Password and Confirmed Password are not the same");
        }

        Validates validates = validateService.findUserByResetToken(token);

        if(validates == null) return JsonResult.errorMsg("The reset request is forbidden");

        if(!TimeUtil.isSameTime(validates.getModifiedTime(), validates.getCreateTime())){
            return JsonResult.errorMsg("The token is no longer valid,  Please try again later");
        }

        // update user password and update the validate modified time.
        Users userCondition = new Users();
        userCondition.setId(validates.getUserId());
        userCondition.setPassword(MD5Util.getMD5Str(password));
        userService.updateUserStatus(userCondition);

        validates.setModifiedTime(new Date());
        validateService.updateValidate(validates);

        return JsonResult.ok();
    }
}
