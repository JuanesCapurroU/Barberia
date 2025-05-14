package com.example.Barberia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoReserva(String destinatario, String asunto, String cuerpoHtml) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(cuerpoHtml, true); // true = es HTML
            helper.setFrom("tu_cuenta@gmail.com"); // Cambia por tu correo real
            mailSender.send(mensaje);
        } catch (Exception e) {
            // Puedes loguear el error o manejarlo según tu necesidad
            System.err.println("Error enviando correo: " + e.getMessage());
        }
    }
}
