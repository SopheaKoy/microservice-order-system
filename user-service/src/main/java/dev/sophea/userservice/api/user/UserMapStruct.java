package dev.sophea.userservice.api.user;

import dev.sophea.userservice.api.user.web.IsDeleteDto;
import dev.sophea.userservice.api.user.web.UpdateUserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapStruct {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapForPartialUpdate(@MappingTarget User user, UpdateUserDto updateUserDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapForPartialDelete(@MappingTarget User user, IsDeleteDto isDeleteDto);

}
