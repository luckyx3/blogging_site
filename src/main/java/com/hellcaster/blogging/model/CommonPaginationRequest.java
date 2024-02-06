package com.hellcaster.blogging.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonPaginationRequest {
    private Integer pageNo;
    private Integer pageSize;
    private String sortBy;
    private String value;
}
