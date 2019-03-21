package model.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
    
    private final String bancoDeDados = "TesteGuizion";
    private final String usuario = "postgres";
    private final String senha = "rodrigo1";
    
    public Connection getConnection(){
        Connection connect = null;
        try {
            connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + bancoDeDados, usuario, senha);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connect;
    }
}
