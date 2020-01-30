package com.example.mychatappnetty.controller;

import com.example.mychatappnetty.entity.Users;
import com.example.mychatappnetty.entity.dto.JsonResult;
import com.example.mychatappnetty.entity.dto.bo.UsersBO;
import com.example.mychatappnetty.entity.dto.vo.UsersVO;
import com.example.mychatappnetty.service.UserService;
import com.example.mychatappnetty.util.FastDFSClient;
import com.example.mychatappnetty.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/upload")
public class UploadFileController {

  @Autowired private FastDFSClient fastDFSClient;

  @Autowired private UserService userService;

  /**
   * 1. Get the Base64 Image String, convert to MultipartFile, * *
   *
   * <p>2. Upload Image to the FastDFS File Server. * *
   *
   * <p>3. Get the profile image and its Thumbnail url. * *
   *
   * <p>4. updated user information with new images. * *
   *
   * <p>return updatedUser information Back to User.
   *
   * @param usersBO
   * @return
   */
  @PostMapping("/uploadmomentbackgroundImg")
  @ResponseBody
  public JsonResult uploadBackgroundImgBase64(@RequestBody UsersBO usersBO) {

    String base64Data = usersBO.getMomentImgData();

    // String userFacePath = "C:\\" + usersBO.getUserId() + "userface64.png";

    MultipartFile bgFile =
        FileUtil.base64ToMultipart(
            base64Data, usersBO.getUserId() + "moment_background.png", "image/png");

    String bgImgURL = null;

    try {
      bgImgURL = fastDFSClient.uploadFile(bgFile);
    } catch (IOException e) {
      e.printStackTrace();
      return JsonResult.errorMsg("upload Image fail, try later");
    }

    // Update User by condition: Upload user profile img.
    Users userCondition =
        Users.builder().id(usersBO.getUserId()).momentBackgroundImg(bgImgURL).build();

    Users updatedUser = null;
    try {
      updatedUser = userService.updateUserStatus(userCondition);
    } catch (Exception e) {
      return JsonResult.errorMsg("upload Image fail, try later");
    }

    // Display info in the front-end
    UsersVO usersVO = new UsersVO();
    BeanUtils.copyProperties(updatedUser, usersVO);

    return JsonResult.ok(updatedUser);
  }

  @PostMapping("/uploadprofileimg")
  @ResponseBody
  public JsonResult uploadProfileImgBase64(@RequestBody UsersBO usersBO) {
    String base64Data = usersBO.getFaceData();

    // String userFacePath = "C:\\" + usersBO.getUserId() + "userface64.png";

    MultipartFile profileFile =
        FileUtil.base64ToMultipart(base64Data, usersBO.getUserId() + "userface64.png", "image/png");

    String profileImgURL = null;

    try {
      profileImgURL = fastDFSClient.uploadFile2(profileFile);
    } catch (IOException e) {
      e.printStackTrace();
      return JsonResult.errorMsg("upload Image fail, try later");
    }

    // e.g		"dhawuidhwaiuh3u89u98432.png"
    // e.g		"dhawuidhwaiuh3u89u98432_80x80.png"

    // Thumbnail url
    String thump = "_80x80.";
    String arr[] = profileImgURL.split("\\.");
    String thumpImgUrl = arr[0] + thump + arr[1];

    // Update User by condition: Upload user profile img.
    Users userCondition = new Users();
    userCondition.setId(usersBO.getUserId());
    userCondition.setFaceImage(thumpImgUrl);
    userCondition.setFaceImageBig(profileImgURL);

    Users updatedUser = null;
    try {
      updatedUser = userService.updateUserStatus(userCondition);
    } catch (Exception e) {
      return JsonResult.errorMsg("upload Image fail, try later");
    }

    // Display info in the front-end
    UsersVO usersVO = new UsersVO();
    BeanUtils.copyProperties(updatedUser, usersVO);

    return JsonResult.ok(updatedUser);
  }
}
