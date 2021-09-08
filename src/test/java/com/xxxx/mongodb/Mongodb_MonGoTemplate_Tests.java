package com.xxxx.mongodb;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.xxxx.mongodb.entity.User;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ExecutableUpdateOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
class Mongodb_MonGoTemplate_Tests {

    //注入MongoTemplate
    @Autowired
    MongoTemplate mongoTemplate;

    //添加
    @Test
    public void create() {
        User user = new User();
        user.setName("Sooocen");
        user.setAge(20);
        user.setEmail("socen@163.com");
        User user1 = mongoTemplate.insert(user);
        System.out.println(user1);
    }

    //查询所有数据
    @Test
    public void findAll(){
        List<User> users = mongoTemplate.findAll(User.class);
        System.out.println(users);
    }

    //根据ID查询
    @Test
    public void findById(){
        User user = mongoTemplate.findById("611240a843bdbd00662b0b22",User.class);
        System.out.println(user);
    }

    //条件查询 name=test age=20
    @Test
    public void findUserList(){
        Query query = new Query(Criteria.where("name").is("test").and("age").is("20"));
        List<User> users = mongoTemplate.find(query,User.class);
        System.out.println(users);
    }

    //模糊查询 名字中包含oo的User
    @Test
    public void findLikeUserList(){
        String name = "oo";
        //构建查询条件
        String regex = String.format("%s%s%s","^.*",name,".*$");
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));

        List<User> users = mongoTemplate.find(query,User.class);
        System.out.println(users);
    }

    //分页查询
    @Test
    public void findPageUserList(){
        int current = 1;    //当前页
        int pagesize = 3;   //每页数据量

        String name = "oo";
        //构建查询条件
        String regex = String.format("%s%s%s","^.*",name,".*$");
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));

        //构架分页
        long count = mongoTemplate.count(query, User.class);
        //分页
        List<User> users = mongoTemplate.find(query.skip((count - 1) * pagesize).limit(pagesize), User.class);
        System.out.println(count);
        System.out.println(users);
    }

    //修改操作
    @Test
    public void updateUser(){
        //根据ID查
        User user = mongoTemplate.findById("611240a843bdbd00662b0b22", User.class);
        //修改参数
        user.setAge(66);
        //数据库修改
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update();
        update.set("name",user.getName());
        update.set("age",user.getAge());
        update.set("email",user.getEmail());
        UpdateResult upsert = mongoTemplate.upsert(query, update, User.class);
        System.out.println(upsert.getModifiedCount());
    }

    //删除操作
    @Test
    public void deleteUser(){
        Query query =new Query(Criteria.where("_id").is("61124d9723d47a536a6b669c"));
        DeleteResult remove = mongoTemplate.remove(query, User.class);
        System.out.println(remove.getDeletedCount());
    }

    @Test
    void contextLoads() {

    }

}
