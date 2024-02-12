package DataAccess;

import Connection.ConnectionFactory;
import Model.Carts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class CartsDAO extends AbstractDAO<Carts> {

    /**
     * return all the products which are in the order with the specific id
     * @param id
     * @return
     */
    public List<Carts> findAllById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * from Carts WHERE id_order="+id;
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return  createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"CartsDAO:findAllById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * find a specific product in an order
     * @param idOrder
     * @param idProduct
     * @return
     */
    public Carts findByBothIds(int idOrder,int idProduct) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT  * FROM Carts WHERE id_order = "+idOrder+" and id_product= "+idProduct;
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            List<Carts> res =createObjects(resultSet);
            if(res.isEmpty())
                return null;
            return res.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "CartsDAO:findByBothId " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public void deleteByBothIds(int idOrder,int idProduct) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "DELETE FROM Carts WHERE id_order = "+idOrder+" and id_product= "+idProduct;
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "CartsDAO:findByBothId " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

}
