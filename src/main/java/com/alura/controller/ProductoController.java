package com.alura.controller;

import com.alura.factory.ConnectionFactory;
import com.alura.modelo.Producto;
import com.alura.dao.ProductoDAO;
import com.alura.modelo.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

    private ProductoDAO productoDAO;

    public ProductoController() {
        this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
    }

    public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
        return productoDAO.modificar(nombre, descripcion, cantidad, id);

    }

    public int eliminar(Integer id) {
        //ConnectionFactory factory = new ConnectionFactory();
        //final Connection con = factory.recuperaConexion();
        return productoDAO.eliminar(id);
    }

    public List<Producto> listar() {
        //ProductoDAO productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion()); No se necesita esta conexión debido a que ya la generamos globalmente en la clase
        return productoDAO.listar();
    }

    public List<Producto> listar(Categoria categoria) {
        return productoDAO.listar(categoria.getId());
    }

    public void guardar(Producto producto, Integer categoriaId) {
        producto.setCategoriaId(categoriaId);
        //String nombre = producto.getNombre();
        //String descripcion = producto.getDescripcion();
        //Integer cantidad = producto.getCantidad();
        //Integer maximoCantidad = 50;

        //ProductoDAO productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
        productoDAO.guardar(producto);

    }

//    private void ejecutaRegistro(Producto producto, PreparedStatement statement) throws SQLException {
//        statement.setString(1, producto.getNombre());
//        statement.setString(2, producto.getDescripcion());
//        statement.setInt(3, producto.getCantidad());
//
//        statement.execute();
//
//        //Version 7 de Java
//        /*try (ResultSet resultSet = statement.getGeneratedKeys();) {
//            while (resultSet.next()) {
//                System.out.println(
//                        String.format(
//                                "Fue insertado el producto de ID %d",
//                                resultSet.getInt(1)));
//
//            }
//        }*/
//        //Version 9 de Java
//        final ResultSet resultSet = statement.getGeneratedKeys();
//        try (resultSet) {
//            while (resultSet.next()) {
//                producto.setId(resultSet.getInt(1));
//                System.out.println(String.format("Fue insertado el producto %s", producto));
//
//            }
//        }
//
//    }
}
