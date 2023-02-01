package engine.presentation;

import engine.business.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import javax.validation.Valid;


@RestController()
@Validated
public class EngineController {
    private final QuizService quizService;
    private final UserService userService;
    private final CompletionService completionService;
    private final Logger LOGGER = LoggerFactory.getLogger(EngineController.class.getName());

    @Autowired
    public EngineController(UserService userService, QuizService questionService, CompletionService completionService) {
        this.userService = userService;
        this.quizService = questionService;
        this.completionService = completionService;
    }

    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable long id) {
        LOGGER.info("Authenticated request to get quiz " + id + " received.");
        LOGGER.info(quizService.get(id).toString());
        return quizService.get(id);
    }

    @GetMapping("/api/quizzes")
    public Page<Quiz> getAllQuizzes(@RequestParam(defaultValue = "0", required = false) int page,
                                    @RequestParam(defaultValue = "10", required = false) int size) {
        LOGGER.info("Authenticated request to get all quizzes received.");
        return quizService.getAll(page, size);
    }

    @PostMapping("/api/quizzes")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz,
                           Authentication authentication) {
        LOGGER.info("Authenticated quiz creation request received.");
        quiz.setUser(userService.get(authentication.getName()));
        return quizService.add(quiz);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Response answerQuiz(@PathVariable long id,
                               @RequestBody(required = false) Answer answer,
                               Authentication authentication) {
        LOGGER.info("Authenticated quiz answer received.");
        User user = userService.get(authentication.getName());
        return quizService.answer(id, answer, user);
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<Quiz> deleteQuiz(@PathVariable long id,
                                           Authentication authentication) {
        LOGGER.info("Authenticated quiz deletion request for quiz " + id + " received.");
        Quiz quiz = quizService.get(id);
        System.out.println("Deletion requested by '" + authentication.getName()
                + "' and quiz is owned by '" + quiz.getUser().getEmail() + "'.");
        if (quizService.get(id).getUser().getEmail().equals(authentication.getName())) {
            LOGGER.info("User is the owner of the question. Request will be actioned if question exists.");
            return quizService.delete(id) ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            LOGGER.warn("User is not the owner of the question. Deletion request declined.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/api/register")
    public HttpStatus registerUser(@Valid @RequestBody User user) {
        LOGGER.info("New user registration request received.");
        return userService.add(user) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @GetMapping("/api/quizzes/completed")
    public Page<Completion> getCompletions(@RequestParam(defaultValue = "0", required = false) int page,
                                           @RequestParam(defaultValue = "10", required = false) int size,
                                           Authentication authentication) {
        LOGGER.info("Authenticated request to get all completions received.");
        User user = userService.get(authentication.getName());
        return completionService.getAll(page, size, user);
    }
}
