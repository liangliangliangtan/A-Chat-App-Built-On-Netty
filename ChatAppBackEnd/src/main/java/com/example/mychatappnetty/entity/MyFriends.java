package com.example.mychatappnetty.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyFriends {

    private String id;// primary Key for table my_friends

    private String myUserId;

    private String myFriendUserId;

}
