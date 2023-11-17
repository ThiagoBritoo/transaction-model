package com.thiagobrito.banking.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.thiagobrito.banking.domain.Transaction.Transaction;
import com.thiagobrito.banking.domain.User.User;
import com.thiagobrito.banking.domain.User.UserType;
import com.thiagobrito.banking.dtos.TransactionDTO;
import com.thiagobrito.banking.repositories.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    public List<Transaction> findAll(){
        return repository.findAll();
    }

    public List<Transaction> findBySender(User user){
        return repository.findBySender(user);
    }

    public Transaction findByid(Long id){
        return repository.findById(id).orElseThrow();
    }
    
    public Transaction createTransaction(TransactionDTO transaction) throws Exception{

        User sender = userService.findById(transaction.senderId());
        User receiver = userService.findById(transaction.receiverId());
        boolean isAuthorized = authorizeTransaction(sender, transaction.amount());

        validateTransaction(sender, receiver, transaction.amount());

        if (!isAuthorized) {
            throw new Exception("Transaction not authorized.");
        }

        var newTransaction = makeTransaction(sender, receiver, transaction.amount());        

        repository.save(newTransaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);

        return newTransaction;
    }

    @Transactional
    public Transaction makeTransaction(User sender, User receiver, BigDecimal amount){
        
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(amount);
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());        

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        return newTransaction;
    }

    public void validateTransaction(User sender, User receiver, BigDecimal amount) throws Exception{
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("User not allowed to make transaction.");
        }
        if (amount.compareTo(sender.getBalance()) > 0) {
            throw new Exception("Not enough balance.");            
        }
        if (sender.equals(receiver)) {
            throw new Exception("Self-transfer not allowed.");            
        }
    }

    public boolean authorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);     
        }
        return false;
    }      
    
    public boolean deleteTransaction(long id){
        if (repository.findById(id).isEmpty()) {
            return false;
        }        
        repository.deleteById(id);
        return true;
    }
}