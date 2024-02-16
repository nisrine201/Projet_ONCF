package ma.ac.esi.voyager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscountCardDAO {
    private Connection connection;

    public DiscountCardDAO(Connection connection) {
        this.connection = connection;
    }

    public List<DiscountCard> getAllDiscountCards() throws SQLException {
        List<DiscountCard> discountCards = new ArrayList<>();
        String sql = "SELECT * FROM DiscountCards";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                DiscountCard discountCard = new DiscountCard();
                discountCard.setCardId(resultSet.getString("cardId"));
                discountCard.setCardType(resultSet.getString("cardType"));
                discountCard.setDiscountPercentage(resultSet.getDouble("discountPercentage"));
                java.util.Date validityPeriod = resultSet.getDate("validityPeriod");
                discountCard.setValidityPeriod(validityPeriod);

                discountCards.add(discountCard);
            }
        }
        return discountCards;
    }
}
