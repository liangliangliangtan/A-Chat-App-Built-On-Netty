package com.example.mychatappnetty.dao;
import com.example.mychatappnetty.entity.Users;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserDao {

    /**
     * register, active state 0, send mail to user email
     * @param user
     */
    int insertUser(Users user);


    /**
     * Active the user status if the code is correct
     * @param user
     */
    void updateUserStatus(Users user);

    /**
     *  Query user by different conditions
     * @param userCondition
     * @return
     */
    Users queryUser(Users userCondition);
}
