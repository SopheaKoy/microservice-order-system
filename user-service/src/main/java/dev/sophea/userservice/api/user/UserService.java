package dev.sophea.userservice.api.user;

import dev.sophea.userservice.api.user.web.CreateUserDto;
import dev.sophea.userservice.api.user.web.IsDeleteDto;
import dev.sophea.userservice.api.user.web.UpdateUserDto;
import dev.sophea.userservice.api.user.web.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<User> getAllUser();

    Mono<User> saveUser(CreateUserDto createUserDto);

    Mono<User> findUserByUuid(String uuid);

    Mono<User> updateUser(String uuid, UpdateUserDto updateUserDto);

    Mono<User> deleteUser(String uuid, IsDeleteDto isDeleteDto);


}
