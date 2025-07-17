package dao;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import entidades.Cliente;
import controladores.ComunasControlador;
import java.util.List;
import entidades.Comuna;

@DisplayName("Pruebas para ClienteDAO")
public class ClienteDAOTest {

    private ClienteDAO clienteDAO;
    private Cliente clientePrueba;
    private ComunasControlador comunasControlador;

    @BeforeEach
    void setUp() {
        clienteDAO = new ClienteDAO();
        comunasControlador = new ComunasControlador();
        clientePrueba = new Cliente();
        clientePrueba.setRut("12345678-9");
        clientePrueba.setNombres("Juan Test");
        clientePrueba.setApellidoP("Apellido");
        clientePrueba.setApellidoM("Prueba");
        clientePrueba.setCorreo("juan.test@claro.cl");
        clientePrueba.setTelefono(987654321L);
        clientePrueba.setEdad((byte) 25);
        clientePrueba.setDireccion("Calle Test 123");

        // Obtener una comuna válida de la base de datos
        List<Comuna> comunas = comunasControlador.listarComunas();
        if (!comunas.isEmpty()) {
            clientePrueba.setIdComuna(comunas.get(0).getIdComuna());
        } else {
            throw new RuntimeException("No hay comunas disponibles en la base de datos");
        }
    }

    @Test
    @DisplayName("Crear Cliente - Caso Exitoso")
    void testCrearCliente() {
        // Arrange ya está hecho en setUp()

        // Act
        boolean resultado = clienteDAO.crearCliente(clientePrueba);

        // Assert
        assertTrue(resultado, "La creación del cliente debería ser exitosa");

        // Cleanup
        clienteDAO.eliminarCliente(clientePrueba.getRut());
    }

    @Test
    @DisplayName("Buscar Cliente por RUT - Caso Exitoso")
    void testBuscarClientePorRut() {
        // Arrange
        clienteDAO.crearCliente(clientePrueba);

        // Act
        Cliente clienteEncontrado = clienteDAO.obtenerClientePorRut(clientePrueba.getRut());

        // Assert
        assertNotNull(clienteEncontrado, "El cliente debería ser encontrado");
        assertEquals(clientePrueba.getRut(), clienteEncontrado.getRut(), "Los RUTs deberían coincidir");

        // Cleanup
        clienteDAO.eliminarCliente(clientePrueba.getRut());
    }

    @Test
    @DisplayName("Actualizar Cliente - Caso Exitoso")
    void testActualizarCliente() {
        // Arrange
        clienteDAO.crearCliente(clientePrueba);
        String nuevoCorreo = "nuevo.correo@claro.cl";
        clientePrueba.setCorreo(nuevoCorreo);

        // Act
        boolean resultado = clienteDAO.actualizarCliente(clientePrueba);
        Cliente clienteActualizado = clienteDAO.obtenerClientePorRut(clientePrueba.getRut());

        // Assert
        assertTrue(resultado, "La actualización debería ser exitosa");
        assertEquals(nuevoCorreo, clienteActualizado.getCorreo(), "El correo debería estar actualizado");

        // Cleanup
        clienteDAO.eliminarCliente(clientePrueba.getRut());
    }

    @Test
    @DisplayName("Eliminar Cliente - Caso Exitoso")
    void testEliminarCliente() {
        // Arrange
        clienteDAO.crearCliente(clientePrueba);

        // Act
        boolean resultado = clienteDAO.eliminarCliente(clientePrueba.getRut());
        Cliente clienteEliminado = clienteDAO.obtenerClientePorRut(clientePrueba.getRut());

        // Assert
        assertTrue(resultado, "La eliminación debería ser exitosa");
        assertNull(clienteEliminado, "El cliente no debería existir después de eliminarlo");
    }

    @Test
    @DisplayName("Listar Clientes - No Vacío")
    void testListarClientes() {
        // Arrange
        clienteDAO.crearCliente(clientePrueba);

        // Act
        List<Cliente> clientes = clienteDAO.listarClientes();

        // Assert
        assertNotNull(clientes, "La lista de clientes no debería ser null");
        assertFalse(clientes.isEmpty(), "La lista de clientes no debería estar vacía");

        // Cleanup
        clienteDAO.eliminarCliente(clientePrueba.getRut());
    }

    @Test
    @DisplayName("Buscar Cliente - Criterio Válido")
    void testBuscarPorCriterio() {
        // Arrange
        clienteDAO.crearCliente(clientePrueba);
        String criterio = clientePrueba.getNombres();

        // Act
        List<Cliente> resultados = clienteDAO.buscarPorCriterio(criterio);

        // Assert
        assertFalse(resultados.isEmpty(), "Deberían encontrarse resultados");
        assertTrue(resultados.stream()
                .anyMatch(c -> c.getRut().equals(clientePrueba.getRut())),
                "El cliente de prueba debería estar en los resultados");

        // Cleanup
        clienteDAO.eliminarCliente(clientePrueba.getRut());
    }
}
