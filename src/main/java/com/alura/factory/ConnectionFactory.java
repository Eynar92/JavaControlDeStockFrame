package com.alura.factory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class ConnectionFactory {

    private DataSource dataSource;

    public ConnectionFactory() {
        var pooledDataSource = new ComboPooledDataSource();
        pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC");
        pooledDataSource.setUser("root");
        pooledDataSource.setPassword("1992");
        pooledDataSource.setMaxPoolSize(10); // Cantidad maxima de conexiones que se puede tener

        this.dataSource = pooledDataSource;
    }

    public Connection recuperaConexion() {
        try {
            return this.dataSource.getConnection();

            // Antes de implementar pooledDataSource
            /* return DriverManager.getConnection(
            "jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
            "root",
            "1992"); */
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
