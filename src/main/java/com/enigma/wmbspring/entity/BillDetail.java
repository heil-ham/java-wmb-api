package com.enigma.wmbspring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_bill_detail")
public class BillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    @JsonBackReference
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Column(name = "price", updatable = false, nullable = false)
    private Long price;
}
