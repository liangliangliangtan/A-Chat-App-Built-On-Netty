package com.example.mychatappnetty.controller;

import com.example.mychatappnetty.entity.PostImg;
import com.example.mychatappnetty.entity.Posts;
import com.example.mychatappnetty.entity.dto.JsonResult;
import com.example.mychatappnetty.entity.dto.vo.PostVO;
import com.example.mychatappnetty.service.PostService;
import com.example.mychatappnetty.service.UserService;
import com.example.mychatappnetty.util.FastDFSClient;
import com.example.mychatappnetty.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    private FastDFSClient fastDFSClient;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @PostMapping("/send_post")
    @ResponseBody
    public JsonResult sendPost(HttpServletRequest request) {

        String ownerId = request.getParameter("ownerId");
        String content = request.getParameter("content");

        if (ownerId == null || ownerId.length() == 0 || content == null) {
            return JsonResult.errorMsg("Please try again later");
        }

        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;

        // 1. Iterate through all files and save the  MultipartFiles to a list

        Iterator<String> files = mRequest.getFileNames();
        List<MultipartFile> imgFileList = new ArrayList<>();
        while (files.hasNext()) {
            MultipartFile mFile = mRequest.getFile(files.next());
            imgFileList.add(mFile);
        }

        // 2. Upload Files to the fastDFS server
        List<String> imgURLs = new ArrayList<>();
        imgFileList.forEach(
            multipartFile -> {
                try {
                    String postImgURL = fastDFSClient.uploadFile(multipartFile);
                    imgURLs.add(postImgURL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        // 3. Create a new Post
        Posts post = Posts.builder()
            .id(UUIDUtil.generateUUID())
            .userId(ownerId)
            .postContent(content)
            .build();

        Date createTime = new Date();
        post.setPostCreateTime(createTime);

        // 4. Create List of postImg objects associated with the new post.
        List<PostImg> postImgList = new ArrayList<>();
        for (int idx = 0; idx < imgURLs.size(); idx++) {

            PostImg postImg = PostImg.builder()
                .id(UUIDUtil.generateUUID())
                .postId(post.getId())
                .postImg(imgURLs.get(idx))
                .imgPosition(idx)
                .build();

            // get the original img size.
            MultipartFile file = imgFileList.get(idx);
            try {
                BufferedImage image = ImageIO.read(file.getInputStream());
                // if  image=null means that it is not a picture
                if (image != null) {
                    String imgSize = image.getWidth() + "x" + image.getHeight();
                    postImg.setImgSize(imgSize);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return JsonResult.errorMsg("not a valid image");
            }

            postImgList.add(postImg);
        }

        // 5. insert the posts and images into the databases
        postService.insertPost(post);
        postService.batchInsertPostImg(postImgList);

        // 6. broadcast or fanOut to all users friends  async
        postService.fanout(post);

        return JsonResult.ok();
    }


    @GetMapping("/{user_id}/{page_number}/{page_size}")
    @ResponseBody
    public JsonResult getPosts(
        @PathVariable("user_id") String userId,
        @PathVariable("page_number") Integer pageNumber,
        @PathVariable("page_size") Integer pageSize) {

        if (pageNumber == null || pageSize == null) {
            return JsonResult.errorMsg("missing parameters ");
        }

        PostVO postVO = postService.queryPostsByCondition(userId, pageNumber, pageSize);

        return JsonResult.ok(postVO);
    }
}
