package engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebQuizEngine {
    public static void main(String[] args) {
        SpringApplication.run(WebQuizEngine.class, args);
        System.out.println("We're live.");
    }

    // Solutions to Stage 2: https://hyperskill.org/projects/91/stages/505/implement#solutions
    // Solutions to Stage 3: https://hyperskill.org/projects/91/stages/506/implement#solutions
    // Solutions to Stage 4: https://hyperskill.org/projects/91/stages/507/implement#solutions
    // Solutions to Stage 5: https://hyperskill.org/projects/91/stages/508/implement#solutions
}
