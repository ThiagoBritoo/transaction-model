package com.thiagobrito.banking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiagobrito.banking.domain.Transaction.Transaction;
import com.thiagobrito.banking.domain.User.User;
import com.thiagobrito.banking.dtos.TransactionDTO;
import com.thiagobrito.banking.services.TransactionService;
import com.thiagobrito.banking.services.UserService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Transaction>> findAll(){
        return new ResponseEntity<>(transactionService.findAll(), HttpStatus.OK);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> findById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(transactionService.findByid(id), HttpStatus.OK);        
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<Transaction>> findBySender(@PathVariable(name = "id") long id) throws Exception{
        User user = userService.findById(id);
        return new ResponseEntity<>(transactionService.findBySender(user), HttpStatus.OK);        
    }
    
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO trasaction) throws Exception{
        return new ResponseEntity<>(transactionService.createTransaction(trasaction), HttpStatus.CREATED);
    }

    @DeleteMapping("/{ìd}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable(name = "id") Long id){
        if (!(transactionService.deleteTransaction(id))) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
