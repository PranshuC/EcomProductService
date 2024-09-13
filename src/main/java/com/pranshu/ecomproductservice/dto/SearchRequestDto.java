package com.pranshu.ecomproductservice.dto;

import com.pranshu.ecomproductservice.model.SortParam;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchRequestDto {
    private String title;
    private int pageNumber;
    private int pageSize;
    //private String sortParam;
    private List<SortParam> sortParams;
}
