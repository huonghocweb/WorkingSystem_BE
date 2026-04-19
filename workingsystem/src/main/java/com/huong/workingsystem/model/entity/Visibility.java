package com.huong.workingsystem.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "visibilities")
public class Visibility {
    @Id
    @GeneratedValue(strategy =   GenerationType.IDENTITY)
    @Column(name ="visibility_id")
    private Integer visibilityId;

    @Column(name="visibility_name")
    private String visibilityName;
}
