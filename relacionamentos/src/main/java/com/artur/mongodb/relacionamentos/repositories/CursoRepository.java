package com.artur.mongodb.relacionamentos.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.artur.mongodb.relacionamentos.models.Curso;

public interface CursoRepository extends MongoRepository<Curso, String> {
    
}
