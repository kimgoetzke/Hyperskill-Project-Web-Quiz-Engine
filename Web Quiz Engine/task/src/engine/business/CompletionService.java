package engine.business;

import engine.persistence.CompletionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class CompletionService {
    private final CompletionRepository completionRepository;

    @Autowired
    public CompletionService(CompletionRepository completionRepository) {
        this.completionRepository = completionRepository;
    }

    public void add(long quizId, User user) {
        Completion completion = new Completion(quizId, LocalDateTime.now(), user);
        System.out.println("New completion added: " + completion.toString() + ".");
        completionRepository.save(completion);
    }

    public Page<Completion> getAll(int page, int size, User user) {
        Pageable paging = PageRequest.of(page, size, Sort.by("date_time").descending());
        Page<Completion> result = completionRepository.findAllByUser(user, paging);
        if(result.hasContent()) {
            System.out.println("Returned completions: " + Arrays.toString(result.getContent().toArray()));
        }
        return result;
    }
}
