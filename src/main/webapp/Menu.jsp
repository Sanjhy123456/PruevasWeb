<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administrador - Intranet</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar (Expanded Full Height) -->
            <nav id="sidebarMenu" class="col-md-3 col-lg-2 bg-dark sidebar vh-100">
                <div class="position-sticky pt-3">
                    <div class="text-center mb-3">
                        <img src="path/to/admin-logo.png" alt="Admin Logo" class="admin-logo mb-3">
                        <h4 class="text-white">Cliente</h4>
                    </div>
                    <ul class="nav flex-column fs-5">
                        <!-- Home -->
                        <li class="nav-item">
                            <a class="nav-link text-light" href="index.jsp">
                                <i class="bi bi-house-door-fill me-2"></i> Inicio
                            </a>
                        </li>
                        <!-- Mantenimiento Dropdown -->
                        <li class="nav-item">
                            <a class="nav-link text-light d-flex align-items-center" data-bs-toggle="collapse" href="#maintenanceMenu" role="button" aria-expanded="false" aria-controls="maintenanceMenu">
                                <i class="bi bi-tools me-2"></i> Mantenimiento
                            </a>
                            <div class="collapse" id="maintenanceMenu">
                                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small ps-3">
                                    <li><a href="usuarios.jsp" class="nav-link text-light"><i class="bi bi-people-fill me-2"></i>Usuarios</a></li>
                                    <li><a href="productos.jsp" class="nav-link text-light"><i class="bi bi-box-seam me-2"></i>Productos</a></li>
                                </ul>
                            </div>
                        </li>
                        <!-- Reports -->
                        <li class="nav-item">
                            <a class="nav-link text-light" href="reports.jsp">
                                <i class="bi bi-bar-chart-fill me-2"></i> Reportes
                            </a>
                        </li>
                        <!-- Settings -->
                        <li class="nav-item">
                            <a class="nav-link text-light" href="settings.jsp">
                                <i class="bi bi-gear-fill me-2"></i> Configuración de Datos
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>
            <!-- Main content -->
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">Bienvenido, Administrador</h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                        <button class="btn btn-outline-light bg-dark text-white">
                            <i class="bi bi-box-arrow-right me-1"></i> Cerrar Sesión
                        </button>
                    </div>
                </div>
                <!-- Dashboard content here -->
                <p class="lead">Utiliza el menú de la izquierda para acceder a las diferentes secciones de administración del sistema.</p>
            </main>
        </div>
    </div>

    <!-- Bootstrap JS y dependencias -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
