package com.artur.mongodb.relacionamentos.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.artur.mongodb.relacionamentos.models.Postagem;

public interface PostagemRepository extends MongoRepository<Postagem, String> {
    
}
