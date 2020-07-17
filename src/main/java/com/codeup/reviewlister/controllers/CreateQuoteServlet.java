package com.codeup.reviewlister.controllers;

import com.codeup.reviewlister.Config;
import com.codeup.reviewlister.Quote;
import com.codeup.reviewlister.QuotesDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="CreateQuoteServlet", urlPatterns = "/quotes/create")
public class CreateQuoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       request.getRequestDispatcher("/WEB-INF/quotes/create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userAuthor = request.getParameter("author");
        String userQuote = request.getParameter("quote");
        Config config = new Config();
        QuotesDao quotesDao = new QuotesDao(config);
        quotesDao.create(userAuthor, userQuote);
        PrintWriter out = response.getWriter();
        out.println("<html><body><b>Successfully Inserted" + "</b></body></html>");
    }
}