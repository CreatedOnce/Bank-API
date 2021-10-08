package org.Bootcamp.alexander.bankapi.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.Bootcamp.alexander.bankapi.model.Card;

import java.sql.SQLException;

public interface CardService {
    void insertCardInDatabase(Card card) throws SQLException;
    void deleteCardFromDataBase(Card card) throws SQLException;
    ArrayNode getCards() throws SQLException;
}
