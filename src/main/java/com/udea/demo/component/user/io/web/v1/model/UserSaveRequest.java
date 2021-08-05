package com.udea.demo.component.user.io.web.v1.model;

import com.udea.demo.component.user.service.model.UserSaveCmd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSaveRequest {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 45)
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 45)
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 100)
    private String email;

    public static UserSaveCmd toModel(UserSaveRequest userToCreate) {
        return UserSaveCmd.builder().name(userToCreate.getName()).password(userToCreate.getPassword())
                .email(userToCreate.getEmail()).build();
    }
}
