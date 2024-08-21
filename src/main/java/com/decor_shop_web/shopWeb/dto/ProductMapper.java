package com.decor_shop_web.shopWeb.dto;

import com.decor_shop_web.shopWeb.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    public abstract Product toProduct(ProductDTO productDTO);
}
