package com.vlasova.cleverbank.service;

import com.vlasova.cleverbank.dao.impl.CustomerDao;
import com.vlasova.cleverbank.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class CustomerService {
    private CustomerDao dao;

    public Customer getById(Long id) {
        return dao.findById(id).orElseThrow(()
                -> new EntityNotFoundException(String.format("Customer with %d id not found", id)));
    }
}
