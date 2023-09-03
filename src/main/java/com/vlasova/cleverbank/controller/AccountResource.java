package com.vlasova.cleverbank.controller;

import com.vlasova.cleverbank.controller.dto.CreateAccountDto;
import com.vlasova.cleverbank.controller.dto.UpdateAccountDto;
import com.vlasova.cleverbank.entity.accounte.Account;
import com.vlasova.cleverbank.service.AccountService;
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

@Path("/accounts")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {
    @Inject
    private AccountService service;

    @Path("/{id}")
    @GET
    public Account getById(@PathParam("id") long id) {
        return service.getById(id);
    }

    @POST
    public Account create(CreateAccountDto dto) {
        return service.create(dto);
    }

    @Path("/{id}")
    @PUT
    public Account update(@PathParam("id") long id, UpdateAccountDto dto) {
        return service.update(dto, id);
    }

    @Path("/{id}")
    @DELETE
    public void delete(@PathParam("id") long id) {
        service.delete(id);
    }

    @GET
    public List<Account> getAll(Integer offset, Integer limit) {
        return service.getAllWithPagination(offset, limit);
    }
}
