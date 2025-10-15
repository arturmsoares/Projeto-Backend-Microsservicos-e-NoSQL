package com.artur.api.product.product_api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.artur.api.product.product_api.models.Category;
import com.artur.api.product.product_api.models.Product;
import com.artur.api.product.product_api.models.dto.ProductDTO;
import com.artur.api.product.product_api.repositories.CategoryRepository;
import com.artur.api.product.product_api.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<ProductDTO> getAll() {
        return productRepository.findAll().stream()
                .map(ProductDTO::convert)
                .collect(Collectors.toList());
    }

    public ProductDTO findById(String id) {
        return productRepository.findById(id)
                .map(ProductDTO::convert)
                .orElse(null);
    }

    public List<ProductDTO> getProductByCategoryId(String categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(ProductDTO::convert)
                .collect(Collectors.toList());
    }

    public ProductDTO findByProductIdentifier(String productIdentifier) {
        Product product = productRepository.findByProductIdentifier(productIdentifier);
        if (product != null) {
            return ProductDTO.convert(product);
        }
        throw new RuntimeException("Produto não encontrado para o identificador: " + productIdentifier);
    }

    public ProductDTO save(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        Product product = Product.convert(productDTO);
        product.setCategory(category);
        product = productRepository.save(product);
        return ProductDTO.convert(product);
    }

    public void delete(String id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresentOrElse(p -> productRepository.delete(p),
                () -> {
                    throw new RuntimeException("Produto não encontrado para o ID: " + id);
                });
    }

    public ProductDTO update(String id, ProductDTO productDTO) {
        return productRepository.findById(id).map(product -> {
            Category category = categoryRepository.findById(productDTO.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

            product.setNome(productDTO.getNome());
            product.setPreco(productDTO.getPreco());
            product.setDescricao(productDTO.getDescricao());
            product.setCategory(category);
            return ProductDTO.convert(productRepository.save(product));
        }).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Page<ProductDTO> getAllPage(Pageable pageable) {
        Page<Product> productsPage = productRepository.findAll(pageable);
        return productsPage.map(ProductDTO::convert);
    }

}
