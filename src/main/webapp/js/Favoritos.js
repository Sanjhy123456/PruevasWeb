// Favorito.js

function agregarAFavoritos(codProducto) {
    $.ajax({
        url: 'FavoritosServlet?action=agregarQuitarFavorito',
        type: 'POST',
        data: { cod_producto: codProducto },
        success: function(data) {
            // Recargar la página o actualizar el estado del botón de favoritos
            location.reload(); // Recargar la página para actualizar el estado de favoritos
        },
        error: function(xhr, status, error) {
            console.error('Error al agregar o quitar de favoritos:', error);
        }
    });
}

// Verificar si un producto está en favoritos
function esFavorito(codProducto) {
    $.ajax({
        url: 'FavoritosServlet?action=verificarFavorito',
        type: 'GET',
        data: { cod_producto: codProducto },
        success: function(data) {
            if (data === 'true') {
                // Cambiar el estado del botón de favorito a activo
                $('#favorito_' + codProducto).text('Quitar de favoritos');
            } else {
                // Cambiar el estado del botón de favorito a inactivo
                $('#favorito_' + codProducto).text('Agregar a favoritos');
            }
        },
        error: function(xhr, status, error) {
            console.error('Error al verificar el estado de favoritos:', error);
        }
    });
}
