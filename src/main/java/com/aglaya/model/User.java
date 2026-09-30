package com.aglaya.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "username", nullable = false, unique = true, length = 200)
    String username;

    @Column(name = "password", nullable = false, length = 200)
    String password;

    @Column(name = "email", nullable = false, unique = true, length = 200)
    String email;
}
