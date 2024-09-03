package org.example.gestrecrutements.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.gestrecrutements.enums.StudyLevel;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"candidate", "id"})
@FieldDefaults(level = AccessLevel.PROTECTED)

public class Education implements Serializable {

    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Enumerated(EnumType.STRING)
    StudyLevel studyLevel;
    String establishment;
    String speciality;
    int graduationYear;
    double averageFirstYear;
    double averageSecondYear;

    @ManyToOne
    Candidate candidate;

}
