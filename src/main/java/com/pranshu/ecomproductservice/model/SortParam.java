package com.pranshu.ecomproductservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortParam {
    private String name;
    private SortType type; // ASC or DESC
}
