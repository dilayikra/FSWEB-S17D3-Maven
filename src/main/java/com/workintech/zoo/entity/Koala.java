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
    private double sleepHour;  // 3. sırada: Test bunu bekliyor
    private double weight;     // 4. sırada: Test bunu bekliyor
    private String gender;
}