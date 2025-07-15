package validators;

import entidades.Usuario;
import java.util.regex.Pattern;

public class UsuarioValidator {
    public static boolean esCampoVacio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }

    public static boolean esRutValido(String rut) {
        return ClienteValidator.isRutValido(rut); // Usa el validador que ya tienes
    }

    public static boolean esRolAdministrador(String idRol) {
        return "R001".equals(idRol); // R001 = Administrador
    }

    public static boolean esCorreoValido(String correo) {
        return Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", correo);
    }
    
    public static boolean validarCampos(String nombres, String apellidoP, String rut, String clave) {
    if (nombres == null || nombres.trim().isEmpty()) return false;
    if (apellidoP == null || apellidoP.trim().isEmpty()) return false;
    
    // Validación básica de RUT (formato chileno: XXXXXXXX-Z)
    if (rut == null || !rut.matches("\\d{7,8}-[\\dkK]")) return false;

    // Clave mínima de 6 caracteres
    if (clave == null || clave.trim().length() < 6) return false;

    return true;
}
}
