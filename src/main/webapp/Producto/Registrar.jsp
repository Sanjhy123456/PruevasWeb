<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Productos</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center">Registro de Producto</h1>
        <!-- Formulario para registrar un producto -->
       <form action="../ProductoServlet" method="post" enctype="multipart/form-data">
    <input type="hidden" name="action" value="agregarProductos">
    <div class="form-group">
        <label for="codProducto">Código del Producto:</label>
        <input type="number" class="form-control" name="codProducto" id="codProducto" required>
    </div>
    <div class="form-group">
        <label for="nombre">Nombre:</label>
        <input type="text" class="form-control" name="nombre" id="nombre" required>
    </div>
    <div class="form-group">
        <label for="detalle">Detalle:</label>
        <input type="text" class="form-control" name="detalle" id="detalle" required>
    </div>
    <div class="form-group">
        <label for="descripcion">Descripción:</label>
        <textarea class="form-control" name="descripcion" id="descripcion" rows="3" required></textarea>
    </div>
    <div class="form-group">
        <label for="precio">Precio:</label>
        <input type="number" class="form-control" name="precio" id="precio" step="0.01" required>
    </div>
    <div class="form-group">
        <label for="codCategoria">Código de Categoría:</label>
        <input type="number" class="form-control" name="codCategoria" id="codCategoria" required>
    </div>
    <div class="form-group">
        <label for="imagen">Imagen:</label>
        <input type="file" class="form-control-file" name="imagen" id="imagen" accept="image/*" required>
    </div>
    <button type="submit" class="btn btn-success">Registrar Producto</button>
</form>

    </div>
</body>
</html>
