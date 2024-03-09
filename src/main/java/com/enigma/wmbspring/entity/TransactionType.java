package com.enigma.wmbspring.entity;

import com.enigma.wmbspring.constant.TransType;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_trans_type")
public class TransactionType {
    @Id
    @Enumerated(EnumType.STRING)
    private TransType transType;

    @Column(name = "description")
    private String description;
}
