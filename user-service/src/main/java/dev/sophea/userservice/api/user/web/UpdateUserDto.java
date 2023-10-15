package dev.sophea.userservice.api.user.web;

import lombok.Builder;
@Builder
public record UpdateUserDto(String firstName,
                            String lastName,
                            String gender,
                            String fullName,
                            String phoneNumber) {
}
