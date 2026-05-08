package repositories;

import domain.Booking;
import domain.Train;

import java.util.List;

public interface BookingRepositoryInterface extends Repository<Long, Booking>{

    List<Booking> bookingsOfTrain(Train train);
}
