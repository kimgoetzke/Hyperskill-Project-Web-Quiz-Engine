package engine.business;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "quizzes")
@JsonPropertyOrder({
        "id",
        "title",
        "text",
        "options",
})
public class Quiz {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @NotBlank(message = "Every quiz needs a title.")
    private String title;

    @Column
    @NotBlank(message = "Every quiz needs the text that contains it.")
    private String text;

    @NotNull
    @Size(min = 2, message = "Every quiz must have at least two options to choose from.")
    @ElementCollection
    @CollectionTable(name = "quiz_options", joinColumns = @JoinColumn(name = "quiz_id") )
    @Column(name = "options")
    private List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection
    @CollectionTable(name = "answers", joinColumns = @JoinColumn(name = "quiz_id"))
    @Column(name = "answer")
    private List<Integer> answer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonCreator
    public Quiz(@JsonProperty("title") String title,
                @JsonProperty("text") String text,
                @JsonProperty("options") List<String> options,
                @JsonProperty("answer") List<Integer> answer) {
        this.title = Optional.ofNullable(title).orElse("Untitled quiz");
        this.text = text;
        this.options = options;
        this.answer = Objects.requireNonNullElse(answer, Collections.emptyList());
    }

    public Quiz() { }

    @Override
    public String toString() {
        return "Title: " + this.title
                + ", id: " + this.id
                + ", text: " + this.text
                + ", options: " + this.options
                + ", answer: " + this.answer
                + ", user: " + this.user.getEmail();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
