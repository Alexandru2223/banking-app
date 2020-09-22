package com.siit.bankingapp.controller;

import com.siit.bankingapp.domain.entity.UserEntity;
import com.siit.bankingapp.domain.model.User;
import com.siit.bankingapp.service.UserService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class AuthenticationController {

    private final UserService service;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView home() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {

        ModelAndView modelAndView = new ModelAndView();
        UserEntity userEntity = new UserEntity();
        modelAndView.addObject("userEntity", userEntity);
        modelAndView.setViewName("register");
        return modelAndView;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerUser(@Valid UserEntity userEntity, BindingResult bindingResult, ModelMap modelMap) {

        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "Please correct the errors in form!");
            modelMap.addAttribute("bindingResult", bindingResult);
        } else if (service.isUserAlreadyPresent(userEntity)) {
            modelAndView.addObject("successMessage", "user already exists!");
        } else {

            service.create(userEntity);

            modelAndView.addObject("successMessage", "User is registered successfully!");
        }

        modelAndView.addObject("userEntity", new UserEntity());
        modelAndView.setViewName("register");
        return modelAndView;
    }
    @PostMapping(value = "/update/password")
    public String savePassword(@ModelAttribute("employee") User user) throws NotFoundException {


        service.updatePassword(user);

        return "/login";
    }

    @RequestMapping("/recover-password")
    public ModelAndView editPassword() {

        ModelAndView mav = new ModelAndView("user/recover-password");
        User user = new User();
        mav.addObject("user", user);
        return mav;
    }
}

