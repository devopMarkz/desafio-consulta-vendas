package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
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
            "AND UPPER(obj.seller.name) " +
            "LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<Sale> searchSales(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

}
