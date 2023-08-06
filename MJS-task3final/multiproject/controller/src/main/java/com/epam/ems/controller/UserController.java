package com.epam.ems.controller;

import com.epam.ems.converter.UserConverter;
import com.epam.ems.dto.UserDTO;
import com.epam.ems.response.InfoResponse;
import com.epam.ems.validator.PaginationValidator;
import com.epam.esm.entity.Pagination;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.ServiceException;

import com.epam.esm.service.DataGenerator;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("users")
public class UserController {

    private UserService userService;
    private DataGenerator dataGeneratorService;
    private MessageSource messageSource;

    @Autowired
    public UserController(UserService userService, DataGenerator dataGeneratorService, MessageSource messageSource) {
        this.userService = userService;
        this.dataGeneratorService = dataGeneratorService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public HttpEntity<List<UserDTO>> getUsers(Pagination pagination, Locale locale) throws ServiceException, BusinessValidationException {
        PaginationValidator.validatePagination(pagination, locale);
        List<UserDTO> usersDTO = userService.getUsers(pagination)
                .stream()
                .map(UserConverter::convertToDto)
                .collect(Collectors.toList());
        addTagsHATEOAS(usersDTO, pagination, locale);
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/{userId}")
    public HttpEntity<UserDTO> getUser(@PathVariable Long userId, Locale locale, Pagination pagination) throws ServiceException, BusinessValidationException {
        UserDTO userDTO = UserConverter.convertToDto(userService.get(userId));
        addTagHATEOAS(userDTO, pagination, locale);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/generate")
    public InfoResponse generateUsers(@RequestParam(value = "count") int count, Locale locale) throws ServiceException {
        dataGeneratorService.generateUsers(count);
        int numberOfRows = userService.getAllUsers().size();
        String message = messageSource.getMessage("label.users.total.rows", null, locale);
        return new InfoResponse(
                HttpStatus.OK.value(),
                message + ":" + numberOfRows,
                HttpStatus.OK.toString());
    }

    private void addTagsHATEOAS(List<UserDTO> usersDTO, Pagination pagination, Locale locale) throws ServiceException, BusinessValidationException {
        for (UserDTO userDTO : usersDTO) {
            userDTO.add(linkTo(methodOn(UserController.class).getUser(userDTO.getId(), locale, pagination)).withSelfRel());
        }
    }

    private void addTagHATEOAS(UserDTO userDTO, Pagination pagination, Locale locale) throws ServiceException, BusinessValidationException {
        userDTO.add(linkTo(methodOn(UserController.class).getUser(userDTO.getId(), locale, pagination)).withSelfRel());
        userDTO.add(linkTo(methodOn(UserController.class).getUsers(pagination, locale)).withRel("users"));
    }
}
