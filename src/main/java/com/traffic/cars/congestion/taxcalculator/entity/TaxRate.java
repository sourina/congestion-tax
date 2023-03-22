package com.traffic.cars.congestion.taxcalculator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;



@Entity
@Table(name = "taxrate")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxRate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "start_time")
    public Time startTime;

    @Column(name = "end_time")
    public Time endTime;

    @Column(name = "amount")
    private double amount;
}
