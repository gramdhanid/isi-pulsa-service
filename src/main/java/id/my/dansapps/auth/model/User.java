package id.my.dansapps.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "master_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 50)
    private Long id;
    @Column(length = 20)
    private String username;
    private String password;
    @Column(length = 50)
    private String fullName;
    @Column(length = 15)
    private String phoneNumber;
    @Column(length = 100)
    private String address;
    private String photoProfile;

}
