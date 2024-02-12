package Connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/Tema3";
    private static final String USER = "root";
    private static final String PASS = "Root2023!";

    private static ConnectionFactory singleInstance= new ConnectionFactory();

    private ConnectionFactory()  {
        try {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private Connection createConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL,USER,PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error while trying to connect to database");
            e.printStackTrace();
        }
        return connection;
    };
    public static Connection getConnection(){
        return singleInstance.createConnection();
    };

    public static void close(Connection connection){
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING,"Error while trying to close a connection");
            }
        }
    };
    public static void close(Statement statement){
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING,"Error while trying to close a statement");
            }
        }
    };
    public static void close(ResultSet resultSet){
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING,"Error while trying to close a resultSet");
            }
        }
    };

}
