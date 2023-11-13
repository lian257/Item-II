package com.example.springboot_test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot_test.entity.User;

public interface UserMapper extends BaseMapper<User>{

}


/*
@Mapper
public interface UserMapper {

    @Insert("insert into `user` (username, password, name, phone, email, address, avatar, role) " +
            "values (#{username}, #{password}, #{name}, #{phone}, #{email}, #{address}, #{avatar},#{role})")
    void insert(User user);

    @Update("update `user` set username = #{username}, password = #{password}, name= #{name}, phone = #{phone}, " +
            "email = #{email}, address = #{address}, avatar = #{avatar} where id = #{id}")
    void updateUser(User user);

    @Delete("delete from `user` where id = #{id}")
    void deleteUser(Integer id);

    @Select("select * from `user` order by id desc")
    List<User> selectAll();

    @Select("select * from `user` where id = #{id} order by id desc")
    User selectById(Integer id);

    @Select("select * from `user` where name = #{name} order by id desc")
    List<User> selectByName(String name);


    @Select("select * from `user` where username = #{username} and name = #{name} order by id desc")
    List<User> selectByMore(@Param("username") String username, @Param("name") String name);

    @Select("select * from `user` where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%') order by id desc")
    List<User> selectByMo(String username, String name);

    @Select("select * from `user` where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%') order by id desc limit #{skipNum}, #{pageSize}")
    List<User> selectByPage(@Param("skipNum") Integer skipNum, @Param("pageSize")Integer pageSize, @Param("username") String username, @Param("name") String name);

    @Select("select count(id) from `user` where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%') order by id desc")
    int selectCountByPage(@Param("username") String username, @Param("name") String name);

    @Select("select * from `user` where username = #{username} order by id desc")
    User selectByUserName(String username);

}
*/
