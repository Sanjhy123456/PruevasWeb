package Email;


import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailService {

    public static void sendWelcomeEmail(String toEmail) {
        // Configura las propiedades para conectar con el servidor de correo
        String host = "smtp.gmail.com";  // Puedes usar el servidor SMTP que prefieras
        final String user = "jhordy2694@gmail.com";  // Reemplaza con tu correo
        final String password = "ujnl gmaa hhag lbyx";  // Reemplaza con tu contraseña

        // Configuraciones para la conexión
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Crear una sesión
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            // Crear el mensaje de correo
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("¡Bienvenido a nuestro sistema!");
            message.setText("Hola,\n\nGracias por registrarte en nuestro sistema. ¡Estamos felices de tenerte con nosotros!\n\nSaludos,\nEl equipo");

            // Enviar el mensaje
            Transport.send(message);
            System.out.println("Correo de bienvenida enviado con éxito.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
