package com.artur.api.product.product_api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.artur.api.product.product_api.models.Category;
import com.artur.api.product.product_api.models.dto.CategoryDTO;
import com.artur.api.product.product_api.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryDTO::convert)
                .collect(Collectors.toList());
    }

    public CategoryDTO findById(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(CategoryDTO::convert).orElse(null);
    }

    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = categoryRepository.save(Category.convert(categoryDTO));
        return CategoryDTO.convert(category);
    }

    public void delete(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.delete(category.get());
        } else {
            throw new RuntimeException("Categoria não encontrada para o ID: " + id);
        }
    }

    public CategoryDTO update(String id, CategoryDTO categoryDTO) {
        Optional<Category> categoriaExistenteOp = categoryRepository.findById(id);
        if (categoriaExistenteOp.isPresent()) {
            Category categoriaExistente = categoriaExistenteOp.get();
            categoriaExistente.setNome(categoryDTO.getNome());
            return CategoryDTO.convert(categoryRepository.save(categoriaExistente));
        } else {
            throw new RuntimeException("Categoria não encontrada para o ID: " + id);
        }
    }

    // Outra forma:
    // Category categoriaExistente = categoryRepository.findById(id)
    // .orElseThrow(() -> new RuntimeException("Categoria não encontrada para o ID:
    // " + id));

    public Page<CategoryDTO> getAllPage(Pageable pageable) {
        Page<Category> categoriesPage = categoryRepository.findAll(pageable);
        return categoriesPage.map(CategoryDTO::convert);
    }

}
