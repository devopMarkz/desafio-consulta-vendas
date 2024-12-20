package com.devsuperior.dsmeta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<?> getReport(@RequestParam(name = "minDate", defaultValue = "") String minDate,
									   @RequestParam(name = "maxDate", defaultValue = "") String maxDate,
									   @RequestParam(name = "name", defaultValue = "") String name,
									   Pageable pageable) {
		return ResponseEntity.ok(service.findSalesReport(minDate, maxDate, name, pageable));
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<?> getSummary(@RequestParam(name = "minDate", defaultValue = "") String minDate,
										@RequestParam(name = "maxDate", defaultValue = "") String maxDate,
										Pageable pageable) {
		return ResponseEntity.ok(service.findSalesSummary(minDate, maxDate, pageable));
	}
}
