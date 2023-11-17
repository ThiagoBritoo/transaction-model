package com.thiagobrito.banking.dtos;

import java.math.BigDecimal;

public record UserDTO(String name, String email, String document, String password, BigDecimal balance, com.thiagobrito.banking.domain.User.UserType userType) {
}
