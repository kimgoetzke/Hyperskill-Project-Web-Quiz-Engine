package engine.business;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Answer {
    private final List<Integer> answer;

    public Answer() {
        this.answer = Collections.emptyList();
    }

    public Answer(List<Integer> answer) {
        this.answer = Objects.requireNonNullElse(answer, Collections.emptyList());
    }

    public void print() {
        System.out.println("Answer: " + Arrays.toString(answer.toArray()));
    }

    public List<Integer> getAnswer() {
        return answer;
    }
}
