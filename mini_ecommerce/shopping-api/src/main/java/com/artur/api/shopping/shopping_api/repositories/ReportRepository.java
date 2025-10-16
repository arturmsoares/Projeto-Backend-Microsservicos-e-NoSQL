package com.artur.api.shopping.shopping_api.repositories;

import java.time.LocalDate;

import com.artur.api.shopping.shopping_api.models.dto.ShopReportDTO;

public interface ReportRepository {
    public ShopReportDTO getReportByDate(LocalDate dataInicio, LocalDate dataFim);
}
