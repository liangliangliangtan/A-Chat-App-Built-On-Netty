package com.example.mychatappnetty.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Validates {

    private Integer id;

    private String userId;

    private String userEmail;

    private String resetToken;

    private String type;

    private Date createTime;

    private Date modifiedTime;

}
