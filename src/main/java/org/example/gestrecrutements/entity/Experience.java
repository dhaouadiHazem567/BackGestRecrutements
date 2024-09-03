package org.example.gestrecrutements.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.gestrecrutements.enums.ContractType;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"candidate", "id"})
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Experience implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String jobProfile;
    String duration;
    @Enumerated(EnumType.STRING)
    ContractType contractType;
    String jobFunction;

    @ManyToOne
    Candidate candidate;
}
