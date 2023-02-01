package engine.business;

import engine.persistence.CompletionRepository;
import engine.presentation.EngineController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger LOGGER = LoggerFactory.getLogger(EngineController.class.getName());

    @Autowired
    public CompletionService(CompletionRepository completionRepository) {
        this.completionRepository = completionRepository;
    }

    public void add(long quizId, User user) {
        Completion completion = new Completion(quizId, LocalDateTime.now(), user);
        LOGGER.info("New completion added: " + completion + ".");
        completionRepository.save(completion);
    }

    public Page<Completion> getAll(int page, int size, User user) {
        Pageable paging = PageRequest.of(page, size, Sort.by("date_time").descending());
        Page<Completion> result = completionRepository.findAllByUser(user, paging);
        if(result.hasContent()) {
            LOGGER.info("Returned completions: " + Arrays.toString(result.getContent().toArray()));
        }
        return result;
    }
}
