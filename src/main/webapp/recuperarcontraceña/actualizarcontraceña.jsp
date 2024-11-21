<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Actualizar Contraseña</title>
    <script type="text/javascript">
        // Función para verificar si las contraseñas coinciden
        function validarContraseñas() {
            var contrasena = document.getElementById("nuevaContrasena").value;
            var confirmarContrasena = document.getElementById("confirmarContrasena").value;

            if (contrasena !== confirmarContrasena) {
                alert("Las contraseñas no coinciden. Por favor, verifique.");
                return false; // Prevenir el envío del formulario
            }
            return true; // Permitir el envío del formulario si las contraseñas coinciden
        }
    </script>
</head>
<body>
    <h2>Actualizar Contraseña</h2>
    <form action="../RestablecerContraseñaServlet" method="POST" onsubmit="return validarContraseñas()">
        <label for="dni">DNI:</label>
        <input type="text" id="dni" name="dni" required><br><br>

        <label for="nuevaContrasena">Nueva Contraseña:</label>
        <input type="password" id="nuevaContrasena" name="nuevaContrasena" required><br><br>

        <label for="confirmarContrasena">Confirmar Contraseña:</label>
        <input type="password" id="confirmarContrasena" name="confirmarContrasena" required><br><br>

        <input type="submit" value="Actualizar Contraseña">
    </form>
</body>
</html>
