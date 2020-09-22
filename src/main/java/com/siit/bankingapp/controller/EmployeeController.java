package com.siit.bankingapp.controller;

import com.siit.bankingapp.domain.model.Account;
import com.siit.bankingapp.domain.model.Customer;
import com.siit.bankingapp.domain.model.Employee;
import com.siit.bankingapp.domain.model.User;
import com.siit.bankingapp.service.AccountService;
import com.siit.bankingapp.service.CustomerService;
import com.siit.bankingapp.service.EmployeeService;
import com.siit.bankingapp.service.UserService;

import javassist.NotFoundException;

import org.springframework.http.HttpStatus;
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
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {

    private final AccountService accountService;

    private final CustomerService customerService;

    private final EmployeeService service;

    private final UserService userService;

    @GetMapping("/addMoneyToCustomer")
    public String addMoneyToCustomer(Employee employee) {

        return "employee/addMoneyToCustomer";
    }

    @RequestMapping("/addMoney/{cnp}")
    public ModelAndView addMoneyToCustomer(@PathVariable(name = "cnp") String cnp) throws NotFoundException {

        ModelAndView modelAndView = new ModelAndView("employee/update_account");
        Customer customer = customerService.findByCnp(cnp);
        Account account = accountService.findById(customer.getAccount().getAccountId());
        modelAndView.addObject("account", account);
        return modelAndView;
    }

    @RequestMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable("id") long id) throws NotFoundException {

        service.delete(id);
        return "employee/redirect";
    }

    @GetMapping("/employees")
    @ResponseStatus(HttpStatus.OK)
    public String findAllEmployees(Model model) {

        var employees = (List<Employee>) service.getAll();
        model.addAttribute("employees", employees);
        return "employee/employees";
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public String findAllUsers(Model model) {

        var users = (List<User>) userService.getAllUsers();
        model.addAttribute("users", users);
        return "employee/users";
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String getOneEmployee(@PathVariable("id") long id, Model model) throws NotFoundException {

        var employee = service.findById(id);
        model.addAttribute("employee", employee);
        return "employee/employee";
    }

    @GetMapping("/lastname/{lastName}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getOneEmployeeByLastName(@PathVariable("lastName") String lastName) throws NotFoundException {

        return service.findByLastName(lastName);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String mainPage() {

        return "employee/employeePage";
    }

    @PostMapping("/addEmployee")
    public String processAddEmployee(Employee employee) {
        Employee employee1 = service.create(employee);
        return "redirect:/employee/" + employee1.getId();
    }

    @GetMapping("/addEmployee")
    public String sendAddEmployee(Employee employee) {

        return "employee/addEmployee";
    }

    @PostMapping(value = "/updateAccount")
    public String saveMoney(@ModelAttribute("account") Account account) throws NotFoundException {

        accountService.updateBalance(account);

        return "employee/redirect";
    }

    @PostMapping(value = "/update")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) throws NotFoundException {

        service.update(employee);

        return "employee/redirect";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editEmployee(@PathVariable(name = "id") int id) throws NotFoundException {

        ModelAndView mav = new ModelAndView("employee/edit_employee");
        Employee employee = service.findById(id);
        mav.addObject("employee", employee);
        return mav;
    }

}
