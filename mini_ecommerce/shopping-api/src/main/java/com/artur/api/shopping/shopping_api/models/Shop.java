package com.artur.api.shopping.shopping_api.models;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.artur.api.shopping.shopping_api.models.dto.ShopDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "shops")
public class Shop {

    @Id
    private String id;
    @Field("userIdentifier")
    private String userIdentifier;
    @Field("date")
    private LocalDate date;
    @Field("items")
    private List<Item> items;
    @Field("total")
    private double total;

    public static Shop convert(ShopDTO shopDTO) {
        Shop shop = new Shop();
        shop.setId(shopDTO.getId());
        shop.setUserIdentifier(shopDTO.getUserIdentifier());
        shop.setDate(shopDTO.getDate());
        shop.setItems(shopDTO.getItems()
                .stream()
                .map(Item::convert)
                .collect(Collectors.toList()));
        shop.setTotal(shopDTO.getTotal());
        return shop;
    }

}
