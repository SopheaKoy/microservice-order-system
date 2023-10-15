package dev.sophea.userservice.api.user;

import dev.sophea.userservice.api.user.web.CreateUserDto;
import dev.sophea.userservice.api.user.web.IsDeleteDto;
import dev.sophea.userservice.api.user.web.UpdateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapStruct userMapStruct;

    @Override
    public Flux<User> getAllUser() {
        return userRepository.findByIsDeletedFalse();
    }

    @Override
    public Mono<User> saveUser(CreateUserDto createUserDto) {

        User user = User.builder()
                .uuid(UUID.randomUUID().toString())
                .firstName(createUserDto.firstName())
                .lastName(createUserDto.lastName())
                .fullName(createUserDto.firstName()+" "+ createUserDto.lastName())
                .gender(createUserDto.gender())
                .phoneNumber(createUserDto.phoneNumber())
                .email(createUserDto.email())
                .password(createUserDto.password())
                .isDeleted(createUserDto.isDeleted())
                .isVerified(createUserDto.isVerified())
                .build();

        return userRepository.save(user);
    }

    @Override
    public Mono<User> findUserByUuid(String uuid) {

        return userRepository.findUserByUuid(uuid);

    }

    @Override
    public Mono<User> updateUser(String uuid, UpdateUserDto updateUserDto) {
        return userRepository.findUserByUuid(uuid)
                .flatMap(user -> {
                    userMapStruct.mapForPartialUpdate(user, updateUserDto);
                    return userRepository.save(user);
                });
    }

    @Override
    public Mono<User> deleteUser(String uuid, IsDeleteDto isDeleteDto) {
        return userRepository.findUserByUuid(uuid).flatMap(user -> {
            userMapStruct.mapForPartialDelete(user, isDeleteDto);
            return userRepository.save(user);
        });
    }

}
