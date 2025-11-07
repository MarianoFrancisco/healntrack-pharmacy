package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.controller;

import com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_monthly_closing.CreateMonthlyClosing;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_monthly_closing.CreateMonthlyClosingCommand;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_monthly_closing.CreateMonthlyClosingLineCommand;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto.CreateMonthlyClosingDTO;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto.CreateMonthlyClosingLineDTO;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.mapper.MonthlyClosingLineRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory/monthly-closings")
public class MonthlyClosingController {

    private final CreateMonthlyClosing createMonthlyClosing;

    @PostMapping("/{year}/{month}:create")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@PathVariable int year,
                       @PathVariable int month,
                       @Valid @RequestBody CreateMonthlyClosingDTO body) {

        List<CreateMonthlyClosingLineCommand> lines = body.lines().stream()
                .map(MonthlyClosingLineRestMapper::toCommand)
                .toList();

        CreateMonthlyClosingCommand cmd = new CreateMonthlyClosingCommand(
                year,
                month,
                body.closedBy(),
                lines
        );
        createMonthlyClosing.handle(cmd);
    }
}
