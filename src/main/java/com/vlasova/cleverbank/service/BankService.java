package com.vlasova.cleverbank.service;


import com.vlasova.cleverbank.controller.dto.BankDto;
import com.vlasova.cleverbank.dao.impl.BankDao;
import com.vlasova.cleverbank.entity.Bank;
import com.vlasova.cleverbank.mapper.BankMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class BankService {
    private BankMapper mapper;
    private BankDao bankDao;

    public Bank getById(long id) {
        return bankDao.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Bank with id %d not found", id)));
    }

    public Bank create(BankDto dto) {
        var bank = mapper.toEntityFromDto(dto);

        return bankDao.save(bank);
    }

    public Bank update(BankDto dto, Long id) {
        var bank = mapper.toEntityFromDto(dto);
        bank.setId(id);
        bankDao.update(bank);

        return bankDao.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Bank with id %d not found", id)));
    }

    public void delete(long id) {
        bankDao.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Bank with id %d not found", id)));

        bankDao.delete(id);
    }

    public List<Bank> getAllWithPagination(Integer offset, Integer limit) {
        return bankDao.findAll(offset, limit).stream().toList();
    }
}
