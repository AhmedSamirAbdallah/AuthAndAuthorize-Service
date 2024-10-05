package com.auth.authorize.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authority")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Authority {

    @Id
    @SequenceGenerator(name = "authority_seq", sequenceName = "authority_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_seq")
    private Long id;

    private String name;

}
