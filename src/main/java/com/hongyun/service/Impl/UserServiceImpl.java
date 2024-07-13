package com.hongyun.service.Impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.hongyun.common.ResponseObjectVO;
import com.hongyun.entity.User;
import com.hongyun.mapper.UserMapper;
import com.hongyun.service.UserService;
import com.hongyun.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;

    private Log log = LogFactory.get();
    @Override
    public ResponseObjectVO<List<User>> findUsers() {
        ResponseObjectVO<List<User>> responseObjectVO = new ResponseObjectVO();
        List<User> users = userMapper.getAll();
        log.info("userLst size -> {}", users.size());
        return responseObjectVO.getSuccess("findSuccess", users);
    }

    @Override
    public String register(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        String token = null;
        if(userMapper.insert(user) > 0){
            token = jwtUtils.generateToken(user);
            return token;
        }
        return token;
    }
}
