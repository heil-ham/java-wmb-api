package com.enigma.wmbspring.entity;

import com.enigma.wmbspring.constant.TableName;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_table")
public class Tables {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private TableName tableName;
}
