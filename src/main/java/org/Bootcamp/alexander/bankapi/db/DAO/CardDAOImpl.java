package org.Bootcamp.alexander.bankapi.db.DAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.Bootcamp.alexander.bankapi.db.H2JDBCUtils;
import org.Bootcamp.alexander.bankapi.model.Card;

import java.sql.*;

/**
 * @CardDAOImpl - класс реализующий интерфейс CardDAO.
 * И содержащий в себе методы для создание карты и получения списка карт
 */

public class CardDAOImpl implements CardDAO {
    private final String createCardQuery = "INSERT INTO CARD (number, month, year, code, account_id) " +
            "VALUES ( ?, ?, ?, ?, ?);";
    private final String selectCardInfoQuery = "SELECT id, number from CARD;";

    @Override
    public void create(Card card) throws SQLException {
        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(createCardQuery)) {

            statement.setString(1, card.getNumber());
            statement.setString(2, card.getMonth());
            statement.setString(3, card.getYear());
            statement.setString(4, card.getCode());
            statement.setInt(5, card.getAccountId());
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            // log and throw custom exception
        }
    }

    // List<Card>
    @Override
    public ArrayNode getCards() throws SQLException {
        ArrayNode arr = null;

        try (Connection connection = H2JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(selectCardInfoQuery);
            ObjectMapper mapper = new ObjectMapper();
            arr = mapper.createArrayNode();
            while (rs.next()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", rs.getInt("id"));
                node.put("number", rs.getString("number"));
                arr.add(node);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return arr;
    }
}
