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
public class UserListResponse {

    private Long id;

    private String name;

    private String email;

    private LocalDateTime updateDate;

    public static UserListResponse fromModel(User user) {
        return UserListResponse.builder().id(user.getId()).name(user.getName()).email(user.getEmail()).updateDate(
                user.getUpdateDate()).build();
    }
}
