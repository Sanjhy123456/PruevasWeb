package Email;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailRecuperarContraceña {

    public static void sendPasswordResetEmail(String toEmail) {
        String host = "smtp.gmail.com";  // Usando Gmail como ejemplo
        final String user = "jhordy2694@gmail.com";  // Tu correo
        final String password = "ujnl gmaa hhag lbyx";  // Tu contraseña de aplicación (debe ser una contraseña de aplicación de Google)

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Contraseña Restablecida");

            // El mensaje indicando que la contraseña ha sido restablecida exitosamente
            message.setText("Hola,\n\nTu contraseña ha sido restablecida exitosamente. Si no has realizado esta solicitud, por favor contacta con nuestro soporte técnico de inmediato.\n\nGracias.");

            // Enviar el mensaje
            Transport.send(message);
            System.out.println("Correo enviado con éxito.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error al enviar el correo: " + e.getMessage());
        }
    }
}
