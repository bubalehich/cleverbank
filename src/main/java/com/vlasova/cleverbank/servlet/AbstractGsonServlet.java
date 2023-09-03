package com.vlasova.cleverbank.servlet;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;

public abstract class AbstractGsonServlet extends HttpServlet {
    protected final transient Gson converter;

    public AbstractGsonServlet() {
        this.converter = new Gson();
    }
}
