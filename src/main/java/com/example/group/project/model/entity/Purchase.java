package com.example.group.project.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// Known bug when using Data & Builder together: you have to add NoArgsConstructor as well to allow them to run
// AllArgsConstructor then added to meet the application specific requirements
@Entity
@Table(name = "purchases")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    // Annotation allows foreign key mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Record recordLink;
}
