<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
</head>
<body>
    <h2><%= request.getAttribute("mensaje") != null ? request.getAttribute("mensaje") : "Error desconocido" %></h2>
    <a href="../Persona.jsp">Volver al inicio</a>
</body>
</html>
