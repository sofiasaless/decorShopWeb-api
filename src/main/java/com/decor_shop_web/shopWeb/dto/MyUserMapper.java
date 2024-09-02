package com.decor_shop_web.shopWeb.dto;

import com.decor_shop_web.shopWeb.model.MyUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class MyUserMapper {

    public static final MyUserMapper INSTANCE = Mappers.getMapper(MyUserMapper.class);

    public abstract MyUser toMyUser(MyUserDTO myUserDto);

}
