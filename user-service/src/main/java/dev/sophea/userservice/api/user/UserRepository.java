package dev.sophea.userservice.api.user;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Flux<User> findByIsDeletedFalse();
    Mono<Boolean> existsByUuid(String uuid);

    Mono<User> findUserByUuid(String uuid);

    Mono<User> findByUuidAndIsDeleted(String uuid , Boolean isDeleted);

}
