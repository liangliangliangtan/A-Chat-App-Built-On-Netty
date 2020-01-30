package com.example.mychatappnetty.controller;

import com.example.mychatappnetty.entity.Users;
import com.example.mychatappnetty.entity.dto.JsonResult;
import com.example.mychatappnetty.entity.dto.bo.UsersBO;
import com.example.mychatappnetty.entity.dto.vo.MyFriendsVO;
import com.example.mychatappnetty.entity.dto.vo.UsersVO;
import com.example.mychatappnetty.enums.OperatorFriendRequestTypeEnum;
import com.example.mychatappnetty.enums.SearchFriendsStatusEnum;
import com.example.mychatappnetty.enums.StateEnum;
import com.example.mychatappnetty.enums.TimeEnum;
import com.example.mychatappnetty.service.ChatService;
import com.example.mychatappnetty.service.FriendRequestService;
import com.example.mychatappnetty.service.MailService;
import com.example.mychatappnetty.service.UserService;
import com.example.mychatappnetty.util.EmailUtil;
import com.example.mychatappnetty.util.MD5Util;
import com.example.mychatappnetty.util.TimeUtil;
import com.example.mychatappnetty.util.VerfiyCodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private ChatService chatService;

    /**
     * @Description: User register
     */
    @PostMapping("/register")
    @ResponseBody
    public JsonResult register(
        @RequestBody UsersBO usersBo, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        Users user = new Users();
        BeanUtils.copyProperties(usersBo, user);
        String Vcode = usersBo.getVcode();
        // 0. verify user name, user password, email and vcode;
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return JsonResult.errorMsg("User name and password can not be empty");
        }

        if (StringUtils.isBlank(user.getUserEmail())) {
            return JsonResult.errorMsg("Must enter your email address");
        }

        if (Vcode == null || Vcode.isEmpty()) {
            return JsonResult.errorMsg("Please get vcode first");
        }

        Users userCondition = new Users();
        userCondition.setUsername(user.getUsername());
        userCondition.setStatus(StateEnum.ACTIVE.getStateCode());

        // 1.1 search by username
        Users searchedUser = userService.searchUserByCondition(userCondition);
        if (searchedUser != null) {
            return JsonResult.errorMsg("UserName has been used");
        }

        // 1.2 search by userEmail
        userCondition.setUsername(null);
        userCondition.setUserEmail(user.getUserEmail());
        searchedUser = userService.searchUserByCondition(userCondition);
        if (searchedUser != null) {
            return JsonResult.errorMsg("One email can only have one account");
        }
        // 2. validate the Vcode.
        String vcode = Vcode.trim();
        String vcodeTime = (String) request.getSession().getAttribute("vcodeTime");
        String[] vcodeArr = VerfiyCodeUtil.decodeVcode(vcodeTime);

        if (!vcodeArr[0].equals(vcode)) {
            return JsonResult.errorMsg("verification code is not correct");
        }

        if (TimeUtil.isDifferenceGreaterThanTarget(vcodeArr[1], TimeEnum.TEN_MIN)) {
            return JsonResult.errorMsg("Verification code is invalid");
        }

        // TODO: register, add QR Code, add default Profile Img
        Users userResult = userService.register(user);

        // Display info in the front-end
        UsersVO usersVO = new UsersVO();

        BeanUtils.copyProperties(userResult, usersVO);

        return JsonResult.ok(usersVO);
    }

    /**
     * Get the Vs Code so as to bind the user and his/her email
     */
    @GetMapping("/getVCode")
    @ResponseBody
    public JsonResult getVCode(HttpServletRequest request, HttpServletResponse response)
        throws ExecutionException, InterruptedException {

        response.setHeader("Access-Control-Allow-Credentials", "true");
        // 0. check user email
        if (request.getParameter("userEmail") == null) {
            return JsonResult.errorMsg("Must enter your email address");
        }

        String email = request.getParameter("userEmail");

        if (!EmailUtil.checkValidEmail(email)) {
            // check email via regular expression for pattern
            return JsonResult.errorMsg("Please enter valid email");
        }

        Users userCondition = new Users();
        userCondition.setStatus(StateEnum.ACTIVE.getStateCode());
        // search by email
        userCondition.setUserEmail(email);
        Users searchedUser = userService.searchUserByCondition(userCondition);
        if (searchedUser != null) {
            return JsonResult.errorMsg("Email has been used");
        }

        // 1. generate verification code
        Integer code = VerfiyCodeUtil.generateVCode();

        // 2. send text by email asynchronous
        String text =
            "Thanks for register, Your verification code is : "
                + code
                + "<br/>"
                + "Please finish registration in 10 minutes.";

        Future<Boolean> mailFuture = mailService.sendHtmlMail(email, "Verification Code", text);

        while (!mailFuture.isDone()) {
            ;
        }

        if (mailFuture.get()) {
            // 3.add to session, recode time and verification code.
            request.getSession().setAttribute("vcodeTime", VerfiyCodeUtil.encodeVCode(code));
            return JsonResult.ok();
        } else {
            return JsonResult.errorMsg("Error When sending mail, Please connect with admin");
        }
    }

    /**
     * @title
     * @description
     * @author admin
     * @updateTime 2019/11/27 11:43
     */
    @PostMapping("/login")
    @ResponseBody
    public JsonResult login(@RequestBody Users user) {
        // 0. verify user name, user password, email and vcode;
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return JsonResult.errorMsg("User name and password can not be empty");
        }

        Users userCondition = new Users();
        userCondition.setUsername(user.getUsername());

        try {
            userCondition.setPassword(MD5Util.getMD5Str(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.errorMsg(e.getMessage());
        }

        Users userResult = userService.searchUserByCondition(userCondition);

        if (userResult == null) {
            return JsonResult.errorMsg("UserName or Password is not correct");
        }

        // Display info in the front-end
        UsersVO usersVO = new UsersVO();

        BeanUtils.copyProperties(userResult, usersVO);

        return JsonResult.ok(usersVO);
    }

    /**
     * Modify UserNickName by its account Id.
     */
    @PostMapping("/setnickname")
    @ResponseBody
    public JsonResult modifyUserNickName(@RequestBody UsersBO usersBO) {
        if (usersBO.getNickname() == null || usersBO.getNickname().length() == 0) {
            return JsonResult.errorMsg("NickName cannot be empty");
        }

        Users userCondition = new Users();

        userCondition.setId(usersBO.getUserId());
        userCondition.setNickname(usersBO.getNickname());

        Users userResult = userService.updateUserStatus(userCondition);

        // Display info in the front-end
        UsersVO usersVO = new UsersVO();

        BeanUtils.copyProperties(userResult, usersVO);

        return JsonResult.ok(usersVO);
    }

    /**
     * Search well-matched user You cannot add other users if some conditions do not meet when meet
     * the conditions, search the user the client want to add and return it to the front-end
     */
    @PostMapping("/searchuser")
    @ResponseBody
    public JsonResult searchUser(
        @RequestParam("fromUserId") String fromUserId,
        @RequestParam("toUserName") String toUserName) {
        if (fromUserId == null
            || toUserName == null
            || fromUserId.length() == 0
            || toUserName.length() == 0) {
            return JsonResult.errorMsg("cannot be empty via searching");
        }

        Integer stateCode = userService.preConditionSearchFriend(fromUserId, toUserName);

        if (stateCode != SearchFriendsStatusEnum.SUCCESS.getStatus()) {
            return JsonResult.errorMsg(SearchFriendsStatusEnum.getMsgByState(stateCode));
        }

        Users userResult =
            userService.searchUserByCondition(Users.builder().username(toUserName).build());
        // Display info in the front-end
        UsersVO usersVO = new UsersVO();

        BeanUtils.copyProperties(userResult, usersVO);

        return JsonResult.ok(usersVO);
    }

    @PostMapping("/sendfriendshiprequest")
    @ResponseBody
    public JsonResult addFriendRequest(
        @RequestParam("fromUserId") String fromUserId,
        @RequestParam("toUserName") String toUserName) {
        if (fromUserId == null
            || toUserName == null
            || fromUserId.length() == 0
            || toUserName.length() == 0) {
            return JsonResult.errorMsg("cannot be empty via searching");
        }

        Integer stateCode = userService.preConditionSearchFriend(fromUserId, toUserName);

        if (stateCode != SearchFriendsStatusEnum.SUCCESS.getStatus()) {
            return JsonResult.errorMsg(SearchFriendsStatusEnum.getMsgByState(stateCode));
        }

        Users acceptUser =
            userService.searchUserByCondition(Users.builder().username(toUserName).build());

        Integer affectRow = friendRequestService
            .sentFriendShipRequest(fromUserId, acceptUser.getId());

        if (affectRow == null) {
            return JsonResult.errorMsg("Have already been added to your contact");
        }

        if (affectRow == -1) {
            return JsonResult.errorMsg("Add contact failed, please try again");
        }

        return JsonResult.ok();
    }

    @PostMapping("/getfriendsrequestlist")
    @ResponseBody
    public JsonResult getFriendsRequestList(@RequestParam("userId") String acceptUserId) {
        if (acceptUserId == null || acceptUserId.length() == 0) {
            return JsonResult.errorMsg("User Id can not be empty");
        }

        return JsonResult.ok(friendRequestService.getFriendsRequestList(acceptUserId));
    }

    @PostMapping("/operfriendship")
    @ResponseBody
    public JsonResult operateFriendshipRequest(
        @RequestParam("senderUserId") String senderUserId,
        @RequestParam("acceptUserId") String acceptUserId,
        @RequestParam("operatorType") String operatorType) {
        if (StringUtils.isBlank(senderUserId) || StringUtils.isBlank(acceptUserId)) {
            return JsonResult.errorMsg("id cannot be empty");
        }

        int operator = Integer.valueOf(operatorType);

        if (operator == OperatorFriendRequestTypeEnum.IGNORE.type) {
            int affectRow = friendRequestService
                .deleteFriendShipRequest(senderUserId, acceptUserId);

            if (affectRow == -1) {
                return JsonResult.errorMsg("please try again");
            }

            return JsonResult.ok();
        }

        userService.acceptUserRequest(senderUserId, acceptUserId);

        return JsonResult.ok();
    }

    @GetMapping("/myFriends")
    @ResponseBody
    public JsonResult getMyFriendsList(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getParameter("userId") == null) {
            return JsonResult.errorMsg("");
        }

        String userId = httpServletRequest.getParameter("userId");

        List<MyFriendsVO> myFriendsList = userService.getFriendsByUserId(userId);

        return JsonResult.ok(myFriendsList);
    }

    /**
     * Get user offline messages
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/getUnreadMessages")
    @ResponseBody
    public JsonResult getUnreadMessages(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getParameter("acceptUserId") == null) {
            return JsonResult.errorMsg("");
        }
        String acceptUserId = httpServletRequest.getParameter("acceptUserId");
        if (acceptUserId == null || acceptUserId.length() == 0) {
            return JsonResult.errorMsg("User Id can not be empty");
        }

        return JsonResult.ok(chatService.queryUnreadChatMsg(acceptUserId));
    }
}
