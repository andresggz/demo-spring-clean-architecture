package com.udea.demo.component.user.io.gateway;

import com.udea.demo.component.shared.web.exception.ResourceNotFoundException;
import com.udea.demo.component.user.io.repository.UserRepository;
import com.udea.demo.component.user.model.User;
import com.udea.demo.component.user.service.UserGateway;
import com.udea.demo.component.user.service.model.UserQuerySearchCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Repository
class UserGatewayImpl implements UserGateway {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String RESOURCE_NOT_FOUND = "User not found";

    private UserRepository userRepository;

    public UserGatewayImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(@NotNull User userToCreate) {
        logger.debug("Begin save userToCreate = {}", userToCreate);

        final User userToBeCreated =
                userToCreate.toBuilder().createDate(LocalDateTime.now()).updateDate(LocalDateTime.now()).build();

        final User userCreated = userRepository.save(userToBeCreated);

        logger.debug("End save userCreated = {}", userCreated);

        return userCreated;
    }

    @Override
    public User findById(@NotNull Long id) {
        logger.debug("Begin findById id = {}", id);

        User userFound = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));

        logger.debug("End findById userFound = {}", userFound);

        return userFound;
    }

    @Override
    public Page<User> findByParameters(@NotNull UserQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {
        logger.debug("Begin findByParameters queryCriteria = {}, pageable = {}", queryCriteria, pageable);

        Specification<User> specification = buildCriteria(queryCriteria);

        Page<User> resourcesFound = userRepository.findAll(specification, pageable);

        logger.debug("End findByParameters resourcesFound = {}", resourcesFound);

        return resourcesFound;
    }

    @Override
    public void deleteById(@NotNull Long id) {
        logger.debug("Begin deleteById id = {}", id);

        findById(id);
        userRepository.deleteById(id);

        logger.debug("End deleteById");
    }

    @Override
    public User update(@NotNull User userToUpdate) {
        logger.debug("Begin update userToUpdate = {}", userToUpdate);

        final User userToBeUpdated =
                userToUpdate.toBuilder().updateDate(LocalDateTime.now()).build();

        final User userUpdated = userRepository.save(userToBeUpdated);

        logger.debug("End update userUpdated = {}", userUpdated);

        return userUpdated;
    }


    private Specification<User> buildCriteria(@NotNull UserQuerySearchCmd queryCriteria) {
        logger.debug("Begin buildCriteria queryCriteria = {}", queryCriteria);

        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (nonNull(queryCriteria.getName())) {
                predicates
                        .add(criteriaBuilder.and(
                                criteriaBuilder.like(root.get("name"), "%" + queryCriteria.getName() + "%")));
            }

            if (nonNull(queryCriteria.getEmail())) {
                predicates.add(criteriaBuilder
                                       .and(criteriaBuilder.like(root.get("email"),
                                                                 "%" + queryCriteria.getEmail() + "%")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
