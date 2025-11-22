package com.example.Barberia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String fromEmail;

    public void enviarCorreoReserva(String destinatario, String asunto, String cuerpoHtml) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(cuerpoHtml, true);
            helper.setFrom(fromEmail);
            mailSender.send(mensaje);
        } catch (Exception e) {
            System.err.println("Error enviando correo: " + e.getMessage());
        }
    }

    public void enviarCorreoBienvenida(String destinatario, String nombreCliente) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
            helper.setTo(destinatario);
            helper.setSubject("¡Bienvenido a KALU Barbería!");
            helper.setText(construirCuerpoCorreoBienvenida(nombreCliente), true);
            helper.setFrom(fromEmail);
            mailSender.send(mensaje);
        } catch (Exception e) {
            System.err.println("Error enviando correo de bienvenida: " + e.getMessage());
        }
    }

    public void enviarCorreoRecuperacionContraseña(String destinatario, String nombreCliente, String nuevaContraseña) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
            helper.setTo(destinatario);
            helper.setSubject("Recuperación de contraseña - KALU Barbería");
            helper.setText(construirCuerpoCorreoRecuperacion(nombreCliente, nuevaContraseña), true);
            helper.setFrom(fromEmail);
            mailSender.send(mensaje);
        } catch (Exception e) {
            System.err.println("Error enviando correo de recuperación: " + e.getMessage());
        }
    }

    private String construirCuerpoCorreoRecuperacion(String nombreCliente, String nuevaContraseña) {
        // Puedes reemplazar los enlaces de iconos por los de tus redes reales
        String facebook = "https://facebook.com/";
        String instagram = "https://instagram.com/";
        String whatsapp = "https://wa.me/";

        return """
        <div style="background: #181818; color: #fff; font-family: Arial, sans-serif; padding: 32px; border-radius: 16px; max-width: 500px; margin: auto;">
            <div style="text-align: center;">
                <h1 style="color: #FFD700; margin-bottom: 0;">KALU Barbería</h1>
                <p style="color: #bbb; margin-top: 4px;">Recuperación de contraseña</p>
            </div>
            <hr style="border: 1px solid #FFD700; margin: 24px 0;">
            <p style="font-size: 18px;">Hola <b>%s</b>,<br>
            Has solicitado recuperar tu contraseña. Aquí está tu nueva contraseña temporal:</p>
            <div style="background: #2a2a2a; padding: 24px; border-radius: 12px; margin: 24px 0; text-align: center; border: 2px solid #FFD700;">
                <p style="font-size: 14px; color: #bbb; margin: 0 0 8px 0;">Tu nueva contraseña:</p>
                <p style="font-size: 24px; font-weight: bold; color: #FFD700; margin: 0; letter-spacing: 2px; font-family: monospace;">%s</p>
            </div>
            <div style="background: #2a2a2a; padding: 16px; border-radius: 12px; margin: 24px 0;">
                <p style="font-size: 14px; line-height: 1.7; margin: 0; color: #ff9800;">
                    <b>⚠️ Importante:</b><br>
                    • Por seguridad, te recomendamos cambiar esta contraseña después de iniciar sesión.<br>
                    • Esta contraseña es temporal y solo es válida hasta que la cambies.<br>
                    • Si no solicitaste este cambio, contacta con soporte inmediatamente.
                </p>
            </div>
            <div style="text-align: center; margin: 24px 0;">
                <span style="font-size: 18px; color: #FFD700;">¡Ya puedes iniciar sesión con tu nueva contraseña!</span>
            </div>
            <hr style="border: 1px solid #FFD700; margin: 24px 0;">
            <div style="text-align: center; margin-bottom: 12px;">
                <a href="%s" style="margin: 0 10px;"><img src="https://cdn-icons-png.flaticon.com/32/733/733547.png" alt="Facebook" style="vertical-align: middle;" /></a>
                <a href="%s" style="margin: 0 10px;"><img src="https://cdn-icons-png.flaticon.com/32/2111/2111463.png" alt="Instagram" style="vertical-align: middle;" /></a>
                <a href="%s" style="margin: 0 10px;"><img src="https://cdn-icons-png.flaticon.com/32/733/733585.png" alt="WhatsApp" style="vertical-align: middle;" /></a>
            </div>
            <div style="text-align: center; color: #bbb; font-size: 13px;">
                &copy; 2024 KALU Barbería. Todos los derechos reservados.<br>
                Juan Capurro y Juan Bustos
            </div>
        </div>
        """.formatted(
                nombreCliente,
                nuevaContraseña,
                facebook,
                instagram,
                whatsapp
        );
    }

    private String construirCuerpoCorreoBienvenida(String nombreCliente) {
        // Puedes reemplazar los enlaces de iconos por los de tus redes reales
        String facebook = "https://facebook.com/";
        String instagram = "https://instagram.com/";
        String whatsapp = "https://wa.me/";

        return """
        <div style="background: #181818; color: #fff; font-family: Arial, sans-serif; padding: 32px; border-radius: 16px; max-width: 500px; margin: auto;">
            <div style="text-align: center;">
                <h1 style="color: #FFD700; margin-bottom: 0;">KALU Barbería</h1>
                <p style="color: #bbb; margin-top: 4px;">¡Bienvenido a nuestra familia!</p>
            </div>
            <hr style="border: 1px solid #FFD700; margin: 24px 0;">
            <p style="font-size: 18px;">Hola <b>%s</b>,<br>
            ¡Gracias por registrarte en KALU Barbería! Estamos emocionados de tenerte como parte de nuestra comunidad.</p>
            <div style="background: #2a2a2a; padding: 20px; border-radius: 12px; margin: 24px 0;">
                <p style="font-size: 16px; line-height: 1.7; margin: 0;">
                    <b>Con tu cuenta podrás:</b><br>
                    ✓ Reservar tus citas de forma fácil y rápida<br>
                    ✓ Gestionar tus reservas desde cualquier lugar<br>
                    ✓ Solicitar servicios a domicilio<br>
                    ✓ Ver tu historial de servicios<br>
                    ✓ Y mucho más...
                </p>
            </div>
            <div style="text-align: center; margin: 24px 0;">
                <span style="font-size: 22px; color: #FFD700;">¡Estamos listos para darte el mejor servicio!</span>
            </div>
            <hr style="border: 1px solid #FFD700; margin: 24px 0;">
            <div style="text-align: center; margin-bottom: 12px;">
                <a href="%s" style="margin: 0 10px;"><img src="https://cdn-icons-png.flaticon.com/32/733/733547.png" alt="Facebook" style="vertical-align: middle;" /></a>
                <a href="%s" style="margin: 0 10px;"><img src="https://cdn-icons-png.flaticon.com/32/2111/2111463.png" alt="Instagram" style="vertical-align: middle;" /></a>
                <a href="%s" style="margin: 0 10px;"><img src="https://cdn-icons-png.flaticon.com/32/733/733585.png" alt="WhatsApp" style="vertical-align: middle;" /></a>
            </div>
            <div style="text-align: center; color: #bbb; font-size: 13px;">
                &copy; 2024 KALU Barbería. Todos los derechos reservados.<br>
                Juan Capurro y Juan Bustos
            </div>
        </div>
        """.formatted(
                nombreCliente,
                facebook,
                instagram,
                whatsapp
        );
    }

    public void enviarCorreoInformativo(String destinatario, String nombreCliente, String mensaje) {
        try {
            MimeMessage mensajeEmail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensajeEmail, true, "UTF-8");
            helper.setTo(destinatario);
            helper.setSubject("Información importante - KALU Barbería");
            helper.setText(construirCuerpoCorreoInformativo(nombreCliente, mensaje), true);
            helper.setFrom(fromEmail);
            mailSender.send(mensajeEmail);
        } catch (Exception e) {
            System.err.println("Error enviando correo informativo: " + e.getMessage());
        }
    }

    public void enviarCorreoPromocion(String destinatario, String nombreCliente, String nombreCupon, 
                                      int porcentajeDescuento, String fechaValidez) {
        try {
            MimeMessage mensajeEmail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensajeEmail, true, "UTF-8");
            helper.setTo(destinatario);
            helper.setSubject("¡Promoción especial para ti! - KALU Barbería");
            helper.setText(construirCuerpoCorreoPromocion(nombreCliente, nombreCupon, porcentajeDescuento, fechaValidez), true);
            helper.setFrom(fromEmail);
            mailSender.send(mensajeEmail);
        } catch (Exception e) {
            System.err.println("Error enviando correo de promoción: " + e.getMessage());
        }
    }

    private String construirCuerpoCorreoInformativo(String nombreCliente, String mensaje) {
        String facebook = "https://facebook.com/";
        String instagram = "https://instagram.com/";
        String whatsapp = "https://wa.me/";

        return """
        <div style="background: #181818; color: #fff; font-family: Arial, sans-serif; padding: 32px; border-radius: 16px; max-width: 500px; margin: auto;">
            <div style="text-align: center;">
                <h1 style="color: #FFD700; margin-bottom: 0;">KALU Barbería</h1>
                <p style="color: #bbb; margin-top: 4px;">Información importante</p>
            </div>
            <hr style="border: 1px solid #FFD700; margin: 24px 0;">
            <p style="font-size: 18px;">Hola <b>%s</b>,</p>
            <div style="background: #2a2a2a; padding: 24px; border-radius: 12px; margin: 24px 0;">
                <p style="font-size: 16px; line-height: 1.7; margin: 0; white-space: pre-wrap;">%s</p>
            </div>
            <hr style="border: 1px solid #FFD700; margin: 24px 0;">
            <div style="text-align: center; margin-bottom: 12px;">
                <a href="%s" style="margin: 0 10px;"><img src="https://cdn-icons-png.flaticon.com/32/733/733547.png" alt="Facebook" style="vertical-align: middle;" /></a>
                <a href="%s" style="margin: 0 10px;"><img src="https://cdn-icons-png.flaticon.com/32/2111/2111463.png" alt="Instagram" style="vertical-align: middle;" /></a>
                <a href="%s" style="margin: 0 10px;"><img src="https://cdn-icons-png.flaticon.com/32/733/733585.png" alt="WhatsApp" style="vertical-align: middle;" /></a>
            </div>
            <div style="text-align: center; color: #bbb; font-size: 13px;">
                &copy; 2024 KALU Barbería. Todos los derechos reservados.<br>
                Juan Capurro y Juan Bustos
            </div>
        </div>
        """.formatted(
                nombreCliente,
                mensaje,
                facebook,
                instagram,
                whatsapp
        );
    }

    private String construirCuerpoCorreoPromocion(String nombreCliente, String nombreCupon, 
                                                   int porcentajeDescuento, String fechaValidez) {
        String facebook = "https://facebook.com/";
        String instagram = "https://instagram.com/";
        String whatsapp = "https://wa.me/";

        return """
        <div style="background: #181818; color: #fff; font-family: Arial, sans-serif; padding: 32px; border-radius: 16px; max-width: 500px; margin: auto;">
            <div style="text-align: center;">
                <h1 style="color: #FFD700; margin-bottom: 0;">KALU Barbería</h1>
                <p style="color: #bbb; margin-top: 4px;">¡Promoción especial!</p>
            </div>
            <hr style="border: 1px solid #FFD700; margin: 24px 0;">
            <p style="font-size: 18px;">Hola <b>%s</b>,</p>
            <p style="font-size: 18px; text-align: center; margin: 24px 0;">
                <span style="color: #FFD700; font-size: 24px; font-weight: bold;">¡Tenemos una sorpresa para ti!</span>
            </p>
            <div style="background: linear-gradient(135deg, #FFD700 0%%, #FFA500 100%%); padding: 24px; border-radius: 12px; margin: 24px 0; text-align: center; border: 3px solid #FFD700;">
                <p style="font-size: 14px; color: #181818; margin: 0 0 8px 0; font-weight: bold;">CÓDIGO DE CUPÓN</p>
                <p style="font-size: 32px; font-weight: bold; color: #181818; margin: 0; letter-spacing: 3px; font-family: monospace;">%s</p>
            </div>
            <div style="background: #2a2a2a; padding: 24px; border-radius: 12px; margin: 24px 0; text-align: center;">
                <p style="font-size: 48px; font-weight: bold; color: #4CAF50; margin: 0;">%d%%</p>
                <p style="font-size: 20px; color: #fff; margin: 8px 0 0 0;">DE DESCUENTO</p>
            </div>
            <div style="background: #2a2a2a; padding: 16px; border-radius: 12px; margin: 24px 0;">
                <p style="font-size: 14px; line-height: 1.7; margin: 0; color: #bbb;">
                    <b style="color: #FFD700;">📅 Válido hasta:</b> %s<br><br>
                    <b style="color: #FFD700;">💡 Cómo usar tu cupón:</b><br>
                    • Reserva tu servicio a través de la app<br>
                    • Ingresa el código del cupón al momento de pagar<br>
                    • ¡Disfruta de tu descuento!
                </p>
            </div>
            <div style="text-align: center; margin: 24px 0;">
                <span style="font-size: 18px; color: #FFD700;">¡No dejes pasar esta oportunidad!</span>
            </div>
            <hr style="border: 1px solid #FFD700; margin: 24px 0;">
            <div style="text-align: center; margin-bottom: 12px;">
                <a href="%s" style="margin: 0 10px;"><img src="https://cdn-icons-png.flaticon.com/32/733/733547.png" alt="Facebook" style="vertical-align: middle;" /></a>
                <a href="%s" style="margin: 0 10px;"><img src="https://cdn-icons-png.flaticon.com/32/2111/2111463.png" alt="Instagram" style="vertical-align: middle;" /></a>
                <a href="%s" style="margin: 0 10px;"><img src="https://cdn-icons-png.flaticon.com/32/733/733585.png" alt="WhatsApp" style="vertical-align: middle;" /></a>
            </div>
            <div style="text-align: center; color: #bbb; font-size: 13px;">
                &copy; 2024 KALU Barbería. Todos los derechos reservados.<br>
                Juan Capurro y Juan Bustos
            </div>
        </div>
        """.formatted(
                nombreCliente,
                nombreCupon,
                porcentajeDescuento,
                fechaValidez,
                facebook,
                instagram,
                whatsapp
        );
    }
}
