package com.artur.api.shopping.shopping_api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artur.api.shopping.shopping_api.models.Shop;
import com.artur.api.shopping.shopping_api.models.dto.ShopDTO;
import com.artur.api.shopping.shopping_api.repositories.ShopRepository;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    public List<ShopDTO> getAll() {
        return shopRepository.findAll().stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDTO> getByUser(String userIdentifier) {
        return shopRepository.findByUserIdentifier(userIdentifier).stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDTO> getByDate(LocalDateTime date) {
        return shopRepository.findAllByDateGreaterThanEqual(date).stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public ShopDTO findById(String id) {
        Optional<Shop> shop = shopRepository.findById(id);
        return shop.map(ShopDTO::convert).orElse(null);
    }

    public ShopDTO save(ShopDTO shopDTO) {
        shopDTO.setTotal(shopDTO.getItems().stream()
                .map(item -> item.getPrice())
                .reduce(0d, Double::sum));
        shopDTO.setDate(LocalDateTime.now());

        Shop shop = Shop.convert(shopDTO);
        shop = shopRepository.save(shop);
        return ShopDTO.convert(shop);
    }

    public List<ShopDTO> getShopsByFilter(LocalDateTime dataInicio, LocalDateTime dataFim, Float valorMinimo) {
        return shopRepository.findByDateBetweenAndTotalGreaterThanEqual(dataInicio, dataFim, valorMinimo)
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDTO> findByProductIdentifier(String productIdentifier) {
        return shopRepository.findByItemsProductIdentifier(productIdentifier)
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDTO> getReportByDate(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return shopRepository.findByDateBetween(dataInicio, dataFim)
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }
}