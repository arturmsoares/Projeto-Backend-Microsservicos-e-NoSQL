package com.artur.mongodb.relacionamentos.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.artur.mongodb.relacionamentos.models.Perfil;

public interface PerfilRepository extends MongoRepository<Perfil, String> {
    
}
