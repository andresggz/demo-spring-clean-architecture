package com.udea.demo.component.user.service;

import com.udea.demo.component.user.model.User;
import com.udea.demo.component.user.service.model.UserQuerySearchCmd;
import com.udea.demo.component.user.service.model.UserSaveCmd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public interface UserService {
    
    User create(@NotNull UserSaveCmd userToCreateCmd);

    User findById(@NotNull Long id);

    Page<User> findByParameters(@NotNull UserQuerySearchCmd queryCriteriaCmd, @NotNull Pageable pageable);

    void deleteById(@NotNull Long id);

    User update(@NotNull Long id, @NotNull UserSaveCmd userToUpdateCmd);
}
