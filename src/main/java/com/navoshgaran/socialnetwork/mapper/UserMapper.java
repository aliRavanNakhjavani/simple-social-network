package com.navoshgaran.socialnetwork.mapper;

import com.navoshgaran.socialnetwork.dto.LoginUserDto;
import com.navoshgaran.socialnetwork.dto.RegisterUserDto;
import com.navoshgaran.socialnetwork.entity.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
        builder = @Builder(disableBuilder = true),
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User registerUserDtoToUser(RegisterUserDto registerUserDto);

    User loginUserDtoToUser(LoginUserDto loginUserDto);
}
