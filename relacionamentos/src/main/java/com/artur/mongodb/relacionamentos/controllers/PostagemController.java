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

import com.artur.mongodb.relacionamentos.models.Postagem;
import com.artur.mongodb.relacionamentos.repositories.PostagemRepository;

@RestController
@RequestMapping("/postagens")
public class PostagemController {

    @Autowired
    private PostagemRepository postagemRepository;

    @PostMapping
    public Postagem create(@RequestBody Postagem postagem) {
        return postagemRepository.save(postagem);
    }

    @GetMapping
    public List<Postagem> getAll() {
        return postagemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Postagem> findById(@PathVariable String id) {
        return postagemRepository.findById(id)
                .map(postagem -> ResponseEntity.ok(postagem))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Postagem> update(@PathVariable String id, @RequestBody Postagem postagem) {
        return postagemRepository.findById(id)
                .map(postagemExistente -> {
                    postagem.setId(id);
                    Postagem postagemAtualizada = postagemRepository.save(postagem);
                    return ResponseEntity.ok(postagemAtualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        return postagemRepository.findById(id)
                .map(postagem -> {
                    postagemRepository.delete(postagem);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}