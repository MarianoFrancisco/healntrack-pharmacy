package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.persistence;

import com.sa.healntrack.pharmacy_service.sales.application.port.out.persistence.FindSales;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.persistence.StoreSale;
import com.sa.healntrack.pharmacy_service.sales.domain.Sale;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleStatus;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.persistence.entity.SaleEntity;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.persistence.mapper.SaleEntityMapper;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.persistence.repository.SaleJpaRepository;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.persistence.specification.SaleSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SaleRepository implements FindSales, StoreSale {

    private final SaleJpaRepository jpa;

    @Override
    public Optional<Sale> findById(UUID saleId) {
        return jpa.findById(saleId).map(SaleEntityMapper::toDomain);
    }

    @Override
    public List<Sale> search(Long occurredFrom, Long occurredTo, UUID sellerId, String status) {
        Specification<SaleEntity> spec = Specification.allOf(
                SaleSpecs.occurredFrom(occurredFrom),
                SaleSpecs.occurredTo(occurredTo),
                SaleSpecs.hasSeller(sellerId),
                SaleSpecs.hasStatus(status != null ? SaleStatus.valueOf(status) : null)
        );
        return jpa.findAll(spec).stream()
                .map(SaleEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Sale save(Sale sale) {
        SaleEntity saved = jpa.save(SaleEntityMapper.toEntity(sale));
        return SaleEntityMapper.toDomain(saved);
    }
}
