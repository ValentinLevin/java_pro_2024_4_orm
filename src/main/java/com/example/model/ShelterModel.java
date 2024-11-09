package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("shelter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelterModel {
    @Id
    private String id;
    private String name;
    private String location;
    private Integer capacity;
    private PET_TYPE type;
    private Double rating;
    private Boolean isGovernmentFunded;
    private Integer averageAdoptionTime;
    private Double dailyCost;
}
