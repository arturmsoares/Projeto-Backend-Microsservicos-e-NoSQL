package com.artur.api.product.product_api.models.dto;

import com.artur.api.product.product_api.models.Product;
import com.artur.api.product.product_api.models.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    
    private String id;
    private String productIdentifier;
    private String nome;
    private String descricao;
    private Double preco;
    private CategoryDTO category;



    public static ProductDTO convert(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setNome(product.getNome());
        productDTO.setDescricao(product.getDescricao());
        productDTO.setPreco(product.getPreco());
        if (product.getCategory() != null) {
            productDTO.setCategory(CategoryDTO.convert(product.getCategory()));
        }

        return productDTO;
    }
}
