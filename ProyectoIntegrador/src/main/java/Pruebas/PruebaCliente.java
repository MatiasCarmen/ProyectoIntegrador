/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pruebas;

import controladores.ClienteControlador;
import entidades.Cliente;

public class PruebaCliente {
    public static void main(String[] args) {
        ClienteControlador controlador = new ClienteControlador();

        // Crear un nuevo cliente usando setters
        Cliente c = new Cliente();
        c.setRut("12345678-9");
        c.setCorreo("cliente@test.com");
        c.setNombres("Juan Pedro");
        c.setApellidoP("Pérez");
        c.setApellidoM("González");
        c.setTelefono(912345678L);
        c.setEdad((byte)25);
        c.setDireccion("Av. Principal 123");
        c.setIdComuna("1");

        // Probar agregar
        if (controlador.crearCliente(c)) {
            System.out.println("Cliente agregado exitosamente");
            System.out.println("RUT: " + c.getRut() + ", Nombre: " + c.getNombres());
        } else {
            System.out.println("Error al agregar cliente");
        }

        // Probar búsqueda
        Cliente buscado = controlador.obtenerClientePorRut("12345678-9");
        if (buscado != null) {
            System.out.println("Cliente encontrado: " + buscado.getNombres());
        } else {
            System.out.println("Cliente no encontrado");
        }

        // Probar modificación
        if (buscado != null) {
            buscado.setNombres("Juan Pablo");
            if (controlador.actualizarCliente(buscado)) {
                System.out.println("Cliente modificado exitosamente");
            } else {
                System.out.println("Error al modificar cliente");
            }
        }

        // Probar eliminación
        if (controlador.eliminarCliente("12345678-9")) {
            System.out.println("Cliente eliminado exitosamente");
        } else {
            System.out.println("Error al eliminar cliente");
        }
    }
}
