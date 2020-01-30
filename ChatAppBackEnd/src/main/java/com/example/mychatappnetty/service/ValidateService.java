package com.example.mychatappnetty.service;

import com.example.mychatappnetty.entity.Users;
import com.example.mychatappnetty.entity.Validates;
import com.example.mychatappnetty.enums.TimeEnum;


public interface ValidateService {

    // void sendPasswordResetEmail(String receiver, String subject, String content);

    /**
     * Insert a new validation record.
     * @param validate
     * @param user
     * @param resetToken
     * @return
     */
    int insertNewResetRecord(Validates validate, Users user, String resetToken);

    /**
     * Find resetToken to find User
     * @param resetToken
     * @return
     */
    Validates findUserByResetToken(String resetToken);

    /**
     * Verify if the server should send the reset Email again
     *
     * return true if the request should be sent if the requestPerDay not exceed the maximum times and the last token
     * is out-of-date
     *
     * @param userEmail
     * @param maxRequestPerDay
     * @param maxInterval: maxInterval is the longest effect time for the reset token.
     * @return
     */
    boolean validateLimitation(String userEmail, long maxRequestPerDay, TimeEnum maxInterval);


    int updateValidate(Validates validatesCondition);



}
