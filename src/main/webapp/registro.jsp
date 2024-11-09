<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registro</title>
</head>
<body>
    <h1>Registro</h1>
   <form action="LoginServlet" method="post">
       <input type="hidden" name="action" value="registrar">
    <label for="dni">DNI:</label>
    <input type="text" id="dni" name="dni" required>

    <label for="nombre">Nombre:</label>
    <input type="text" id="nombre" name="nombre" required>

    <label for="apellido">Apellido:</label>
    <input type="text" id="apellido" name="apellido" required>

    <label for="correo">Correo:</label>
    <input type="email" id="correo" name="correo" required>

    <label for="telefono">Teléfono:</label>
    <input type="text" id="telefono" name="telefono" required>

    <label for="contraseña">Contraseña:</label>
    <input type="password" id="contraseña" name="contraseña" required>

    <!-- No incluir cod_ubicacion_cliente y cod_rol en el formulario -->
    
    <button type="submit">Registrar</button>
</form>


    <a href="login.jsp">Ya tengo una cuenta. Iniciar sesión</a>
</body>
</html>
