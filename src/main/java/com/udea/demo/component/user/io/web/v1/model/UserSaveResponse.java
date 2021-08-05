package com.udea.demo.component.user.io.web.v1.model;

import com.udea.demo.component.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSaveResponse {

    private Long id;

    private String name;

    private String password;

    private String email;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public static UserSaveResponse fromModel(User user) {
        return UserSaveResponse.builder().id(user.getId()).name(
                user.getName()).email(user.getEmail()).password(user.getPassword()).createDate(
                user.getCreateDate()).updateDate(
                user.getUpdateDate()).build();
    }
}
