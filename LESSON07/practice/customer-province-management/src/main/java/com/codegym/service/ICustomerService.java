package com.codegym.service;

import com.codegym.model.Customer;
import com.codegym.model.Province;

import java.util.Optional;

public interface ICustomerService extends IGenerateService<Customer>  {
    Iterable<Customer> findAllByProvince(Province province);
}
