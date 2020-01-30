package com.example.mychatappnetty.dao;

import com.example.mychatappnetty.entity.Validates;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;


@Mapper
public interface ValidateDao {

    /**
     * Insert one validates record into the databases.
     * @param validates
     * @return
     */
    int insertRecord(Validates validates);

    /**
     * Dynamic SQL: query validation record by condition
     * Condition can be :
     * 1. resetToken
     * 2. userEmail
     * 3. type
     * @param validatesCondition
     * @return
     */
    List<Validates> queryRecordByCondition(Validates validatesCondition);


    int updateValidate(Validates validates);

}
