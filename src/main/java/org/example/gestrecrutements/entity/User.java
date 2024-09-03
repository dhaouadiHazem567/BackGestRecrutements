package org.example.gestrecrutements.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.gestrecrutements.enums.Gender;
import org.example.gestrecrutements.enums.RoleName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PROTECTED)

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String firstname;
    String lastname;
    String phone;
    @Enumerated(EnumType.STRING)
    Gender gender;
    @Temporal(TemporalType.DATE)
    Date birthdate;
    @Column(unique = true)
    String username;
    @Column(unique = true)
    String email;
    String password;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    long code;
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles;

}
