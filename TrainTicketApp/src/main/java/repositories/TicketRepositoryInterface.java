package repositories;

import domain.Ticket;
import domain.Train;

import java.util.Date;
import java.util.List;

public interface TicketRepositoryInterface extends Repository<Long, Ticket>{

    long countByTrainAndDate(Train train, Date date);
}
