package domain;


import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "ticket")
public class Ticket extends Entity<Long>{

    @ManyToOne
    @JoinColumn(name="train_id", nullable=false)
    private Train train;

    @ManyToOne
    @JoinColumn(name="booking_id", nullable=false)
    private Booking booking;

    @Column(name="no_tickets")
    private int noTickets;

    public Ticket() {}

    public Ticket(Long aLong, Train train, Booking booking) {
        super(aLong);
        this.train = train;
        this.booking = booking;
    }

    public Ticket( Train train, Booking booking, int noTickets) {
        this.train = train;
        this.booking = booking;
        this.noTickets = noTickets;
    }

    public Train getTrain() {
        return train;
    }
    public void setTrain(Train train) {
        this.train = train;
    }

    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public int getNoTickets() {
        return noTickets;
    }

    public void setNoTickets(int noTickets) {
        this.noTickets = noTickets;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "train=" + train.getName() +
                '}';
    }
}
