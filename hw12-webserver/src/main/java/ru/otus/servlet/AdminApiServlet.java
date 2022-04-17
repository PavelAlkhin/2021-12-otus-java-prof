package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceUserClient;
import ru.otus.services.dto.ClientDto;

import java.io.IOException;
import java.util.List;


public class AdminApiServlet extends HttpServlet {

    private static final String CLIENT_NAME = "clientNameTextBox";
    private static final String CLIENT_ADDRESS = "clientAddressTextBox";
    private static final String CLIENT_PHONE = "clientPhoneTextBox";

    private final Gson gson;
    private final DBServiceUserClient dbService;


    public AdminApiServlet(Gson gson, DBServiceUserClient dbService){
        this.gson = gson;
        this.dbService = dbService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<ClientDto> clients = dbService.findAllClients();
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(clients));
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
