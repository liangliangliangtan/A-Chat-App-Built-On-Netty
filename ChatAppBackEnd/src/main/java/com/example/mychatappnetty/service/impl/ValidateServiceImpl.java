package com.example.mychatappnetty.service.impl;


import com.example.mychatappnetty.dao.ValidateDao;
import com.example.mychatappnetty.entity.Users;
import com.example.mychatappnetty.entity.Validates;
import com.example.mychatappnetty.enums.TimeEnum;
import com.example.mychatappnetty.service.ValidateService;
import com.example.mychatappnetty.util.TimeUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ValidateServiceImpl implements ValidateService {


    @Autowired
    private ValidateDao validateDao;
    private static final String TYPE = "passwordRest";


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int insertNewResetRecord(Validates validate, Users user, String resetToken) {
        validate.setUserId(user.getId());
        validate.setUserEmail(user.getUserEmail());
        validate.setResetToken(resetToken);
        validate.setType(TYPE);
        validate.setCreateTime(new Date());
        validate.setModifiedTime(new Date());
        return validateDao.insertRecord(validate);
    }


    @Override
    public Validates findUserByResetToken(String resetToken) {

        Validates validateCondition = new Validates();
        validateCondition.setResetToken(resetToken);
        List<Validates> validatesList = validateDao.queryRecordByCondition(validateCondition);

        return validatesList == null || validatesList.isEmpty() ? null : validatesList.get(0);
    }

    @Override
    public boolean validateLimitation(String userEmail, long maxRequestPerDay, TimeEnum maxInterval) {

        Validates validateCondition = new Validates();
        validateCondition.setUserEmail(userEmail);
        validateCondition.setType(TYPE);

        List<Validates> validatesList = validateDao.queryRecordByCondition(validateCondition);

        // 0. if no record found, this means that it is the first time the user want to reset the password
        if (validatesList.isEmpty()) {
            return true;
        }

        // 1. count how many times there are records in today.
        long countTodayValidationTimes = validatesList.stream().filter(
                                          record -> DateUtils.isSameDay(record.getModifiedTime(), new Date())).count();



        // get the latest modified record if present
        Optional record = validatesList.stream().map(Validates::getModifiedTime)
                                                .max(Date::compareTo);
        Date dateOfLastRequest = new Date();

        if (record.isPresent()) dateOfLastRequest = (Date) record.get();

        return countTodayValidationTimes <= maxRequestPerDay
                && TimeUtil.isDifferenceGreaterThanTarget(dateOfLastRequest, maxInterval);

    }

    @Override
    public int updateValidate(Validates validatesCondition) {
        return validateDao.updateValidate(validatesCondition);
    }


}
