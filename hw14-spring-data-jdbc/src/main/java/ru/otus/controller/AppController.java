package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.crm.model.dto.ClientRequestDto;
import ru.otus.service.AppService;

@Controller
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping({"", "/", "/clients"})
    public String getClients(Model model) {
        model.addAttribute("clients", appService.getAllClients());
        return "clients";
    }

    @PostMapping("/clients/new")
    public String createNewClient(@ModelAttribute("client") ClientRequestDto clientRequestDto) {
        appService.saveUpdateClient(clientRequestDto);
        return  "redirect:/clients";
    }

}
