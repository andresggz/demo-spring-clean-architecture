package com.udea.demo.component.user.io.web.v1;

import com.udea.demo.component.shared.model.ErrorDetails;
import com.udea.demo.component.shared.model.ResponsePagination;
import com.udea.demo.component.user.io.web.v1.model.UserListResponse;
import com.udea.demo.component.user.io.web.v1.model.UserQuerySearchRequest;
import com.udea.demo.component.user.io.web.v1.model.UserSaveRequest;
import com.udea.demo.component.user.io.web.v1.model.UserSaveResponse;
import com.udea.demo.component.user.model.User;
import com.udea.demo.component.user.service.UserService;
import com.udea.demo.component.user.service.model.UserQuerySearchCmd;
import com.udea.demo.component.user.service.model.UserSaveCmd;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@RestController
@RequestMapping(path = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Users"}, value = "Users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ApiOperation(value = "Create an User.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @CrossOrigin(exposedHeaders = {HttpHeaders.LOCATION})
    public ResponseEntity<Void> create(@Valid @NotNull @RequestBody UserSaveRequest userToCreate) {
        logger.debug("Begin create: userToCreate = {}", userToCreate);

        UserSaveCmd userToCreateCmd = UserSaveRequest.toModel(userToCreate);

        User userCreated = userService.create(userToCreateCmd);

        URI location = fromUriString("/api/v1/users").path("/{id}")
                .buildAndExpand(userCreated.getId()).toUri();

        logger.debug("End create: userCreated = {}", userCreated);

        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Find an User by id.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = UserSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)
    })
    public ResponseEntity<UserSaveResponse> findById(@Valid @PathVariable("id") @NotNull Long id) {
        logger.debug("Begin findById: id = {}", id);

        User userFound = userService.findById(id);

        logger.debug("End findById: userFound = {}", userFound);

        return ResponseEntity.ok(UserSaveResponse.fromModel(userFound));
    }

    @GetMapping
    @ApiOperation(value = "Find users by parameters.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserListResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)

    })
    public ResponsePagination<UserListResponse> findByParameters(@Valid @NotNull UserQuerySearchRequest queryCriteria,
                                                                 @PageableDefault(page = 0, size = 10,
                                                                         direction = Sort.Direction.DESC, sort = "id")
                                                                         Pageable pageable) {
        logger.debug("Begin findByParameters: queryCriteria = {}, pageable= {}", queryCriteria, pageable);

        UserQuerySearchCmd queryCriteriaCmd = UserQuerySearchRequest.toModel(queryCriteria);

        Page<User> usersFound = userService.findByParameters(queryCriteriaCmd, pageable);
        List<UserListResponse> usersFoundList = usersFound.stream().map(UserListResponse::fromModel)
                .collect(Collectors.toList());

        logger.debug("End findByParameters: usersFound = {}", usersFound);

        return ResponsePagination.fromObject(usersFoundList, usersFound.getTotalElements(), usersFound.getNumber(),
                                             usersFoundList.size());
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete an user.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Success."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)

    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@Valid @PathVariable("id") @NotNull Long id) {

        logger.debug("Begin delete: id = {}", id);

        userService.deleteById(id);

        logger.debug("End delete: id = {}", id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Update an user.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = UserSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)

    })
    public ResponseEntity<UserSaveResponse> update(@Valid @RequestBody @NotNull UserSaveRequest userToUpdate,
                                                   @Valid @PathVariable("id") @NotNull Long id) {
        logger.debug("Begin update: userToUpdate = {}, id = {}", userToUpdate, id);

        UserSaveCmd userToUpdateCmd = UserSaveRequest.toModel(userToUpdate);

        User userUpdated = userService.update(id, userToUpdateCmd);

        logger.debug("End update: userUpdated = {}", userUpdated);

        return ResponseEntity.ok(UserSaveResponse.fromModel(userUpdated));
    }
}
