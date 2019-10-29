package ua.Nazar.Rep.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ua.Nazar.Rep.domain.Record;
import ua.Nazar.Rep.domain.User;

public interface RecordRepo extends CrudRepository<Record, Long> {
    Page<Record> findAll(Pageable pageable);
    Page<Record> findAllByDateStartingWith(String filter, Pageable pageable);
    Page<Record> findAllByAuthor(User author, Pageable pageable);
    Page<Record> findAllByDateStartingWithAndAuthor(String filter, User author, Pageable pageable);
}
