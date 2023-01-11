package engine.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class QuizNotFoundException extends RuntimeException
{
    public QuizNotFoundException(long id)
    {
        super("Could not find quiz with ID: " + id + ".");
    }
}