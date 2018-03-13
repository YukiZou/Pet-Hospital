package com.ecnu.dao;

import com.ecnu.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    List<User> getAllUser();
}
