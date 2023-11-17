package com.thiagobrito.banking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thiagobrito.banking.domain.User.User;

public interface UserRepository extends JpaRepository<User, Long>{    
}
