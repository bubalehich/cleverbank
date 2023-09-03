package com.vlasova.cleverbank.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        imports = {java.util.UUID.class})
public class MappersConfig {
}