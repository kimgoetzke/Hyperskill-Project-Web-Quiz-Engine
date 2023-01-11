package engine.business;

public class Response {
    private final boolean success;
    private final String feedback;

    public Response(boolean success) {
        this.success = success;
        this.feedback = success ? "Congratulations, you're right!" : "Wrong answer! Please, try again.";
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}