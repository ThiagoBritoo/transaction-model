package com.thiagobrito.banking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thiagobrito.banking.domain.Transaction.Transaction;
import java.util.List;
import com.thiagobrito.banking.domain.User.User;


public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    List<Transaction> findBySender(User sender);    
}
