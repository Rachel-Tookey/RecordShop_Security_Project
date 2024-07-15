package com.example.group.project.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Records")
@Data
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", nullable = false)
    private Long id;

    @Column(name = "record_name", nullable = false)
    private String name;

    @Column(name = "artist", nullable = false)
    private String address;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private double price;

    @OneToOne(mappedBy = "recordLink", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Purchase purchase;
}

