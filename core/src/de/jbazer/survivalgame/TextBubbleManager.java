package de.jbazer.survivalgame;

public class TextBubbleManager {

    /** TAG for logging. */
    private static final String LOG = TextBubbleManager.class.getSimpleName();
    private static TextBubbleManager instance;

    // Text Bubbles
    private static final int NONE = 0;
    private static final int SIMON_LOCKED_ME_OUT_AGAIN = 1;

    // Events
    private static final int FIRST_STEP_MADE = 1;
    
    private boolean isActive = true;
    //Trigger booleans
    private boolean firstStep;

    private int activeBubble = this.SIMON_LOCKED_ME_OUT_AGAIN;
    
    public TextBubbleManager() {
    }

    public void newEvent(int event) {
        switch (event) {
            case FIRST_STEP_MADE:
                disableIn(1000);
                break;
            default:
                break;
        }
    }

    private void disableIn(final int time) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                disableAll();
            }
        }).start();
    }

    private void disableAll() {
        activeBubble = this.NONE;
        isActive = false;
    }

    /**
     * @return the activeBubble
     */
    public final int getActiveBubble() {
        return activeBubble;
    }

    /**
     * @param activeBubble
     *            the activeBubble to set
     */
    public final void setActiveBubble(int activeBubble) {
        this.activeBubble = activeBubble;
    }
    
    /**
     * @return the isActive
     */
    public final boolean isActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public final void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public static TextBubbleManager getInstance() {
        if (instance == null) {
            instance = new TextBubbleManager();
        }
        return instance;
    }

    public void step() {
        if (!firstStep) {
            firstStep = true;
            newEvent(FIRST_STEP_MADE);
        }
    }
}
