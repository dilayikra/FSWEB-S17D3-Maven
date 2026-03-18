package com.workintech.zoo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Koala {
    private int id;
    private String name;
    private double weight;     // 3. sırada olmalı
    private double sleepHour;  // 4. sırada olmalı
    private String gender;
}