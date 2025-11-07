package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence;

import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.closing.FindMonthlyClosing;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.closing.StoreMonthlyClosing;
import com.sa.healntrack.pharmacy_service.inventory.domain.MonthlyClosing;
import com.sa.healntrack.pharmacy_service.inventory.domain.MonthlyClosingLine;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity.MonthlyClosingEntity;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity.MonthlyClosingLineEntity;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.mapper.MonthlyClosingEntityMapper;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.mapper.MonthlyClosingLineEntityMapper;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.repository.MonthlyClosingJpaRepository;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.repository.MonthlyClosingLineJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MonthlyClosingRepository implements FindMonthlyClosing, StoreMonthlyClosing {

    private final MonthlyClosingJpaRepository closingJPA;
    private final MonthlyClosingLineJpaRepository lineJPA;

    @Override
    public boolean existsByYearMonth(int year, int month) {
        return closingJPA.existsByYearAndMonth(year, month);
    }

    @Override
    public void closeWithLines(MonthlyClosing closing, List<MonthlyClosingLine> lines) {
        MonthlyClosingEntity header = MonthlyClosingEntityMapper.toEntity(closing);
        MonthlyClosingEntity saved = closingJPA.save(header);

        if (lines != null && !lines.isEmpty()) {
            List<MonthlyClosingLineEntity> entities = lines.stream()
                    .map(MonthlyClosingLineEntityMapper::toEntity)
                    .peek(e -> e.setClosingId(saved.getId()))
                    .collect(Collectors.toList());
            lineJPA.saveAll(entities);
        }
    }
}
