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

    public Page<Record> findAll(String filter, Pageable pageable){
        Page<Record> page = recordRepo.findAll(pageable);

        if (filter != null && !filter.isEmpty()) {
            page = recordRepo.findAllByDateStartingWith(filter, pageable);
        }
        return page;
    }

    public Page<Record> findAll(Pageable pageable) {
        return recordRepo.findAll(pageable);
    }

    public Page<Record> findAllByAuthor(User author, String filter, Pageable pageable) {
        Page<Record> page = recordRepo.findAllByAuthor(author, pageable);

        if (filter != null && !filter.isEmpty()) {
            page = recordRepo.findAllByDateStartingWithAndAuthor(filter, author, pageable);
        }
        return page;
    }

    public Page<Record> findAllByAuthor(User author, Pageable pageable) {
        return recordRepo.findAllByAuthor(author, pageable);
    }

    public void save(Record record){
        recordRepo.save(record);
    }

    public void deleteById(Long recordId){
        recordRepo.deleteById(recordId);
    }
}
