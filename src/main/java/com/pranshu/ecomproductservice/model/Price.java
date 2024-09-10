package com.pranshu.ecomproductservice.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Price extends BaseModel {
    private String currency;
    private double amount;
    private double discount;
}

// @Data provides together :
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
