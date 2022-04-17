package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceUserClient;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminServlet extends HttpServlet {

    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final String CLIENT_NAME = "clientNameTextBox";
    private static final String CLIENT_ADDRESS = "clientAddressTextBox";
    private static final String CLIENT_PHONE = "clientPhoneTextBox";

    private final TemplateProcessor templateProcessor;
    private final DBServiceUserClient dbService;


    public AdminServlet(TemplateProcessor templateProcessor, DBServiceUserClient dbService){
        this.templateProcessor = templateProcessor;
        this.dbService = dbService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter(CLIENT_NAME);
        String address = request.getParameter(CLIENT_ADDRESS);
        String phone = request.getParameter(CLIENT_PHONE);

        dbService.saveClient(new Client(name, new Address(address), List.of(new Phone(phone))));
        response.sendRedirect("/admin");

    }

}
