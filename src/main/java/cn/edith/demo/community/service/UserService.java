package cn.edith.demo.community.service;

import cn.edith.demo.community.mapper.UserMapper;
import cn.edith.demo.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
      User dbUser =  userMapper.findByAccountId(user.getAccountId());
      if(dbUser == null){
          user.setGmtCreate(System.currentTimeMillis());
          user.setGmtModified(user.getGmtCreate());
          userMapper.insert(user);
      }else{
          dbUser.setGmtModified(System.currentTimeMillis());
          dbUser.setAvatarUrl(user.getAvatarUrl());
          dbUser.setToken(user.getToken());
          dbUser.setName(user.getName());
          userMapper.update(dbUser);

      }
    }
}