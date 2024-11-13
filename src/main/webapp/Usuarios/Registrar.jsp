<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agregar Usuario</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center">Agregar Usuario</h1>
        <form action="../PersonaServlet" method="post"> <!-- ACCION del Servlet -->
            <input type="hidden" name="action" value="agregarPer">
            <div class="form-group">
                <label for="dni">DNI</label>
                <input type="number" class="form-control" name="dni" required>
            </div>
            <div class="form-group">
                <label for="nombre">Nombre</label>
                <input type="text" class="form-control" name="nombre" required>
            </div>
            <div class="form-group">
                <label for="apellido">Apellido</label>
                <input type="text" class="form-control" name="apellido" required>
            </div>
            <div class="form-group">
                <label for="correo">Correo</label>
                <input type="email" class="form-control" name="correo" required>
            </div>
            <div class="form-group">
                <label for="telefono">Teléfono</label>
                <input type="number" class="form-control" name="telefono" required>
            </div>
            <div class="form-group">
                <label for="contraseña">Contraseña</label>
                <input type="password" class="form-control" name="contraseña" required>
            </div>
            <div class="form-group">
                <label for="cod_rol">Rol</label>
                <select class="form-control" name="cod_rol" required>
                    <option value="">Seleccione un Rol</option>
                    <option value="1">Administrador</option>
                    <option value="2">Trabajador</option>
                    <option value="3">Cliente</option>
                </select>
            </div>
            <button type="submit" class="btn btn-success">Registrar Usuario</button>
            <button type="button" class="btn btn-secondary" onclick="window.location.href='../Admin/num2.jsp'">Cancelar</button>
        </form>
    </div>
</body>
</html>
