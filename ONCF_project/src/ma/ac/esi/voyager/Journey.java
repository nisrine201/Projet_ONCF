package ma.ac.esi.voyager;

import java.sql.Date;

public class Journey {
    private int id;
    private String origin;
    private String destination;
    private Date departureDate;
    private String departureTime;
    private String arrivalTime;
	private int seatClass;
    private double fare;
    private int seatsLeft;
    
    // Constructeurs
    public Journey(int id, String origin, String destination, Date departureDate, String departureTime,
			String arrivalTime, int seatClass, double fare, int seatsLeft) {
		super();
		this.id = id;
		this.origin = origin;
		this.destination = destination;
		this.departureDate = departureDate;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.seatClass = seatClass;
		this.fare = fare;
		this.seatsLeft = seatsLeft;
	}


    public Journey() {
    }


    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

	public int getSeatsLeft() {
		return seatsLeft;
	}

	public void setSeatsLeft(int seatsLeft) {
		this.seatsLeft = seatsLeft;
	}

	public int getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(int seatClass) {
		this.seatClass = seatClass;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	
    // Méthode toString pour l'affichage
    @Override
    public String toString() {
        return "Journey{" +
                "id=" + id +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", fare=" + fare +
                '}';
    }
	
}