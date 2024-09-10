package com.pranshu.ecomproductservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FakeStoreProductResponseDTO {
    private UUID id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;
}
