package ua.Nazar.Rep.repos;

import org.springframework.data.repository.CrudRepository;
import ua.Nazar.Rep.domain.Record;
import ua.Nazar.Rep.domain.User;

public interface RecordRepo extends CrudRepository<Record, Long> {
    Iterable<Record> findAllByDateStartingWith(String filter);
    Iterable<Record> findAllByDateStartingWithAndAuthor(String filter, User author);
}
