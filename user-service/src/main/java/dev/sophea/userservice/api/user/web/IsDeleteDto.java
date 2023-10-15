package dev.sophea.userservice.api.user.web;

import lombok.Builder;

@Builder
public record IsDeleteDto(Boolean isDeleted) {
}
