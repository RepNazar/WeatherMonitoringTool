package ua.Nazar.Rep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.Nazar.Rep.domain.Record;
import ua.Nazar.Rep.domain.User;
import ua.Nazar.Rep.repos.RecordRepo;

@Service
public class RecordService {

    private RecordRepo recordRepo;

    @Autowired
    public void setRecordRepo(RecordRepo recordRepo) {
        this.recordRepo = recordRepo;
    }

    /**
     * @param filter
     * @param pageable
     * @return Page of all records by filter
     */
    public Page<Record> findAll(String filter, Pageable pageable){
        Page<Record> page = recordRepo.findAll(pageable);

        if (filter != null && !filter.isEmpty()) {
            page = recordRepo.findAllByDateStartingWith(filter, pageable);
        }
        return page;
    }

    /**
     * @param pageable
     * @return Page of all records
     */
    public Page<Record> findAll(Pageable pageable) {
        return recordRepo.findAll(pageable);
    }

    /**
     * @param author
     * @param filter
     * @param pageable
     * @return Page of records by author and filter
     */
    public Page<Record> findAllByAuthor(User author, String filter, Pageable pageable) {
        Page<Record> page = recordRepo.findAllByAuthor(author, pageable);

        if (filter != null && !filter.isEmpty()) {
            page = recordRepo.findAllByDateStartingWithAndAuthor(filter, author, pageable);
        }
        return page;
    }

    /**
     * @param author
     * @param pageable
     * @return Page of records by author
     */
    public Page<Record> findAllByAuthor(User author, Pageable pageable) {
        return recordRepo.findAllByAuthor(author, pageable);
    }

    /**
     * @param record
     */
    public void save(Record record){
        recordRepo.save(record);
    }

    /**
     * @param recordId
     */
    public void deleteById(Long recordId){
        recordRepo.deleteById(recordId);
    }
}
