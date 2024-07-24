package com.example.group.project.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Known bug when using Data & Builder together: you have to add NoArgsConstructor as well to allow them to run
// AllArgsConstructor then added to meet the application specific requirements
@Entity
@Table(name = "records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", nullable = false)
    private Long id;

    @Column(name = "record_name", nullable = false)
    private String name;

    @Column(name = "artist", nullable = false)
    private String artist;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private double price;

}

