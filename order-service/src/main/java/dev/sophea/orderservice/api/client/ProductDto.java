package dev.sophea.orderservice.api.client;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;

    private String uuid;

    private String name;

    private String description;

    private Double price;

    private Boolean status;
}
