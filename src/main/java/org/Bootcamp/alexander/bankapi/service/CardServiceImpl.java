package org.Bootcamp.alexander.bankapi.service;

import org.Bootcamp.alexander.bankapi.db.DAO.CardDAO;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.Bootcamp.alexander.bankapi.model.Card;

import java.sql.SQLException;

public class CardServiceImpl implements CardService {
    private final CardDAO cardDAO;

    public CardServiceImpl(CardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }
    @Override
    public void insertCardInDatabase(Card card) throws SQLException {
        cardDAO.create(card);
    }

    // List<CardInfo>
    @Override
    public ArrayNode getCards() throws SQLException {
        return cardDAO.getCards();
    }

}
