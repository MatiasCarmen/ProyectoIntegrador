package validators;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class ActividadValidator {

    public static boolean isDescripcionValida(String descripcion) {
        return StringUtils.isNotBlank(descripcion) && descripcion.length() >= 5;
    }

    public static boolean isObservacionValida(String observacion) {
        return StringUtils.isNotBlank(observacion) && observacion.length() >= 5;
    }

    public static boolean isEmailValido(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public static boolean isTelefonoValido(String telefono) {
        return StringUtils.isNotBlank(telefono) && telefono.matches("^9\\d{8}$");
    }

    public static List<String> validarDatosGenerales(String descripcion, String observacion, String email, String telefono) {
        List<String> errores = new ArrayList<>();

        if (!isDescripcionValida(descripcion)) {
            errores.add("Descripción debe tener al menos 5 caracteres.");
        }

        if (!isObservacionValida(observacion)) {
            errores.add("Observación debe tener al menos 5 caracteres.");
        }

        if (!isEmailValido(email)) {
            errores.add("Correo electrónico inválido.");
        }

        if (!isTelefonoValido(telefono)) {
            errores.add("Teléfono inválido. Debe tener 9 dígitos y comenzar con 9.");
        }

        return errores;
    }
}

