package domain;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "train")
public class Train extends Entity<Long>{

    private String name;

    @ManyToOne
    @JoinColumn(name="route_id", nullable=false)
    private Route route;

    @Column(name="number_of_seats")
    private int numberOfSeats;



    public Train() {
        super();
    }

    public Train(String name, Route route, int numberOfSeats) {
        this.name = name;
        this.route = route;
        this.numberOfSeats = numberOfSeats;
    }

    public Train(Long aLong, String name, Route route, int numberOfSeats) {
        super(aLong);
        this.name = name;
        this.route = route;
        this.numberOfSeats = numberOfSeats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
