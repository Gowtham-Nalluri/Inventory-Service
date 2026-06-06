package com.hcl.inventory.controller;

import com.hcl.inventory.dto.*;
import com.hcl.inventory.entity.*;
import com.hcl.inventory.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
public class InventoryController{

    private final InventoryService service;

    @Operation(
            summary = "Search Inventories",
            description =
                    "Search inventories using optional filters"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation Error")
    })
    @PostMapping("/search")
    public Page<InventoryResponse> search(@RequestBody(required=false) InventorySearchRequest req){
        if(req==null)
            req=new InventorySearchRequest();
        return service.searchInventory(req);
    }
}