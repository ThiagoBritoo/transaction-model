package com.thiagobrito.banking.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagobrito.banking.domain.User.User;
import com.thiagobrito.banking.domain.User.UserType;
import com.thiagobrito.banking.dtos.UserDTO;
import com.thiagobrito.banking.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Long id) throws Exception{
        return repository.findById(id).orElseThrow(() -> new Exception("User not found."));
    }

    public User createUser(UserDTO data){
        User newUser = new User(data);
        return repository.save(newUser);
    }

    public User updateUser(UserDTO data, Long id) throws Exception{
        User upUser = repository.findById(id).orElseThrow(() -> new Exception("User not found."));
        BeanUtils.copyProperties(data, upUser);
        return upUser;
    }

    public boolean deleteUser(Long id){
        if (repository.findById(id).isEmpty()) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
    
    public void validateTransaction(User sender, BigDecimal amount) throws Exception{
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("User not allowed to make transaction.");
        }
        if (amount.compareTo(sender.getBalance()) > 0) {
            throw new Exception("Not enough balance.");            
        }
    }
}
