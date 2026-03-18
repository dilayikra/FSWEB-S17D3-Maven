package com.workintech.zoo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZooErrorResponse {
    private int status;      // İlk sırada status olmalı
    private String message;   // İkinci sırada message
    private long timestamp;
}