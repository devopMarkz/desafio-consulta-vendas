package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
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

    public Page<SaleReportDTO> findSalesReport(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate endDate = (maxDate.isEmpty())? LocalDate.now() : LocalDate.parse(maxDate);
		LocalDate startDate = (minDate.isEmpty())? endDate.minusYears(1L) : LocalDate.parse(minDate);
        return repository.searchSalesReport(startDate, endDate, name, pageable).map(s -> new SaleReportDTO(s.getId(), s.getDate().toString(), s.getAmount(), s.getSeller().getName()));
    }

    public Page<SaleSummaryDTO> findSalesSummary(String minDate, String maxDate, Pageable pageable){
        LocalDate endDate = (maxDate.isEmpty())? LocalDate.now() : LocalDate.parse(maxDate);
        LocalDate startDate = (minDate.isEmpty())? endDate.minusYears(1L) : LocalDate.parse(minDate);
        return repository.searchSalesSummary(startDate, endDate, pageable);
    }
}
