<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Actualizar Contrase�a</title>
    <script type="text/javascript">
        // Funci�n para verificar si las contrase�as coinciden
        function validarContrase�as() {
            var contrasena = document.getElementById("nuevaContrasena").value;
            var confirmarContrasena = document.getElementById("confirmarContrasena").value;

            if (contrasena !== confirmarContrasena) {
                alert("Las contrase�as no coinciden. Por favor, verifique.");
                return false; // Prevenir el env�o del formulario
            }
            return true; // Permitir el env�o del formulario si las contrase�as coinciden
        }
    </script>
</head>
<body>
    <h2>Actualizar Contrase�a</h2>
    <form action="../RestablecerContrase�aServlet" method="POST" onsubmit="return validarContrase�as()">
        <label for="dni">DNI:</label>
        <input type="text" id="dni" name="dni" required><br><br>

        <label for="nuevaContrasena">Nueva Contrase�a:</label>
        <input type="password" id="nuevaContrasena" name="nuevaContrasena" required><br><br>

        <label for="confirmarContrasena">Confirmar Contrase�a:</label>
        <input type="password" id="confirmarContrasena" name="confirmarContrasena" required><br><br>

        <input type="submit" value="Actualizar Contrase�a">
    </form>
</body>
</html>
