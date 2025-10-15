package com.artur.userapi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.artur.userapi.models.User;
import java.util.List;


public interface UserRepository extends MongoRepository<User, String> {

    User findByCpf(String cpf);

    List<User> queryByNomeLike(String name);

    
}
