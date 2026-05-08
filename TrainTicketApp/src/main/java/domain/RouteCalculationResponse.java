package domain;

import java.util.List;

public class RouteCalculationResponse {

    private int departureTime;
    private int arrivalTime;
    private List<Train> trainsToTake;

    public RouteCalculationResponse(int departureTime, int arrivalTime, List<Train> trainsToTake) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.trainsToTake = trainsToTake;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public List<Train> getTrainsToTake() {
        return trainsToTake;
    }

    public void setTrainsToTake(List<Train> trainsToTake) {
        this.trainsToTake = trainsToTake;
    }
}
