package validators;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class MesaCentralValidator {

    public static boolean isTelefonoMesaValido(String telefono) {
        return StringUtils.isNotBlank(telefono) && telefono.matches("^9\\d{8}$");
    }

    public static boolean isLugarValido(String lugar) {
        return StringUtils.isNotBlank(lugar) && lugar.length() >= 3;
    }

    public static List<String> validarMesaCentral(String telefono, String lugar) {
        List<String> errores = new ArrayList<>();

        if (!StringUtils.isBlank(telefono)) {
            if (!isTelefonoMesaValido(telefono)) {
                errores.add("Teléfono de mesa central inválido.");
            }

            if (StringUtils.isBlank(lugar)) {
                errores.add("Debe ingresar un lugar de mesa central si se indica un teléfono.");
            }
        }

        return errores;
    }
}
