package com.iftm.start_example.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.iftm.start_example.models.User;

public interface UserRepositotory  extends MongoRepository<User, ObjectId>{
    
}
