package com.example.banco.usuario;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioDTO {

    @NotBlank(message = "Username obrigatório")
    private String username;
    @NotBlank(message = "Password obrigatória")
    private String password;
    @NotBlank(message = "Role obrigatória.")
    private String roles;

}
