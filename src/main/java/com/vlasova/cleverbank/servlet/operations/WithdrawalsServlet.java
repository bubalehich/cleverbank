package com.vlasova.cleverbank.servlet.operations;

import com.vlasova.cleverbank.exception.ValidationException;
import com.vlasova.cleverbank.service.WithdrawalsService;
import com.vlasova.cleverbank.servlet.dto.ErrorResponse;
import com.vlasova.cleverbank.servlet.dto.WithdrawalsRequestDto;
import com.vlasova.cleverbank.servlet.dto.WithdrawalsResponse;
import com.vlasova.cleverbank.validator.WithdrawalsRequestValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/withdrawals"})
@RequiredArgsConstructor
public class WithdrawalsServlet extends AbstractGsonServlet {
    private final WithdrawalsService service;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter printWriter = response.getWriter()) {
            var withdrawalsRequest = new WithdrawalsRequestDto();
            withdrawalsRequest.setAmount(request.getParameter("amount"));
            withdrawalsRequest.setAccountNumber(request.getParameter("accountNumber"));
            withdrawalsRequest.setCurrency(request.getParameter("currency"));
            withdrawalsRequest.setBankNumber(request.getParameter("bankNumber"));

            WithdrawalsRequestValidator.validate(withdrawalsRequest);

            WithdrawalsResponse withdrawalsResponse = service.doWithdrawalsOperation(withdrawalsRequest);
            printWriter.println(converter.toJson(withdrawalsResponse));
        } catch (ValidationException e) {
            response.getWriter()
                    .println(converter.toJson(new ErrorResponse(400, "Invalid data format", e.getCause().toString())));
        } catch (Exception e) {
            response.getWriter()
                    .println(converter.toJson(new ErrorResponse(500, e.getMessage(), e.getCause().toString())));
        }
    }
}
