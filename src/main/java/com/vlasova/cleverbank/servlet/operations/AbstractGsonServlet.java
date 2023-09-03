package com.vlasova.cleverbank.servlet.operations;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;

public class AbstractGsonServlet extends HttpServlet {
    protected final transient Gson converter;

    public AbstractGsonServlet() {
        this.converter = new Gson();
    }
}
