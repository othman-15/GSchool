package ma.devops.gestionecole.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Etudiant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_etudiant;
    private String nom;
    private String prenom;
    private String cne;
    private String photos;
    @Temporal(TemporalType.DATE)
    private LocalDate date_naissance;
    @ManyToOne
    private Filiere filiere;
}
