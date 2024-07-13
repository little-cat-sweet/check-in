package com.hongyun.service.Impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.hongyun.common.ResponseObjectVO;
import com.hongyun.entity.User;
import com.hongyun.mapper.UserMapper;
import com.hongyun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private Log log = LogFactory.get();
    @Override
    public ResponseObjectVO findUsers() {
        ResponseObjectVO responseObjectVO = new ResponseObjectVO();
        List<User> users = userMapper.getAll();
        log.info("userLst size -> {}", users.size());
        return responseObjectVO.getSuccess("findSuccess", users);
    }
}
