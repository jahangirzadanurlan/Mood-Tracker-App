package com.example.userservice.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscribeRequestDto {
    @Email(message = "Username is invalid")
    String email;
    @NotBlank(message = "City is required")
    String city;
}
