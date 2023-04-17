package com.alura.tests;

import com.alura.factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) throws SQLException {
        // System.out.println("Iniciando la conexión");
        Connection con = new ConnectionFactory().recuperaConexion();
        System.out.println("Cerrando la conexión");
        con.close();
    }
}
