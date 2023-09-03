package com.vlasova.cleverbank.servlet.operations;

import com.vlasova.cleverbank.exception.ValidationException;
import com.vlasova.cleverbank.service.operation.TransferService;
import com.vlasova.cleverbank.servlet.AbstractGsonServlet;
import com.vlasova.cleverbank.servlet.dto.ErrorResponse;
import com.vlasova.cleverbank.servlet.dto.TransferRequestDto;
import com.vlasova.cleverbank.validator.TransferRequestValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/transfer"})
@RequiredArgsConstructor
public class TransferServlet extends AbstractGsonServlet {
    private TransferService service;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter printWriter = response.getWriter()) {
            var transferRequestDto = new TransferRequestDto();
            transferRequestDto.setBankReceiver(request.getParameter("bankReceiver"));
            transferRequestDto.setAccountReceiver(request.getParameter("accountReceiver"));
            transferRequestDto.setBankSender(request.getParameter("bankSender"));
            transferRequestDto.setAccountSender(request.getParameter("accountSender"));
            transferRequestDto.setAmount(request.getParameter("amount"));
            transferRequestDto.setCurrency(request.getParameter("currency"));
            transferRequestDto.setPayload(request.getParameter("payload"));

            TransferRequestValidator.validate(transferRequestDto);

            var transferResponse = service.doTransferOperation(transferRequestDto);
            printWriter.println(converter.toJson(transferResponse));
        } catch (ValidationException e) {
            response.getWriter()
                    .println(converter.toJson(new ErrorResponse(400, "Invalid data format", e.getCause().toString())));
        } catch (Exception e) {
            response.getWriter()
                    .println(converter.toJson(new ErrorResponse(500, e.getMessage(), e.getCause().toString())));
        }
    }
}
