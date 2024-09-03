package org.example.gestrecrutements.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.Set;

@Entity
public class HRManager extends User implements Serializable {

    @OneToMany(mappedBy = "hrManager", cascade = CascadeType.REMOVE)
            @JsonBackReference
    Set<Offre> offres;
}
