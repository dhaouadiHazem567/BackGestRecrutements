package org.example.gestrecrutements.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Candidate extends User implements Serializable {

    @OneToMany(mappedBy = "candidate", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    Set<Education> educations;

    @OneToMany(mappedBy = "candidate", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    Set<Experience> experiences;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.REMOVE)
            @JsonBackReference
    Set<Application> applications;

    @Override
    public boolean equals(Object o){
        if(o instanceof Candidate c){
            return username.equals(c.getUsername()) && email.equals(c.getEmail())
                    && birthdate.equals(c.getBirthdate()) && createdAt.equals(c.getCreatedAt());
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return username.hashCode() + email.hashCode() + birthdate.hashCode()
                + createdAt.hashCode();
    }
}
