package repositories;

import domain.Route;
import domain.Train;

import java.util.List;

public interface TrainRepositoryInterface extends Repository<Long, Train>{

    public List<Train> getAllByRoute(Route route);

}
