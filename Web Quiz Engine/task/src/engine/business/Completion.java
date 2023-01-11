package engine.business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "completions")
public class Completion {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long completionId;

    @NotNull
    @Column
    private long quizId;

    @Column
    private LocalDateTime dateTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @JsonCreator
    public Completion(@JsonProperty("id") long quizId,
                      @JsonProperty("completedAt") LocalDateTime dateTime,
                      User user) {
        this.quizId = quizId;
        this.dateTime = dateTime;
        this.user = user;
    }

    public Completion() { }

    @Override
    public String toString() {
        return "Completion - Quiz ID: " + this.quizId
                + ", at: " + this.dateTime
                + ", by: " + this.user.getEmail();
    }

    public long getQuizId() {
        return quizId;
    }

    public void setQuizId(long id) {
        this.quizId = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
