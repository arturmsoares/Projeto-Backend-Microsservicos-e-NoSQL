package com.artur.api.shopping.shopping_api.models.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.artur.api.shopping.shopping_api.models.Shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO {

    private String id;
    private String userIdentifier;
    private LocalDate date;
    private List<ItemDTO> items;
    private double total;

    public static ShopDTO convert(Shop shop) {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setUserIdentifier(shop.getUserIdentifier());
        shopDTO.setTotal(shop.getTotal());
        shopDTO.setDate(shop.getDate());
        shopDTO.setItems(shop.getItems()
                .stream()
                .map(ItemDTO::convert)
                .collect(Collectors.toList()));
        return shopDTO;
    }

}
