package pepse.util;

/**
 * The Observer interface defines the contract for objects that observe and react to changes or events.
 * Objects implementing this interface must provide an implementation for the rotateAndChange method.
 */
public interface Observer
{
    /**
     * Defines the method to be invoked when an observed object notifies about a change or event.
     * Implementing classes should provide their specific behavior to respond to changes.
     */
    public void rotateAndChange();
}
