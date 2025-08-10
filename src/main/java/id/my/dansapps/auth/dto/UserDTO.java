package id.my.dansapps.auth.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
}
