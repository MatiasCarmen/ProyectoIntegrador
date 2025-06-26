package validators;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import java.util.ArrayList;
import java.util.List;

public class ClienteValidator {

    /**
     * Valida el RUT chileno
     */
    public static boolean isRutValido(String rut) {
        if (StringUtils.isBlank(rut)) {
            return false;
        }

        // Limpia el RUT de puntos y guión
        rut = StringUtils.strip(rut, ".-");

        if (!rut.matches("\\d{7,8}[0-9Kk]")) {
            return false;
        }

        try {
            String rutNum = StringUtils.left(rut, rut.length() - 1);
            char dv = Character.toUpperCase(rut.charAt(rut.length() - 1));

            int m = 0, s = 1;
            for (; rutNum != null; rutNum = StringUtils.substring(rutNum, 0, rutNum.length() - 1)) {
                s = (s + Integer.parseInt(StringUtils.right(rutNum, 1)) * (9 - m++ % 6)) % 11;
            }
            return dv == (char) (s != 0 ? s + 47 : 75);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Valida el correo electrónico usando Apache Commons Validator
     */
    public static boolean isEmailValido(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    /**
     * Valida que el nombre no contenga caracteres especiales ni números
     */
    public static boolean isNombreValido(String nombre) {
        return StringUtils.isNotBlank(nombre) &&
               nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{2,50}");
    }

    /**
     * Valida el número de teléfono (formato chileno)
     */
    public static boolean isTelefonoValido(String telefono) {
        if (StringUtils.isBlank(telefono)) {
            return false;
        }
        telefono = StringUtils.stripStart(telefono, "+");
        return telefono.matches("\\d{9}");
    }

    /**
     * Valida la edad (entre 18 y 120 años)
     */
    public static boolean isEdadValida(int edad) {
        return edad >= 18 && edad <= 120;
    }

    /**
     * Valida todos los campos de un cliente y retorna los errores encontrados
     */
    public static List<String> validarCliente(String rut, String nombres,
            String apellidoP, String apellidoM, String email, String telefono, int edad) {
        List<String> errores = new ArrayList<>();

        if (!isRutValido(rut)) {
            errores.add("RUT inválido");
        }

        if (!isNombreValido(nombres)) {
            errores.add("Nombre inválido");
        }

        if (!isNombreValido(apellidoP)) {
            errores.add("Apellido paterno inválido");
        }

        if (!isNombreValido(apellidoM)) {
            errores.add("Apellido materno inválido");
        }

        if (!isEmailValido(email)) {
            errores.add("Correo electrónico inválido");
        }

        if (!isTelefonoValido(telefono)) {
            errores.add("Número de teléfono inválido");
        }

        if (!isEdadValida(edad)) {
            errores.add("Edad inválida (debe ser entre 18 y 120 años)");
        }

        return errores;
    }

    /**
     * Formatea un RUT con puntos y guión
     */
    public static String formatearRut(String rut) {
        if (StringUtils.isBlank(rut)) {
            return "";
        }

        rut = StringUtils.strip(rut, ".-");
        int length = rut.length();

        if (length < 2) {
            return rut;
        }

        String body = StringUtils.left(rut, length - 1);
        String dv = StringUtils.right(rut, 1);

        String formatted = StringUtils.reverseDelimited(StringUtils.reverse(body), '.');
        return StringUtils.reverse(formatted) + "-" + dv;
    }
}
