package org.example.gestrecrutements.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordRequest {

    long idUser;
    String oldPassword;
    String newPassword;

}
