package com.planetbooks.controllers.views;

import com.planetbooks.models.Client;
import com.planetbooks.repositories.ClientRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class Dash_ClientsController {

    @Autowired
    private ClientRepository clientRepository;

    public Dash_ClientsController() {}
    // LIST ACTIVE CLIENTS
    @GetMapping({"/clients"})
    public String showClients(HttpServletRequest request, Model model) {
        List<Client> clients = clientRepository.findByActiveTrue();
        model.addAttribute("clients", clients);
        model.addAttribute("totalClients", clients.size());
        model.addAttribute("currentPath", request.getRequestURI());
        return "admin/dash-clients";
    }

    // SOFT DELETE CLIENT
    @GetMapping("/clients/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            client.setActive(false);
            clientRepository.save(client);
        }
        return "redirect:/clients";
    }

    // EDIT CLIENT
     @GetMapping("/clients/edit/{id}")
     public String showEditForm(@PathVariable Long id, Model model) {
         Optional<Client> client = clientRepository.findById(id);
         if (client.isPresent()) {
             model.addAttribute("client", client.get());
             return "admin/edit_client";
         }
         return "redirect:/clients";
     }

    @PostMapping("/clients/update")
    public String updateClient(@Valid @ModelAttribute("client") Client clientForm,
                               BindingResult result,
                               Model model) {

        if (result.hasErrors()) {
            model.addAttribute("client", clientForm);
            return "admin/edit_client";
        }

        Client clientDB = clientRepository.findById(clientForm.getId()).orElse(null);

        if (clientDB != null) {
            clientDB.setName(clientForm.getName());
            clientDB.setLast_name_father(clientForm.getLast_name_father());
            clientDB.setLast_name_mother(clientForm.getLast_name_mother());
            clientDB.setAge(clientForm.getAge());
            clientDB.setUser(clientForm.getUser());
            clientDB.setEmail(clientForm.getEmail());
            clientDB.setCountry(clientForm.getCountry());
            clientRepository.save(clientDB);
        }

        return "redirect:/clients";
    }


    // LIST DELETED CLIENTS
    @GetMapping("/clients/deleted")
    public String showDeletedClients(Model model) {
        List<Client> deletedClients = clientRepository.findByActiveTrue();
        model.addAttribute("clients", deletedClients);
        return "admin/delete_client";
    }

    // RESTORE CLIENT
    @GetMapping("/clients/restore/{id}")
    public String restoreClient(@PathVariable Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            client.setActive(true);
            clientRepository.save(client);
        }
        return "redirect:/clients";
    }

    // REGISTER CLIENT (CREATE ACCOUNT)
    @PostMapping("/clients/register")
    public String registerClient(@Valid @ModelAttribute("client") Client client,
                                 BindingResult result,
                                 Model model) {

        if (result.hasErrors()) {
            return "register";
        }

        // Default values
        client.setActive(true);
        client.setPurchases(0);
        client.setSessions(0);
        client.setRegistration_date(LocalDate.now());

        clientRepository.save(client);

        return "redirect:/clients";
    }

    @GetMapping("/clients/create")
    public String showCreateAccountForm(Model model) {
        model.addAttribute("client", new Client());
        return "register";
    }
}