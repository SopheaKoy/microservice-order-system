package dev.sophea.userservice.api.user.web;

import lombok.Builder;

@Builder
public record CreateUserDto(String uuid,
                            String firstName,
                            String lastName,
                            String fullName,
                            String gender,
                            String phoneNumber,
                            String email,
                            String password,
                            Boolean isDeleted,
                            Boolean isVerified) {
}
