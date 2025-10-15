package com.artur.mongodb.relacionamentos.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.artur.mongodb.relacionamentos.models.Usuario;

public interface UsuarioRepository  extends MongoRepository<Usuario, String> {
    
}
