package com.hongyun.service;

import com.hongyun.entity.User;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface UserService {

    String register(User user) throws Exception;

    String login(String email, String password) throws Exception;

    String requestUpdatePasswordByEmail(String email);

    boolean checkCodeExisted(String email);

    boolean updatePassword(String email, String code, String newPassword) throws Exception;
}
