package com.xxxx.mongodb;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.xxxx.mongodb.entity.User;
import com.xxxx.mongodb.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@SpringBootTest
class Mongodb_MonGoRepository_Tests {

    //注入UserRepository
    @Autowired
    UserRepository userRepository;


    //添加
    @Test
    public void create() {
        User user = new User();
        user.setName("xixi");
        user.setAge(18);
        user.setEmail("xixi@163.com");
        User u = userRepository.save(user);
        System.out.println(u);
    }

    //查询所有数据
    @Test
    public void findAll(){
        List<User> all = userRepository.findAll();
        System.out.println(all);
    }

    //根据ID查询
    @Test
    public void findById(){
        User user = userRepository.findById("61124e4031057561678338b6").get();
        System.out.println(user);
    }

    //条件查询 name=test age=20
    @Test
    public void findUserList(){
        User user = new User();
        user.setAge(18);
        user.setName("xixi");
        Example<User> userExample = Example.of(user);
        List<User> all = userRepository.findAll(userExample);
        System.out.println(all);
    }

    //模糊查询 名字中包含oo的User
    @Test
    public void findLikeUserList(){
        //设置模糊匹配规则 ExampleMatcher.StringMatcher.CONTAINING模糊查询  withIgnoreCase忽略大小写
        ExampleMatcher exampleMatcher =  ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase(true);
        User user = new User();
        user.setAge(18);
        user.setName("xi");
        Example<User> userExample = Example.of(user,exampleMatcher);
        List<User> all = userRepository.findAll(userExample);
        System.out.println(all);
    }

    //分页查询
    @Test
    public void findPageUserList(){
        //设置分页参数 0第一页 每页三条记录
        Pageable pageable = PageRequest.of(0, 3);
        User user = new User();
        user.setName("xixi");
        Example<User> userExample = Example.of(user);
        Page<User> all = userRepository.findAll(userExample, pageable);
        System.out.println(all);
    }

    //修改操作 有ID值 修改 没有添加
    @Test
    public void updateUser(){
        User user = userRepository.findById("61124e55cdf5da5319f28c36").get();
        user.setAge(18);
        user.setName("xixixixi");
        User save = userRepository.save(user);
        System.out.println(save);
    }

    //删除操作 传ID
    @Test
    public void deleteUser(){
        userRepository.deleteById("61124e55cdf5da5319f28c36");
    }




}
