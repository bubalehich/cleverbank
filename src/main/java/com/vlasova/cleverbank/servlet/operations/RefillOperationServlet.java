package com.vlasova.cleverbank.servlet.operations;

import com.vlasova.cleverbank.exception.ValidationException;
import com.vlasova.cleverbank.service.RefillService;
import com.vlasova.cleverbank.servlet.dto.ErrorResponse;
import com.vlasova.cleverbank.servlet.dto.RefillRequestDto;
import com.vlasova.cleverbank.servlet.dto.RefillResponse;
import com.vlasova.cleverbank.validator.RefillRequestValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/refill"})
@RequiredArgsConstructor
public class RefillOperationServlet extends AbstractGsonServlet {
    private final RefillService service;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter printWriter = response.getWriter()) {
            var refillRequest = new RefillRequestDto();
            refillRequest.setAmount(request.getParameter("amount"));
            refillRequest.setAccountNumber(request.getParameter("accountNumber"));
            refillRequest.setCurrency(request.getParameter("currency"));
            refillRequest.setBankNumber(request.getParameter("bankNumber"));

            RefillRequestValidator.validate(refillRequest);

            RefillResponse refillResponse = service.doRefillOperation(refillRequest);
            printWriter.println(converter.toJson(refillResponse));
        } catch (ValidationException e) {
            response.getWriter()
                    .println(converter.toJson(new ErrorResponse(400, "Invalid data format", e.getCause().toString())));
        } catch (Exception e) {
            response.getWriter()
                    .println(converter.toJson(new ErrorResponse(500, e.getMessage(), e.getCause().toString())));
        }
    }
}
