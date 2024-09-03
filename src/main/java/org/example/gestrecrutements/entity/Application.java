package org.example.gestrecrutements.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.gestrecrutements.enums.ApplicationStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Application implements Serializable {

    @EmbeddedId
    ApplicationPK applicationPK;

    String fileCV;
    String motivationalLetter;
    @Enumerated(EnumType.STRING)
    ApplicationStatus applicationStatus;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @ManyToOne
    @MapsId("idCandidate")// Maps the embedded ID field to this relationship
    Candidate candidate;

    @ManyToOne
    @MapsId("idOffre")  // Maps the embedded ID field to this relationship
    Offre offre;
}
