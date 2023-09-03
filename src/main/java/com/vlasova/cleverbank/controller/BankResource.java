package com.vlasova.cleverbank.controller;

import com.vlasova.cleverbank.controller.dto.BankDto;
import com.vlasova.cleverbank.entity.Bank;
import com.vlasova.cleverbank.service.BankService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/banks")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class BankResource {
    @Inject
    private BankService bankService;

    @Path("/{id}")
    @GET
    public Bank getById(@PathParam("id") long id) {
        return bankService.getById(id);
    }

    @POST
    public Bank create(BankDto dto){
        return bankService.create(dto);
    }

    @Path("/{id}")
    @PUT
    public Bank update(@PathParam("id") long id, BankDto dto){
        return bankService.update(dto, id);
    }

    @Path("/{id}")
    @DELETE
    public void delete(@PathParam("id") long id) {
        bankService.delete(id);
    }

    @GET
    public List<Bank> getAll(Integer offset, Integer limit) {
        return bankService.getAllWithPagination(offset,limit);
    }
}
