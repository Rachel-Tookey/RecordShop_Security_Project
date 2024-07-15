package com.example.group.project.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Purchases")
@Data
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id", nullable = false)
    private Long id;

    @Column(name = "customer_name", nullable = false)
    private String customer;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "record_id")
    private Record recordLink;
}
