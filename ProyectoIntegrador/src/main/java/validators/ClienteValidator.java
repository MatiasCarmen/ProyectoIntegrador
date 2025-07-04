package validators;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import java.util.ArrayList;
import java.util.List;

public class ClienteValidator {

   
    public static boolean isRutValido(String rut) {
        if (StringUtils.isBlank(rut)) return false;

        rut = rut.trim().toUpperCase();

        // Acepta formato simple: 8 dígitos + guión + número o K para simular lo chileno maomenos
        return rut.matches("\\d{8}-[0-9K]");
    }

    public static boolean isEmailValido(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public static boolean isNombreValido(String nombre) {
        return StringUtils.isNotBlank(nombre) &&
               nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{2,50}");
    }

    public static boolean isTelefonoValido(String telefono) {
        if (StringUtils.isBlank(telefono)) return false;
        telefono = StringUtils.stripStart(telefono, "+");
        return telefono.matches("\\d{9}");
    }

    public static boolean isEdadValida(int edad) {
        return edad >= 18 && edad <= 100;
    }

    public static List<String> validarCliente(String rut, String nombres,
            String apellidoP, String apellidoM, String email, String telefono, int edad) {
        List<String> errores = new ArrayList<>();

        if (!isRutValido(rut)) errores.add("RUT inválido");
        if (!isNombreValido(nombres)) errores.add("Nombre inválido");
        if (!isNombreValido(apellidoP)) errores.add("Apellido paterno inválido");
        if (!isNombreValido(apellidoM)) errores.add("Apellido materno inválido");
        if (!isEmailValido(email)) errores.add("Correo electrónico inválido");
        if (!isTelefonoValido(telefono)) errores.add("Número de teléfono inválido");
        if (!isEdadValida(edad)) errores.add("Edad inválida (debe ser entre 18 y 120 años)");

        return errores;
    }

    public static String formatearRut(String rut) {
        if (StringUtils.isBlank(rut)) return "";

        rut = rut.replace(".", "").replace("-", "").toUpperCase();
        int length = rut.length();
        if (length < 2) return rut;

        String cuerpo = rut.substring(0, rut.length() - 1);
        String dv = rut.substring(rut.length() - 1);

        return cuerpo + "-" + dv;
    }
}