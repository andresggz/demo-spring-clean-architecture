package com.udea.demo.component.user.io.web.v1.model;

import com.udea.demo.component.user.service.model.UserQuerySearchCmd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQuerySearchRequest {

    private String name;

    private String email;

    public static UserQuerySearchCmd toModel(UserQuerySearchRequest queryCriteria) {
        return UserQuerySearchCmd.builder().name(queryCriteria.getName()).email(queryCriteria.getEmail()).build();
    }
}
