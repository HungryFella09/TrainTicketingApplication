package domain;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.List;


@jakarta.persistence.Entity
@Table(name = "bookings")
public class Booking extends Entity<Long>{

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "day_of_trip")
    private Date dayOfTrip;
    private float price;

    @OneToMany(mappedBy="booking")
    private List<Ticket>  tickets;

    public Booking(){
        super();
    }

    public Booking(Long userId, Date dayOfTrip, float price, List<Ticket> tickets) {
        this.userId = userId;
        this.dayOfTrip = dayOfTrip;
        this.price = price;
        this.tickets = tickets;
    }

    public Booking(Long aLong, Long userId, Date dayOfTrip, float price, List<Ticket> tickets) {
        super(aLong);
        this.userId = userId;
        this.dayOfTrip = dayOfTrip;
        this.price = price;
        this.tickets = tickets;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDayOfTrip() {
        return dayOfTrip;
    }

    public void setDayOfTrip(Date dayOfTrip) {
        this.dayOfTrip = dayOfTrip;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "dayOfTrip=" + dayOfTrip +
                ", price=" + price +
                ", tickets=" + tickets +
                '}';
    }
}
