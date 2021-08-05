package com.udea.demo.component.user.service.model;

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
public class UserQuerySearchCmd {

    private String name;

    private String email;
}
