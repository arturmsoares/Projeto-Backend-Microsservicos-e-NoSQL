package com.artur.mongodb.relacionamentos.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.artur.mongodb.relacionamentos.models.Postagem;
import com.artur.mongodb.relacionamentos.models.Usuario;
import com.artur.mongodb.relacionamentos.repositories.PerfilRepository;
import com.artur.mongodb.relacionamentos.repositories.PostagemRepository;
import com.artur.mongodb.relacionamentos.repositories.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PostagemRepository postagemRepository;

    @GetMapping
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable String id) {
        return usuarioRepository.findById(id)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable String id, @RequestBody Usuario usuario) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    usuario.setId(id);

                    // Atualizar perfil
                    if (usuario.getPerfil() != null) {
                        if (usuario.getPerfil().getId() == null) {
                            Perfil perfilSalvo = perfilRepository.save(usuario.getPerfil());
                            usuario.setPerfil(perfilSalvo);
                        }
                    }

                    // Atualizar postagens
                    if (usuario.getPostagens() != null && !usuario.getPostagens().isEmpty()) {
                        List<Postagem> postagensSalvas = new ArrayList<>();
                        for (Postagem postagem : usuario.getPostagens()) {
                            if (postagem.getId() == null) {
                                Postagem postagemSalva = postagemRepository.save(postagem);
                                postagensSalvas.add(postagemSalva);
                            } else {
                                postagensSalvas.add(postagem);
                            }
                        }
                        usuario.setPostagens(postagensSalvas);
                    }

                    Usuario usuarioAtualizado = usuarioRepository.save(usuario);
                    return ResponseEntity.ok(usuarioAtualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Usuario create(@RequestBody Usuario usuario) {
        if (usuario.getPerfil() != null && usuario.getPerfil().getId() == null) {
            Perfil perfilSalvo = perfilRepository.save(usuario.getPerfil());
            usuario.setPerfil(perfilSalvo);
        }

        if (usuario.getPostagens() != null && !usuario.getPostagens().isEmpty()) {
            List<Postagem> postagensSalvas = new ArrayList<>();
            for (Postagem postagem : usuario.getPostagens()) {
                if (postagem.getId() == null) {
                    Postagem postagemSalva = postagemRepository.save(postagem);
                    postagensSalvas.add(postagemSalva);
                } else {
                    postagensSalvas.add(postagem);
                }
            }
            usuario.setPostagens(postagensSalvas);
        }

        return usuarioRepository.save(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuarioRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
