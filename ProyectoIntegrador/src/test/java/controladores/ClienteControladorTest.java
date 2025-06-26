package controladores;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import entidades.Cliente;
import java.util.List;

@DisplayName("Pruebas para ClienteControlador")
public class ClienteControladorTest {

    private ClienteControlador controlador;
    private Cliente clientePrueba;

    @BeforeEach
    void setUp() {
        controlador = new ClienteControlador();
        clientePrueba = new Cliente();
        clientePrueba.setRut("11111111-1");
        clientePrueba.setNombres("Test");
        clientePrueba.setApellidoP("Controlador");
        clientePrueba.setApellidoM("Prueba");
        clientePrueba.setCorreo("test.controlador@claro.cl");
        clientePrueba.setTelefono(911111111L);
        clientePrueba.setEdad((byte)30);
        clientePrueba.setDireccion("Av. Test 456");
        clientePrueba.setIdComuna("13101");
    }

    @Test
    @DisplayName("Crear Cliente con Validación - Caso Exitoso")
    void testCrearClienteConValidacion() {
        // Act
        List<String> errores = controlador.crearClienteConValidacion(clientePrueba);

        // Assert
        assertNull(errores, "No deberían haber errores de validación");

        // Cleanup
        controlador.eliminarCliente(clientePrueba.getRut());
    }

    @Test
    @DisplayName("Crear Cliente con Validación - Caso Error")
    void testCrearClienteConValidacionError() {
        // Arrange
        clientePrueba.setEdad((byte)15); // Edad inválida

        // Act
        List<String> errores = controlador.crearClienteConValidacion(clientePrueba);

        // Assert
        assertNotNull(errores, "Deberían haber errores de validación");
        assertFalse(errores.isEmpty(), "La lista de errores no debería estar vacía");
    }

    @Test
    @DisplayName("Actualizar Cliente con Validación - Caso Exitoso")
    void testActualizarClienteConValidacion() {
        // Arrange
        controlador.crearCliente(clientePrueba);
        clientePrueba.setCorreo("nuevo.test@claro.cl");

        // Act
        List<String> errores = controlador.actualizarClienteConValidacion(clientePrueba);

        // Assert
        assertNull(errores, "No deberían haber errores de validación");

        // Cleanup
        controlador.eliminarCliente(clientePrueba.getRut());
    }

    @Test
    @DisplayName("Buscar Clientes - Criterio Válido")
    void testBuscarClientes() {
        // Arrange
        controlador.crearCliente(clientePrueba);

        // Act
        List<Cliente> resultados = controlador.buscarClientes(clientePrueba.getNombres());

        // Assert
        assertNotNull(resultados, "La lista de resultados no debería ser null");
        assertFalse(resultados.isEmpty(), "Deberían encontrarse resultados");

        // Cleanup
        controlador.eliminarCliente(clientePrueba.getRut());
    }

    @Test
    @DisplayName("Búsqueda Avanzada de Clientes")
    void testBuscarClientesAvanzado() {
        // Arrange
        controlador.crearCliente(clientePrueba);

        // Act
        List<Object[]> resultados = controlador.buscarClientesAvanzado(
            clientePrueba.getRut(),
            clientePrueba.getNombres(),
            clientePrueba.getApellidoP(),
            clientePrueba.getApellidoM(),
            clientePrueba.getDireccion(),
            "Cliente",
            "Santiago"
        );

        // Assert
        assertNotNull(resultados, "La búsqueda avanzada no debería retornar null");

        // Cleanup
        controlador.eliminarCliente(clientePrueba.getRut());
    }

    @AfterEach
    void tearDown() {
        // Asegurar limpieza incluso si una prueba falla
        try {
            controlador.eliminarCliente(clientePrueba.getRut());
        } catch (Exception e) {
            // Ignorar errores en la limpieza
        }
    }
}
