package com.siit.bankingapp.controller;

import com.siit.bankingapp.service.UserService;

import javassist.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @RequestMapping("/employee/deleteUser/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable("id") long id) throws NotFoundException {

        service.delete(id);
        return "employee/redirect";
    }

    @RequestMapping("/user")
    public String redirect() {

        return "user/user";
    }


}
