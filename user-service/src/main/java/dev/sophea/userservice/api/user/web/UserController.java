package dev.sophea.userservice.api.user.web;

import dev.sophea.userservice.api.user.User;
import dev.sophea.userservice.api.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class  UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Flux<User> findAllUser() {
        return userService.getAllUser();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<Void> saveUser(@RequestBody CreateUserDto createUserDto) {

        return userService.saveUser(createUserDto).then();

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{uuid}")
    public Mono<User> findUserByUuid(@PathVariable String uuid) {
        return userService.findUserByUuid(uuid);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{uuid}/updated")
    public Mono<Void> updateUser(@PathVariable String uuid, @RequestBody UpdateUserDto updateUserDto){

        return userService.updateUser(uuid, updateUserDto).then();

    }

    @DeleteMapping("/{uuid}/deleted")
    public Mono<Void> deleteUser(@PathVariable String uuid, @RequestBody IsDeleteDto isDeleteDto) {
        return userService.deleteUser(uuid, isDeleteDto).then();
    }


}
