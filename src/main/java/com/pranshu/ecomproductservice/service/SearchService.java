package com.pranshu.ecomproductservice.service;

import com.pranshu.ecomproductservice.dto.ProductResponseDTO;
import com.pranshu.ecomproductservice.model.Product;
import com.pranshu.ecomproductservice.model.SortParam;
import com.pranshu.ecomproductservice.model.SortType;
import com.pranshu.ecomproductservice.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private ProductRepository productRepository;

    SearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDTO> searchProducts(String query, int pageNumber, int pageSize, List<SortParam> sortParams) {
        Sort sort = null;
        if (sortParams.get(0).getType() == SortType.ASC) {
            sort = Sort.by(sortParams.get(0).getName()).ascending();
        } else {
            sort = Sort.by(sortParams.get(0).getName()).descending();
        }
        for (int i = 1; i < sortParams.size(); i++) {
            if (sortParams.get(i).getType() == SortType.ASC) {
                sort.and(Sort.by(sortParams.get(i).getName()).ascending());
            } else {
                sort.and(Sort.by(sortParams.get(i).getName()).descending());
            }
        }
        System.out.println(sort.toString());

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        List<Product> products = productRepository.findAllByTitleContainingIgnoreCase(query, pageRequest);
        List<ProductResponseDTO> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(ProductResponseDTO.from(product));
        }
        return productDtos;
    }
}
