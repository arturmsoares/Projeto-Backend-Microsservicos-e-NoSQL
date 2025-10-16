package com.artur.api.shopping.shopping_api.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.artur.api.shopping.shopping_api.models.Shop;
import com.artur.api.shopping.shopping_api.models.dto.ShopReportDTO;

public class ReportRepositoryImpl implements ReportRepository {

    private final MongoTemplate mongoTemplate;

    public ReportRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public ShopReportDTO getReportByDate(LocalDate dataInicio, LocalDate dataFim) {
        Query query = new Query();
        query.addCriteria(Criteria.where("date").gte(dataInicio).lte(dataFim));
        
        List<Shop> shops = mongoTemplate.find(query, Shop.class);

        ShopReportDTO reportDTO = new ShopReportDTO();
        reportDTO.setCount((long) shops.size());
        reportDTO.setTotal(shops.stream().mapToDouble(Shop::getTotal).sum());

        return reportDTO;
    }
}