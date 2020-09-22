package com.siit.bankingapp.controller;


import com.siit.bankingapp.domain.model.Customer;
import com.siit.bankingapp.service.CustomerService;
import com.siit.bankingapp.service.UserService;

import javassist.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class CustomerController {

    private final CustomerService service;

    private final UserService userService;

    @GetMapping("/employee/customers_details")
    @ResponseStatus(HttpStatus.OK)
    public String getAllCustomers(Model model) throws NotFoundException {

        List<Customer> customers = service.getAll();
        model.addAttribute("customers", customers);
        return "employee/customers_details";
    }

    @GetMapping("/user/details")
    @ResponseStatus(HttpStatus.OK)
    public String getOneUser(Model model) throws NotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        var user = userService.getOneUser(email);
        model.addAttribute("user", user);
        return "user/customerDetails";
    }

    @PostMapping(value = "/employee/update/customer")
    public String saveCustomer(@ModelAttribute("employee") Customer customer) throws NotFoundException {

        service.update(customer);

        return "employee/redirect";
    }

    @RequestMapping("/employee/edit/customer/{id}")
    public ModelAndView editCustomer(@PathVariable(name = "id") int id) throws NotFoundException {

        ModelAndView mav = new ModelAndView("customer/edit_customer");
        Customer customer = service.findById(id);
        mav.addObject("customer", customer);
        return mav;
    }


}
