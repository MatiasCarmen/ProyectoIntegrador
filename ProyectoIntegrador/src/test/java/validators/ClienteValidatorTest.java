package validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de validación de Cliente")
public class ClienteValidatorTest {

    @Test
    @DisplayName("Validar RUT correcto")
    void testRutValido() {
        assertTrue(ClienteValidator.isRutValido("12345678-9"), "RUT válido debería ser aceptado");
    }

    @Test
    @DisplayName("Validar RUT incorrecto")
    void testRutInvalido() {
        assertFalse(ClienteValidator.isRutValido("1234567-8"), "RUT inválido no debería ser aceptado");
    }

    @ParameterizedTest
    @ValueSource(strings = {"juan@gmail.com", "test@claro.cl", "usuario@dominio.com"})
    @DisplayName("Validar correos electrónicos válidos")
    void testCorreosValidos(String email) {
        assertTrue(ClienteValidator.isEmailValido(email), "Email " + email + " debería ser válido");
    }

    @ParameterizedTest
    @ValueSource(strings = {"juan@", "@claro.cl", "usuario@", "invalido"})
    @DisplayName("Validar correos electrónicos inválidos")
    void testCorreosInvalidos(String email) {
        assertFalse(ClienteValidator.isEmailValido(email), "Email " + email + " no debería ser válido");
    }

    @Test
    @DisplayName("Validar nombre correcto")
    void testNombreValido() {
        assertTrue(ClienteValidator.isNombreValido("Juan Carlos"), "Nombre válido debería ser aceptado");
        assertFalse(ClienteValidator.isNombreValido("Juan123"), "Nombre con números no debería ser aceptado");
    }

    @Test
    @DisplayName("Validar teléfono correcto")
    void testTelefonoValido() {
        assertTrue(ClienteValidator.isTelefonoValido("987654321"), "Teléfono válido debería ser aceptado");
        assertFalse(ClienteValidator.isTelefonoValido("123"), "Teléfono muy corto no debería ser aceptado");
    }

    @Test
    @DisplayName("Validar edad correcta")
    void testEdadValida() {
        assertTrue(ClienteValidator.isEdadValida(25), "Edad 25 debería ser válida");
        assertFalse(ClienteValidator.isEdadValida(15), "Edad 15 no debería ser válida");
        assertFalse(ClienteValidator.isEdadValida(121), "Edad 121 no debería ser válida");
    }

    @Test
    @DisplayName("Validar formateo de RUT")
    void testFormateoRut() {
        assertEquals("12.345.678-9",
            ClienteValidator.formatearRut("123456789"),
            "RUT debería ser formateado correctamente");
        assertEquals("1.234.567-8",
            ClienteValidator.formatearRut("12345678"),
            "RUT debería ser formateado correctamente");
    }
}
