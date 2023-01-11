package engine.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(String email)
    {
        super("Could not find user with email: " + email + ".");
    }
}