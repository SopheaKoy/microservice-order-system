package dev.sophea.userservice.api.user;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class User {

    @MongoId
    private String id;
    private String uuid;

    @Field(name = "first_name")
    private String firstName;

    @Field(name = "last_name")
    private String lastName;

    @Field(name = "full_name")
    private String fullName;

    private String gender;

    @Field(name = "phone_number")
    private String phoneNumber;

    private String email;
    private String password;

    @Field(name = "is_deleted")
    private Boolean isDeleted;

    @Field(name = "is_verified")
    private Boolean isVerified;


}
