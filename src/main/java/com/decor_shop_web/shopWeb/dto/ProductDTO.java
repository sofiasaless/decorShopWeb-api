package com.decor_shop_web.shopWeb.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @NotEmpty(message = "Nome não pode ser vazio")
    @NotNull(message = "Nome não pode ser nulo")
    private String name;

    @NotEmpty(message = "Descrição não pode ser vazia")
    @NotNull(message = "Descrição não pode ser nula")
    private String description;

    @NotNull(message = "Estoque não pode ser nulo")
    private int stock;

    @NotNull(message = "Preço não pode ser nulo")
    private double price;

    private String image;

}
