package com.ensah.domain;
import com.ensah.commons.AbstractEntity;
import com.ensah.constantes.ERole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author med_Aziz
 * @version 1.0
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User  extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String firstName;
    private String lastName;
    private String phonenumber;
    private String normalemail;
    private String academicemail;
    private String password;
    private String picture;
    private String role;

    private String grade;

    @JsonManagedReference
    @OneToMany(mappedBy ="user")
    private List<Reservation> reservationList =new ArrayList<>();

   public User(String firstName,String lastName,String academicemail,String normalemail,String phonenumber,String password,String picture,String role,String grade){
       this.firstName = firstName;
       this.lastName = lastName;
       this.academicemail =academicemail;
       this.normalemail = normalemail;
       this.password = password;
       this.picture = picture;
       this.phonenumber=phonenumber;
       this.role = role;
       this.grade=grade;
   }
}
