package DataAccess;

import Model.Bills;
import Connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class BillDAO extends AbstractDAO<Bills> {

    public List<Bills> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * from Bills";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            List<Bills> billsList = new ArrayList<Bills>();
            while (resultSet.next())
            {
                billsList.add(new Bills(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getFloat(4),resultSet.getString(5)));
            }
            return billsList;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillsDAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}
