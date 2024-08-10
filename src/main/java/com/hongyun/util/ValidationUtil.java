package com.hongyun.util;


import com.hongyun.constants.NormalConstants;
import com.hongyun.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValidationUtil {

    public String checkRegisterUserParams(User user) {
        Map<String, String> errorMap = new HashMap<>();
        if (!StringUtils.hasLength(user.getName())) {
            errorMap.put(NormalConstants.NAME, NormalConstants.NULL);
        }
        if (!StringUtils.hasLength(user.getEmail())) {
            errorMap.put(NormalConstants.EMAIL, NormalConstants.NULL);
        }
        if (!StringUtils.hasLength(user.getPassword())) {
            errorMap.put(NormalConstants.PASSWORD, NormalConstants.NULL);
        }
        return getFieldErrorMessage(errorMap);
    }


    private String getFieldErrorMessage(Map<String, String> errorMap) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!errorMap.isEmpty()) {
            for (String key : errorMap.keySet()) {
                stringBuilder.append(key).append(" : ").append(errorMap.get(key)).append(" ");
            }
            return stringBuilder.toString();
        }
        return null;
    }
}
