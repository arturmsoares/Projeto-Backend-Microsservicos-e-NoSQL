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
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EstudanteRepository estudanteRepository;

    @PostMapping
    public Curso create(@RequestBody Curso curso) {
        if (curso.getEstudantes() != null && !curso.getEstudantes().isEmpty()) {
            List<Estudante> estudantesSalvos = new ArrayList<>();
            for (Estudante estudante : curso.getEstudantes()) {
                if (estudante.getId() == null) {
                    Estudante estudanteSalvo = estudanteRepository.save(estudante);
                    estudantesSalvos.add(estudanteSalvo);
                } else {
                    estudantesSalvos.add(estudante);
                }
            }
            curso.setEstudantes(estudantesSalvos);
        }

        return cursoRepository.save(curso);
    }

    @GetMapping
    public List<Curso> getAll() {
        return cursoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> findById(@PathVariable String id) {
        return cursoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> update(@PathVariable String id, @RequestBody Curso curso) {
        return cursoRepository.findById(id)
                .map(cursoExistente -> {
                    curso.setId(id);

                    if (curso.getEstudantes() != null && !curso.getEstudantes().isEmpty()) {
                        List<Estudante> estudantesSalvos = new ArrayList<>();
                        for (Estudante estudante : curso.getEstudantes()) {
                            if (estudante.getId() == null) {
                                Estudante estudanteSalvo = estudanteRepository.save(estudante);
                                estudantesSalvos.add(estudanteSalvo);
                            } else {
                                estudantesSalvos.add(estudante);
                            }
                        }
                        curso.setEstudantes(estudantesSalvos);
                    }

                    Curso cursoAtualizado = cursoRepository.save(curso);
                    return ResponseEntity.ok(cursoAtualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        return cursoRepository.findById(id)
                .map(curso -> {
                    cursoRepository.delete(curso);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}