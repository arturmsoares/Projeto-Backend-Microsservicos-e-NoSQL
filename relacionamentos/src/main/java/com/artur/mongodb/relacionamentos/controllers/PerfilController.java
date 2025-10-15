package com.artur.mongodb.relacionamentos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artur.mongodb.relacionamentos.models.Perfil;
import com.artur.mongodb.relacionamentos.repositories.PerfilRepository;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    @Autowired
    private PerfilRepository perfilRepository;

    @PostMapping
    public Perfil create(@RequestBody Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    @GetMapping
    public List<Perfil> getAll() {
        return perfilRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfil> findById(@PathVariable String id) {
        return perfilRepository.findById(id)
                .map(perfil -> ResponseEntity.ok(perfil))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Perfil> update(@PathVariable String id, @RequestBody Perfil perfil) {
        return perfilRepository.findById(id)
                .map(perfilExistente -> {
                    perfil.setId(id);
                    Perfil perfilAtualizado = perfilRepository.save(perfil);
                    return ResponseEntity.ok(perfilAtualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        return perfilRepository.findById(id)
                .map(perfil -> {
                    perfilRepository.delete(perfil);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}