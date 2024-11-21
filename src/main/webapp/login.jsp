<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Login</h1>
    <form action="LoginServlet" method="post">
        <input type="hidden" name="action" value="login">
        Correo: <input type="text" name="correo" required><br>
        Contraseña: <input type="password" name="contraseña" required><br>
        <input type="submit" value="Iniciar Sesión">
    </form>

    <c:if test="${param.error == 'true'}">
        <p style="color:red;">Credenciales inválidas. Inténtalo de nuevo.</p>
    </c:if>

    <a href="registro.jsp">Registrarse</a>
    <a href="recuperarcontraceña/actualizarcontraceña.jsp">¿Olvidaste tu contraseña?</a>
</body>
</html>
