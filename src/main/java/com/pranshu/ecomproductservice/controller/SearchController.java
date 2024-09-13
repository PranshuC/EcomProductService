package com.pranshu.ecomproductservice.controller;

import com.pranshu.ecomproductservice.dto.ProductResponseDTO;
import com.pranshu.ecomproductservice.dto.SearchRequestDto;
import com.pranshu.ecomproductservice.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private SearchService searchService;

    SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping
    public Page<ProductResponseDTO> searchProducts(@RequestBody SearchRequestDto requestDto) {
        List<ProductResponseDTO> productDtos = searchService.searchProducts(
                requestDto.getTitle(),
                requestDto.getPageNumber(),
                requestDto.getPageSize());
        //return productDtos;
        // Page object provides more information. Ex : totalPages, totalElements, sort etc.
        Page<ProductResponseDTO> productDtoPage = new PageImpl<>(productDtos);
        return productDtoPage;
    }
}
