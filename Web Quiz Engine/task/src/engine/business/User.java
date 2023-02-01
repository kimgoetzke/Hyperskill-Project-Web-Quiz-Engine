package engine.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @NotNull
    @Email(regexp = ".+@.+\\..+", message = "A valid email address must be entered.")
    private String email;

    @NotBlank
    @Size(min = 5, message = "Password must be at least 5 characters long.")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    private Set<Quiz> quizzes;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.quizzes = Collections.emptySet();
    }

    public User() {
        this.quizzes = Collections.emptySet();
    }

    @Override
    public String toString() {
        return "User: " + this.email
                + ", password: " + this.password
                + ", linked quizzes: " + Arrays.toString(this.quizzes.toArray());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Set<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
}