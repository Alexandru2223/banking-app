package com.siit.bankingapp.controller;

import com.siit.bankingapp.domain.entity.TransactionEntity;
import com.siit.bankingapp.domain.model.Transaction;
import com.siit.bankingapp.domain.model.User;
import com.siit.bankingapp.mapper.TransactionEntityToTransactionMapper;
import com.siit.bankingapp.service.AccountService;
import com.siit.bankingapp.service.TransactionService;
import com.siit.bankingapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
public class TransactionController {

    private final AccountService accountService;

    private final TransactionEntityToTransactionMapper transactionEntityToTransactionMapper;

    private final UserService userService;

    private final TransactionService service;

    @GetMapping("/employee/transactions")
    @ResponseStatus(HttpStatus.OK)
    public String getAllTransactions(Model model) {

        var transactions = (List<Transaction>) service.getAllTransactions();
        model.addAttribute("transactions", transactions);
        return "user/all_transactions";
    }

    @GetMapping("/user/transactions")
    @ResponseStatus(HttpStatus.OK)
    public String getUserTransactions(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<Transaction> transactions = service.getUserTransactions(email);
        model.addAttribute("transactions", transactions);
        return "user/user_transactions";
    }


    @PostMapping(value = "/user/transaction")
    public ModelAndView registerUser(@Valid TransactionEntity transaction, BindingResult bindingResult, ModelMap modelMap) {

        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "Please correct the errors in form!");
            modelMap.addAttribute("bindingResult", bindingResult);
        } else if (!accountService.checkIfIbanExists(transaction.getConsigneeIban())) {
            modelAndView.addObject("successMessage", "IBAN doesn't exists!");
            transaction.setStatus("ERROR - IBAN doesn't exists !");
            service.create(transactionEntityToTransactionMapper.convert(transaction));
        } else if (!accountService.checkEnoughBalance(transaction.getSenderIban(), transaction.getAmount())) {
            modelAndView.addObject("successMessage", "Insufficient funds!");
            transaction.setStatus("ERROR - Insufficient funds");
            service.create(transactionEntityToTransactionMapper.convert(transaction));

        } else {

            System.out.println(transaction.getAmount());
            transaction.setStatus("SUCCES");
            Transaction createdTransaction = service.create(transactionEntityToTransactionMapper.convert(transaction));
            userService.updateAccounts(createdTransaction.getSenderIban(), createdTransaction.getConsigneeIban(), createdTransaction.getAmount(),
                    createdTransaction.getId());


            modelAndView.addObject("successMessage", "Transaction is registered successfully!");
        }

        modelAndView.addObject("transaction", new TransactionEntity());
        modelAndView.setViewName("user/transaction");
        return modelAndView;
    }

    @GetMapping(value = "/user/transaction")
    public ModelAndView sendTransaction() {

        ModelAndView modelAndView = new ModelAndView();
        TransactionEntity transaction = new TransactionEntity();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User oneUser = userService.getOneUser(authentication.getName());
        transaction.setSenderIban(oneUser.getCustomer().getAccount().getIban());
        LocalDate localDate = LocalDate.now();
        transaction.setTransactionDate(localDate);
        modelAndView.addObject("transaction", transaction);
        modelAndView.setViewName("user/transaction");

        return modelAndView;
    }


}
