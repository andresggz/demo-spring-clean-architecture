package com.udea.demo.component.user.service;

import com.udea.demo.component.user.model.User;
import com.udea.demo.component.user.service.model.UserQuerySearchCmd;
import com.udea.demo.component.user.service.model.UserSaveCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional
class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserGateway userGateway;

    public UserServiceImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public User create(@NotNull UserSaveCmd userToCreateCmd) {
        logger.debug("Begin create userToCreateCmd = {}", userToCreateCmd);

        User userToCreate = UserSaveCmd.toModel(userToCreateCmd);

        userToCreate.setPassword(userToCreateCmd.getPassword() + "hash");

        User userCreated = userGateway.save(userToCreate);

        logger.debug("End create userCreated = {}", userCreated);

        return userCreated;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(@NotNull Long id) {
        logger.debug("Begin findById id = {}", id);

        User userFound = userGateway.findById(id);

        logger.debug("End findById userFound = {}", userFound);

        return userFound;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findByParameters(@NotNull UserQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {
        logger.debug("Begin findByParameters queryCriteria = {}, pageable = {}", queryCriteria, pageable);

        Page<User> usersFound = userGateway.findByParameters(queryCriteria, pageable);

        logger.debug("End findByParameters usersFound = {}", usersFound);

        return usersFound;
    }

    @Override
    public void deleteById(@NotNull Long id) {
        logger.debug("Begin deleteById id = {}", id);

        userGateway.deleteById(id);

        logger.debug("End deleteById");
    }

    @Override
    public User update(@NotNull Long id, @NotNull UserSaveCmd userToUpdateCmd) {
        logger.debug("Begin update id = {}, userToUpdateCmd = {}", id, userToUpdateCmd);

        User userInDataBase = findById(id);

        User userToUpdate = userInDataBase.toBuilder().name(userToUpdateCmd.getName()).email(userToUpdateCmd.getEmail())
                .build();

        User userUpdated = userGateway.update(userToUpdate);

        logger.debug("End update userUpdated = {}", userUpdated);

        return userUpdated;
    }
}
