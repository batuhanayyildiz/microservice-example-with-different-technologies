package com.demo.userservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.management.relation.Role;
import java.util.UUID;
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="username",nullable = false,unique = true)
    private String username;
    @Column(name = "email",nullable = false)
    private String email;
    @Column(name="name")
    private String name;
    @Column(name="surname")
    private String surname;
    @Column(name = "address")
    private String address;
    @Column(name="phone_number")
    private String phoneNumber;



//    private Role role;

}
