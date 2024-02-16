package ma.ac.esi.voyager;
import java.util.Date;
public class DiscountCard {
    private int id;
    private String cardId;
    private String cardType;
    private double discountPercentage;
    private Date validityPeriod;

    // Constructeur
    public DiscountCard() {
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }
    
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }


    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Date getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(Date validityPeriod) {
        this.validityPeriod = validityPeriod;
    }
}
