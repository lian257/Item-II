package com.example.springboot_test.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot_test.entity.User;
import com.example.springboot_test.exception.ServiceException;
import com.example.springboot_test.mapper.UserMapper;
import com.example.springboot_test.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 功能：
 * 作者：胡霜福
 * 日期：2023/11/9 21:46
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    /*
    @Autowired
    UserMapper userMapper;

    public void insertUser(User user) {
        userMapper.insert(user);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }

    public void batchDeleteUser(List<Integer> ids) {
        for (Integer id : ids) {
            userMapper.deleteUser(id);
        }
    }

    public List<User> selectAllUser() {
        return userMapper.selectAll();
    }

    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    public List<User> selectByName(String name) {
        return userMapper.selectByName(name);
    }

    public List<User> selectByMore(String username, String name) {
        return userMapper.selectByMore(username,name);
    }

    public List<User> selectByMo(String username, String name) {
        return userMapper.selectByMo(username,name);
    }

     */

    @Resource
    UserMapper userMapper;

    @Override
    public boolean save(User entity) {

        if(StrUtil.isBlank(entity.getName())){
            entity.setName(entity.getUsername());
        }
        if(StrUtil.isBlank(entity.getPassword())){
            entity.setPassword("123");
        }
        if(StrUtil.isBlank(entity.getRole())){
            entity.setRole("用户");
        }
        if(StrUtil.isBlank(entity.getPhone())){
            entity.setPhone("1234567890");
        }

        return super.save(entity);
    }

    public User selectByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        //  eq => ==   where username = #{username}
        // 根据用户名查询数据库的用户信息
        return getOne(queryWrapper);
        //  select * from user where username = #{username}
    }

    /*

    public Page<User> selectByPage(Integer pageNum, Integer pageSize, String username, String name) {
        Integer skipNum = (pageNum - 1) * pageSize;
        // 计算出来  1 -> 0,5    2 -> 5,5   3 -> 10,5
        Page<User> page = new Page<>();
        List<User> userList = userMapper.selectByPage(skipNum, pageSize, username, name);
        Integer total = userMapper.selectCountByPage(username, name);
        page.setTotal(total);
        page.setList(userList);
        return page;
    }

     */

    // 验证
    public User login(User user) {
        User dbUser =  selectByUsername(user.getUsername());
        if(dbUser == null){
            //抛出异常
            throw new ServiceException("用户名或密码错误");
        }
        if(!user.getPassword().equals(dbUser.getPassword())){
            throw new ServiceException("用户名或密码错误");
        }
        //生成 token
        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
      }

    public User register(User user) {
        User dbUser =  selectByUsername(user.getUsername());
        if(dbUser != null){
            //抛出异常
            throw new ServiceException("用户名已存在");
        }
        user.setName(user.getUsername());
        userMapper.insert(user);
        return user;
    }


    public void resetPassword(User user) {
        User dbUser =  selectByUsername(user.getUsername());
        if (dbUser == null) {
            // 抛出一个自定义的异常
            throw new ServiceException("用户不存在");
        }
        if (!user.getPhone().equals(dbUser.getPhone())) {
            throw new ServiceException("验证错误");
        }
        dbUser.setPassword("123");
        // 重置密码
       updateById(dbUser);
    }
}