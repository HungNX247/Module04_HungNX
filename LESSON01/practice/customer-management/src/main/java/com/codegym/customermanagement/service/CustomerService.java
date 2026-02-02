package com.codegym.customermanagement.service;

import com.codegym.customermanagement.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();

    Customer findById(int id);
}
