package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.mapper;

import com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_snapshot.GetSnapshotQuery;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.decrease_stock.DecreaseStockCommand;
import com.sa.healntrack.pharmacy_service.inventory.domain.StockSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto.SnapshotResponseDTO;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto.UpdateStockDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StockSnapshotRestMapper {

    public DecreaseStockCommand toCommand(UpdateStockDTO d) {
        return new DecreaseStockCommand(d.medicineCode(), d.quantity());
    }

    public GetSnapshotQuery toQuery(String medicineCode) {
        return new GetSnapshotQuery(medicineCode);
    }

    public SnapshotResponseDTO toResponse(StockSnapshot s) {
        return new SnapshotResponseDTO(
                s.getMedicineId().value(),
                s.getTotalQuantity().value(),
                s.getUpdatedAt()
        );
    }
}
