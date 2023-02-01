package engine.business;

import engine.business.exceptions.QuizNotFoundException;
import engine.persistence.*;
import engine.presentation.EngineController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final CompletionService completionService;
    private final Logger LOGGER = LoggerFactory.getLogger(EngineController.class.getName());

    @Autowired
    public QuizService(QuizRepository quizRepository, CompletionService completionService) {
        this.quizRepository = quizRepository;
        this.completionService = completionService;
    }

    public Quiz add(Quiz quiz) {
        LOGGER.info("Current count of quizzes in database: "+ count() + ".");
        Quiz result = quizRepository.save(quiz);
        LOGGER.info("New count of quizzes: " + count() + ". Details of added quiz: " + quiz);
        return result;
    }

    public Quiz get(long id) {
        return quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException(id));
    }

    public Page<Quiz> getAll(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return quizRepository.findAll(paging);
    }

    public Response answer(long id, Answer answer, User user) {
        if (answer == null
                || answer.toString().isEmpty()
                || answer.getAnswer() == null) {
            answer = new Answer();
        }

        LOGGER.info("Retrieved answers '" + get(id).getAnswer().toString() + "' from the quiz object"
                + " and received '" + answer.getAnswer().toString() + "' as an answer object (i.e. user input).");

        if (new HashSet<>(answer.getAnswer()).containsAll(get(id).getAnswer())
                && new HashSet<>(get(id).getAnswer()).containsAll(answer.getAnswer())) {
            completionService.add(id, user);
            LOGGER.info("Result: Answer is correct.");
            return new Response(true);
        } else {
            LOGGER.info("Result: Answer is NOT correct.");
            return new Response(false);
        }
    }

    @Transactional
    public boolean delete(long id) {
        if (quizRepository.findById(id).isPresent()) {
            LOGGER.info("Quiz " + id + " is being deleted (count of quizzes: " + count() + ").");
            Quiz quiz = quizRepository.findById(id).get();
            quiz.getUser().getQuizzes().remove(quiz);
            quizRepository.deleteById(id);
            LOGGER.info("Count of remaining quizzes in database: " + count() + ".");
            return true;
        }
        LOGGER.warn("Quiz " + id + " not found. Request declined.");
        throw new QuizNotFoundException(id);
    }

    public long count() {
        return quizRepository.count();
    }
}