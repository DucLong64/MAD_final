package com.jobfinder.job_finder.entity;

import com.jobfinder.job_finder.util.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Hoặc sử dụng InheritanceType.JOINED nếu muốn các bảng riêng biệt
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING) // Cột phân biệt loại người dùng
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;


}
