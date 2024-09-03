package org.example.gestrecrutements.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.gestrecrutements.enums.ContractType;
import org.example.gestrecrutements.enums.OffreStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Offre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String title;
    String mission;
    String experience;
    String diploma;
    String skills;
    String requirement;
    @Enumerated(EnumType.STRING)
    ContractType contractType;
    @Enumerated(EnumType.STRING)
    OffreStatus offreStatus;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @ManyToOne
    HRManager hrManager;

    @OneToMany(mappedBy = "offre", cascade = CascadeType.REMOVE)
            @JsonBackReference
    Set<Application> applications;
}
