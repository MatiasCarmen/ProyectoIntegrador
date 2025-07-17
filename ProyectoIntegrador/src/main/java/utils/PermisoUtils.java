package utils;

import entidades.Usuario;
import java.util.Arrays;
import java.util.List;

/**
 * Clase utilitaria para manejar permisos y validaciones por rol de usuario
 * SOLID - SRP: Única responsabilidad de gestionar permisos
 */
public class PermisoUtils {

    // Constantes de roles
    public static final String ROL_ADMINISTRADOR = "R001";
    public static final String ROL_USUARIO = "D001";
    public static final String ROL_SUPERVISOR = "S001";

    // Constantes de vistas
    public static final String VISTA_USUARIOS = "VistaUsuarios";
    public static final String VISTA_CREAR_USUARIO = "VistaCrearUsuario";
    public static final String VISTA_CLIENTES = "VistaClientes";
    public static final String VISTA_ACTIVIDADES = "VistaActividadesPorCuenta";
    public static final String VISTA_AGENDA = "VistaAgenda";
    public static final String VISTA_MESA_CENTRAL = "VistaMesaCentral";

    /**
     * Verifica si el usuario es administrador
     */
    public static boolean esAdministrador(Usuario usuario) {
        return usuario != null && ROL_ADMINISTRADOR.equals(usuario.getIdRol());
    }

    /**
     * Verifica si el usuario es supervisor
     */
    public static boolean esSupervisor(Usuario usuario) {
        return usuario != null && ROL_SUPERVISOR.equals(usuario.getIdRol());
    }

    /**
     * Verifica si el usuario es usuario común
     */
    public static boolean esUsuarioComun(Usuario usuario) {
        return usuario != null && ROL_USUARIO.equals(usuario.getIdRol());
    }

    /**
     * Obtiene el nombre del rol para mostrar en la interfaz
     */
    public static String obtenerNombreRol(Usuario usuario) {
        if (usuario == null)
            return "Sin rol";

        switch (usuario.getIdRol()) {
            case ROL_ADMINISTRADOR:
                return "Administrador";
            case ROL_SUPERVISOR:
                return "Supervisor";
            case ROL_USUARIO:
                return "Usuario";
            default:
                return "Sin rol";
        }
    }

    /**
     * Verifica si el usuario tiene permiso para acceder a una vista específica
     */
    public static boolean tienePermisoVista(Usuario usuario, String vista) {
        if (usuario == null)
            return false;

        // Administradores tienen acceso a todo
        if (esAdministrador(usuario)) {
            return true;
        }

        // Supervisores tienen acceso a gestión de usuarios también
        if (esSupervisor(usuario)) {
            return true;
        }

        // Usuarios comunes solo tienen acceso a vistas limitadas
        if (esUsuarioComun(usuario)) {
            List<String> vistasPermitidas = Arrays.asList(
                    "CLIENTES",
                    "BUSCAR_CLIENTE",
                    "AGREGAR_CLIENTE",
                    "NUEVA_ACTIVIDAD",
                    "ACTIVIDADES",
                    "AGENDA",
                    "INICIO");
            return vistasPermitidas.contains(vista);
        }

        return false;
    }

    /**
     * Obtiene las opciones de menú permitidas para un usuario
     */
    public static String[][] obtenerOpcionesMenu(Usuario usuario) {
        if (esAdministrador(usuario) || esSupervisor(usuario)) {
            // Menú completo para administradores y supervisores
            return new String[][] {
                    { "~PRINCIPAL~" },
                    { "Inicio", "/imagenes/home.png" },
                    { "~GESTIÓN DE CLIENTES~" },
                    { "Lista de Clientes", "/imagenes/list.png" },
                    { "Buscar Cliente", "/imagenes/search.png" },
                    { "Agregar Cliente", "/imagenes/plus.png" },
                    { "~GESTIÓN DE USUARIOS~" },
                    { "Usuarios", "/imagenes/users.png" },
                    { "Crear Usuario", "/imagenes/plus.png" },
                    { "~ACTIVIDADES~" },
                    { "Nueva Actividad", "/imagenes/icons/activity.svg" },
                    { "Lista Actividades", "/imagenes/list.png" },
                    { "Agenda", "/imagenes/calendar.png" },
                    { "~MESA CENTRAL~" },
                    { "Mesa Central", "/imagenes/icons/status.svg" },
                    { "~SISTEMA~" },
                    { "Cerrar Sesión", "/imagenes/logout.png" }
            };
        } else {
            // Menú limitado para usuarios comunes
            return new String[][] {
                    { "~PRINCIPAL~" },
                    { "Inicio", "/imagenes/home.png" },
                    { "~GESTIÓN DE CLIENTES~" },
                    { "Lista de Clientes", "/imagenes/list.png" },
                    { "Buscar Cliente", "/imagenes/search.png" },
                    { "Agregar Cliente", "/imagenes/plus.png" },
                    { "~ACTIVIDADES~" },
                    { "Nueva Actividad", "/imagenes/icons/activity.svg" },
                    { "Lista Actividades", "/imagenes/list.png" },
                    { "Agenda", "/imagenes/calendar.png" },
                    { "~SISTEMA~" },
                    { "Cerrar Sesión", "/imagenes/logout.png" }
            };
        }
    }
}