package com.vlasova.cleverbank.exception;

//import jakarta.transaction.Transactional;

import jakarta.transaction.Transactional;

@Transactional
public class ServiceException extends RuntimeException {
}
