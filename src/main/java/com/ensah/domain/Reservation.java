package com.ensah.domain;
import com.ensah.commons.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author med_Aziz
 * @version 1.0
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime date_reservation;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime date_match;
    private String against_who;
    @JsonBackReference
    @OneToOne
    private Terrain terrain;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
