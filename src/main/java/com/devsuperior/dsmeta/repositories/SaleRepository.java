package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT obj " +
            "FROM Sale obj " +
            "WHERE obj.date >= :minDate " +
            "AND obj.date <= :maxDate " +
            "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<Sale> searchSalesReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(obj.seller.name AS sellerName, SUM(obj.amount) AS total) " +
            "FROM Sale obj WHERE obj.date >= :minDate AND obj.date <= :maxDate " +
            "GROUP BY obj.seller.name")
    Page<SaleSummaryDTO> searchSalesSummary(LocalDate minDate, LocalDate maxDate, Pageable pageable);

}
