package com.artur.mongodb.relacionamentos.controllers;

import java.util.ArrayList;
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

import com.artur.mongodb.relacionamentos.models.Curso;
import com.artur.mongodb.relacionamentos.models.Estudante;
import com.artur.mongodb.relacionamentos.repositories.CursoRepository;
import com.artur.mongodb.relacionamentos.repositories.EstudanteRepository;

@RestController
@RequestMapping("/estudantes")
public class EstudanteController {

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public Estudante create(@RequestBody Estudante estudante) {
        if (estudante.getCursos() != null && !estudante.getCursos().isEmpty()) {
            List<Curso> cursosSalvos = new ArrayList<>();
            for (Curso curso : estudante.getCursos()) {
                if (curso.getId() == null) {
                    Curso cursoSalvo = cursoRepository.save(curso);
                    cursosSalvos.add(cursoSalvo);
                } else {
                    cursosSalvos.add(curso);
                }
            }
            estudante.setCursos(cursosSalvos);
        }

        return estudanteRepository.save(estudante);
    }

    @GetMapping
    public List<Estudante> getAll() {
        return estudanteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudante> findById(@PathVariable String id) {
        return estudanteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudante> update(@PathVariable String id, @RequestBody Estudante estudante) {
        return estudanteRepository.findById(id)
                .map(estudanteExistente -> {
                    estudante.setId(id);

                    if (estudante.getCursos() != null && !estudante.getCursos().isEmpty()) {
                        List<Curso> cursosSalvos = new ArrayList<>();
                        for (Curso curso : estudante.getCursos()) {
                            if (curso.getId() == null) {
                                Curso cursoSalvo = cursoRepository.save(curso);
                                cursosSalvos.add(cursoSalvo);
                            } else {
                                cursosSalvos.add(curso);
                            }
                        }
                        estudante.setCursos(cursosSalvos);
                    }

                    Estudante estudanteAtualizado = estudanteRepository.save(estudante);
                    return ResponseEntity.ok(estudanteAtualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        return estudanteRepository.findById(id)
                .map(estudante -> {
                    estudanteRepository.delete(estudante);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}