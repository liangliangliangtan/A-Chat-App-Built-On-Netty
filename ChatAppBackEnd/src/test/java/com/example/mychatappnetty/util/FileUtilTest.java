package com.example.mychatappnetty.util;

import com.example.mychatappnetty.entity.dto.bo.UsersBO;
import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;


public class FileUtilTest {

    @Test
    @Ignore
    public void base64ToMultipart() throws IOException {
        File testFile = new File ("C:\\Users\\tan3\\Downloads\\testuploadclient.jpg");
        byte[] fileContent = FileUtils.readFileToByteArray(testFile);
        String base64Data = Base64.getEncoder().encodeToString(fileContent);

        UsersBO usersBO = new UsersBO();
        usersBO.setUserId("a51675a9840c400cb02f6846b8558c31");
        usersBO.setFaceData(base64Data);
        MultipartFile profileFile = FileUtil.base64ToMultipart(base64Data, usersBO.getUserId()+"userface64.png","image/png");

    }
}
