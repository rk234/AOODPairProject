package backend;

public abstract class Objective {
    private boolean failed = false;
    public abstract boolean checkCompleted(Rocket r);
    
    public boolean isFailed() {
        return failed;
    }
    public void setFailed(boolean failed) {
        this.failed = failed;
    }
}