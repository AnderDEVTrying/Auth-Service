package com.example.Auth_Service.Repositories;

import com.example.Auth_Service.Domain.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customers, Integer> {
  Customers findByEmail(String email);
}
