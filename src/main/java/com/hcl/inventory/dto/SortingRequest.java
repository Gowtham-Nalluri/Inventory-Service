package com.hcl.inventory.dto;

import com.hcl.inventory.enums.SortDirection;
import com.hcl.inventory.enums.SortField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SortingRequest {
    private SortField field;
    private SortDirection direction;
}
