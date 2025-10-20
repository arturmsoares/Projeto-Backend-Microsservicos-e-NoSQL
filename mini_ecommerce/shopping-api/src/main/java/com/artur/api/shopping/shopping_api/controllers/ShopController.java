package com.artur.api.shopping.shopping_api.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.artur.api.shopping.shopping_api.models.dto.ShopDTO;
import com.artur.api.shopping.shopping_api.services.ShopService;

@RestController
@RequestMapping("/shopping")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping
    public ResponseEntity<List<ShopDTO>> getAllShops() {
        return ResponseEntity.ok(shopService.getAll());
    }

    @GetMapping("/shopByUser")
    public ResponseEntity<List<ShopDTO>> getShopsByUser(@RequestParam String userIdentifier) {
        return ResponseEntity.ok(shopService.getByUser(userIdentifier));
    }

    @GetMapping("/shopByDate")
    public ResponseEntity<List<ShopDTO>> getShopsByDate(
            @RequestParam LocalDateTime startDate) {
        return ResponseEntity.ok(shopService.getByDate(startDate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(shopService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ShopDTO> saveShop(@RequestBody ShopDTO shopDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shopService.save(shopDTO));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ShopDTO>> getShopsByFilter(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            @RequestParam Float minValue) {
        
        return ResponseEntity.ok(shopService.getShopsByFilter(startDate, endDate, minValue));
    }
    
    @GetMapping("/report")
    public ResponseEntity<List<ShopDTO>> getReportByDate(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(shopService.getReportByDate(startDate, endDate));
    }
    
    @GetMapping("/product/{productIdentifier}")
    public ResponseEntity<List<ShopDTO>> findByProductIdentifier(@PathVariable String productIdentifier) {
        return ResponseEntity.ok(shopService.findByProductIdentifier(productIdentifier));
    }
}
