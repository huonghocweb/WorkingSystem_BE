package com.huong.workingsystem.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name ="roles")
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId; 

    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(mappedBy ="roles")
    private List<User> users;

}
