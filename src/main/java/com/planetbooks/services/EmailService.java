package com.planetbooks.services;

import com.planetbooks.models.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendBooks(String to, List<Inventory> books) {

        StringBuilder sb = new StringBuilder();
        sb.append("Thank you for your purchase!\n\n");
        sb.append("Here are your books:\n\n");

        for (Inventory b : books) {
            sb.append("- " + b.getTitle() + "\n");
            sb.append("  Download: " + b.getDrive_link() + "\n\n");
        }

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Your PlanetBooks digital purchase");
        msg.setText(sb.toString());

        mailSender.send(msg);
    }
}
