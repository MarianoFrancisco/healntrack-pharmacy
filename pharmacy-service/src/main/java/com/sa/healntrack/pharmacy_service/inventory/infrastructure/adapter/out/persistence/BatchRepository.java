package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence;

import com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_batches.GetAllBatchesQuery;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.batch.FindBatches;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.batch.StoreBatch;
import com.sa.healntrack.pharmacy_service.inventory.domain.Batch;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity.BatchEntity;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.mapper.BatchEntityMapper;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.repository.BatchJpaRepository;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.specification.BatchSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BatchRepository implements FindBatches, StoreBatch {

    private final BatchJpaRepository jpa;

    @Override
    public List<Batch> findByMedicine(UUID medicineId, Boolean onlyWithStock, Boolean onlyNotExpired) {
        Specification<BatchEntity> spec = Specification.allOf(
                BatchSpecs.byMedicineId(medicineId),
                BatchSpecs.onlyWithStock(onlyWithStock),
                BatchSpecs.onlyNotExpired(onlyNotExpired)
        );
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        return jpa.findAll(spec, sort).stream().map(BatchEntityMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Batch> findConsumableBatches(UUID medicineId) {
        Specification<BatchEntity> spec = Specification.allOf(
                BatchSpecs.byMedicineId(medicineId),
                BatchSpecs.onlyWithStock(true),
                BatchSpecs.onlyNotExpired(true)
        );
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        return jpa.findAll(spec, sort).stream().map(BatchEntityMapper::toDomain).toList();
    }

    @Override
    public Batch save(Batch batch) {
        BatchEntity saved = jpa.save(BatchEntityMapper.toEntity(batch));
        return BatchEntityMapper.toDomain(saved);
    }
}
