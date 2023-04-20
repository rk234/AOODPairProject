package backend;

public abstract class Objective {
    private String failMessage;
    private boolean failed = false;
    public abstract boolean checkCompleted(Rocket r);
    
    public boolean isFailed() {
        return failed;
    }
    public void setFailed(boolean failed, String failMessage) {
        this.failed = failed;
        this.failMessage = failMessage;
    }
    public String getFailMessage() {
        return failMessage;
    }
}