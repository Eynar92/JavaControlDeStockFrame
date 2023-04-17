package com.alura.dao;

import com.alura.factory.ConnectionFactory;
import com.alura.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoDAO {

    final private Connection con;

    public ProductoDAO(Connection con) {
        this.con = con;
    }

    public void guardar(Producto producto) {
//        ConnectionFactory factory = new ConnectionFactory();
//        final Connection con = factory.recuperaConexion();

        try /*(con)*/ {
            //con.setAutoCommit(false); // Hace que registremos COMPLETAMENTE la transacción o no HAGAMOS nada --- con.setAuto... va con el ROLLBACK del catch SQLException

            final PreparedStatement statement = con.prepareStatement("INSERT INTO producto (nombre,descripcion, cantidad, categoria_id)"
                    + " VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            try (statement) {
                //do {
                //int cantidadParaGuardar = Math.min(cantidad, maximoCantidad);

                ejecutaRegistro(producto, statement);

                //cantidad -= maximoCantidad;
                //} while (cantidad > 0);
                //con.commit(); // Hace que se registre el producto luego de que todo el proceso dentro del doWhile se haya realizado
                //System.out.println("COMMIT");
            } //catch (SQLException ex) {
            //throw new RuntimeException(ex);
            //ex.printStackTrace();
            //System.out.println("ROLLBACK de la transacción");
            //con.rollback();
            //}

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void ejecutaRegistro(Producto producto, PreparedStatement statement) {
        try /*(con)*/ {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setInt(3, producto.getCantidad());
            statement.setInt(4, producto.getCategoriaId());

            statement.execute();

            //Version 7 de Java
            /*try (ResultSet resultSet = statement.getGeneratedKeys();) {
            while (resultSet.next()) {
                System.out.println(
                        String.format(
                                "Fue insertado el producto de ID %d",
                                resultSet.getInt(1)));

            }
        }*/
            //Version 9 de Java
            final ResultSet resultSet = statement.getGeneratedKeys();
            try (resultSet) {
                while (resultSet.next()) {
                    producto.setId(resultSet.getInt(1));
                    System.out.println(String.format("Fue insertado el producto %s", producto));

                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public List<Producto> listar() {

        List<Producto> resultado = new ArrayList<>();

//        ConnectionFactory factory = new ConnectionFactory();
//        final Connection con = factory.recuperaConexion();
        try /*(con)*/ { //No debemos usar la variable de conexión (con) ya que estaríamos cerrando la misma en la clase producto controller
            final PreparedStatement statement = con.prepareStatement("SELECT id, nombre, descripcion, cantidad FROM producto");

            try (statement) {
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();

                try (resultSet) {
                    while (resultSet.next()) {
                        Producto fila = new Producto(resultSet.getInt("id"),
                                resultSet.getString("nombre"),
                                resultSet.getString("descripcion"),
                                resultSet.getInt("cantidad"));
//                        fila.put("id", String.valueOf());
//                        fila.put("nombre",);
//                        fila.put("descripcion",);
//                        fila.put("cantidad", String.valueOf());

                        resultado.add(fila);
                    }
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return resultado;
    }

    public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
        //ConnectionFactory factory = new ConnectionFactory();
        //final Connection con = factory.recuperaConexion();

        try {

            final PreparedStatement statement = con.prepareStatement("UPDATE producto SET "
                    + "nombre = ?, "
                    + "descripcion = ?, "
                    + "cantidad = ? "
                    + "WHERE id = ?");

            try (statement) {
                statement.setString(1, nombre);
                statement.setString(2, descripcion);
                statement.setInt(3, cantidad);
                statement.setInt(4, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int eliminar(Integer id) {
        try {
            final PreparedStatement statement = con.prepareStatement("DELETE FROM producto WHERE id = ?");
            try (statement) {
                statement.setInt(1, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Producto> listar(Integer categoriaId) {
        List<Producto> resultado = new ArrayList<>();

        try {
            var querySelect = "SELECT id, nombre, descripcion, cantidad "
                    + "FROM producto "
                    + "WHERE categoria_id = ?";
            System.out.println(querySelect);
            final PreparedStatement statement = con.prepareStatement(querySelect);

            try (statement) {
                statement.setInt(1, categoriaId);
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();

                try (resultSet) {
                    while (resultSet.next()) {
                        Producto fila = new Producto(resultSet.getInt("id"),
                                resultSet.getString("nombre"),
                                resultSet.getString("descripcion"),
                                resultSet.getInt("cantidad"));

                        resultado.add(fila);
                    }
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return resultado;
    }
}
