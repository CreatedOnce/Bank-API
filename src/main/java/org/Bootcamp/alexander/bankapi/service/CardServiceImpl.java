package org.Bootcamp.alexander.bankapi.service;

import org.Bootcamp.alexander.bankapi.db.DAO.CardDAO;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.Bootcamp.alexander.bankapi.model.Card;

import java.sql.SQLException;

/**
 * @CardServiceImpl - класс реализующий интерфейс CardService.
 * Содержащий в себе методы для вставки карт в базу данных.
 * И для получения списка карт.
 */

public class CardServiceImpl implements CardService {
    private final CardDAO cardDAO;

    public CardServiceImpl(CardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }
    @Override
    public void insertCardInDatabase(Card card) throws SQLException {
        cardDAO.create(card);
    }

    @Override
    public void deleteCardFromDataBase(Card card) throws SQLException{
        cardDAO.deleteCard(card);
    }

    // List<CardInfo>
    @Override
    public ArrayNode getCards() throws SQLException {
        return cardDAO.getCards();
    }

}
