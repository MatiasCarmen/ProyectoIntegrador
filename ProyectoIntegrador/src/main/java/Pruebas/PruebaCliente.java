/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import controladores.ClienteControlador;
import controladores.ComunasControlador;
import entidades.Cliente;

public class PruebaCliente {
    public static void main(String[] args) {
        ClienteControlador cc = new ClienteControlador();
        ComunasControlador ccc = new ComunasControlador();

        // 1) Asegurarnos de que la comuna existe (opcional)
        // ccc.insertarComuna("C003", "ComunaPrueba"); 

        // 2) Crear cliente con un IDCOMUNA que ya existe
        Cliente nuevo = new Cliente(
            "R9999999-1",
            "test@correo.cl",
            "Test",
            "Prueba",
            "Mock",
            912345678L,
            (byte)30,
            "Calle Falsa 123",
            "C001"        // <-- Aquí pones C001 o C002
        );
        System.out.println("Crear: " + cc.crearCliente(nuevo));

        // 3) Listar
        cc.listarClientes().forEach(c ->
            System.out.println(c.getRut() + " - " + c.getNombres())
        );

        // 4) Buscar por RUT
        Cliente buscado = cc.obtenerClientePorRut("R9999999-1");
        System.out.println("Buscado: " +
            (buscado != null ? buscado.getNombres() : "no existe"));

        // 5) Actualizar
        if (buscado != null) {
            buscado.setNombres("TestModificado");
            System.out.println("Actualizar: " + cc.actualizarCliente(buscado));
        }

        // 6) Eliminar
        System.out.println("Eliminar: " + cc.eliminarCliente("R9999999-1"));
    }
}
