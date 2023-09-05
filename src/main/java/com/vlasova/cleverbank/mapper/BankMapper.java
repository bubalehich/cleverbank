package com.vlasova.cleverbank.mapper;

import com.vlasova.cleverbank.controller.dto.BankDto;
import com.vlasova.cleverbank.entity.Bank;
import jakarta.enterprise.context.ApplicationScoped;
import org.mapstruct.Mapper;

@ApplicationScoped
@Mapper(config = MappersConfig.class)
public interface BankMapper {
    Bank toEntityFromDto(BankDto dto);
}
