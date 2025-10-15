package com.artur.mongodb.relacionamentos.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.artur.mongodb.relacionamentos.models.Estudante;

public interface EstudanteRepository extends MongoRepository<Estudante, String> {
    
}
