package com.decor_shop_web.shopWeb.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserDTO {

    @NotEmpty(message = "Nome não pode ser vazio")
    @NotNull(message = "Nome não pode ser nulo")
    private String username;
    @NotEmpty(message = "Nome não pode ser vazio")
    @NotNull(message = "Nome não pode ser nulo")
    private String password;
    private String role;

}
