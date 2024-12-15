package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    public Page<SaleReportDTO> findSales(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate endDate = (maxDate.isEmpty())? LocalDate.now() : LocalDate.parse(maxDate);
		LocalDate startDate = (minDate.isEmpty())? endDate.minusYears(1L) : LocalDate.parse(minDate);
		Page<Sale> sales = repository.searchSales(startDate, endDate, name, pageable);
        return sales.map(sale -> new SaleReportDTO(sale.getId(), sale.getDate().toString(), sale.getAmount(), sale.getSeller().getName()));
    }
}
