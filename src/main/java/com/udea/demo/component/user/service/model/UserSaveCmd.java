package com.udea.demo.component.user.service.model;

import com.udea.demo.component.user.model.User;
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
public class UserSaveCmd {

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

    public static User toModel(@NotNull UserSaveCmd userToCreateCmd) {
        return User.builder().name(userToCreateCmd.getName()).password(userToCreateCmd.getPassword()).email(
                userToCreateCmd.getEmail()).build();
    }
}
