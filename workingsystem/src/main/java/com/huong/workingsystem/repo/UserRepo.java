package com.huong.workingsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.huong.workingsystem.model.entity.User;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User , Integer>{

    @Query("SELECT u FROM User u where u.userId =:userId")
    User getUserById(@Param("userId") Integer userId);
    @Query("SELECT u FROM User u where u.userName =:userName")
    Optional<User> getUserByUserName(@Param("userName") String userName);
    @Query("SELECT u FROM User u where u.userName= :keyword  OR u.email = :keyword")
    Optional<User> getUserByKeyword(@Param("keyword") String  keyword);
    
}
