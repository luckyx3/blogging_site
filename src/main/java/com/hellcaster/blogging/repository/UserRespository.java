package com.hellcaster.blogging.repository;

import com.hellcaster.blogging.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRespository extends MongoRepository<User, String> {
    User findByUserNameAndPassword(String userName, String password);
    User findByUserName(String userName);
    User findByUserIdAndOtp(String userId, Integer otp);
}
