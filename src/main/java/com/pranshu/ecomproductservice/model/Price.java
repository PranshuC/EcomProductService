package com.pranshu.ecomproductservice.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Price extends BaseModel {
    private String currency;
    private double amount;
    private double discount;
}

// @Data provides together :
// @Getter
// @Setter
// @RequiredArgsConstructor
// @ToString
// @EqualsAndHashCode
