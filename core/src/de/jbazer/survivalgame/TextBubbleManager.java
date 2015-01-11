package de.jbazer.survivalgame;

import java.util.ArrayList;

public class TextBubbleManager {

    /** TAG for logging. */
    private static final String LOG = TextBubbleManager.class.getSimpleName();
    private static TextBubbleManager instance;

    // Text Bubbles
    private static final int NONE = 0;
    private static final int START_1 = 1;
    private static final int ANTIFREEZE_1 = 2;
    private static final int FENCE = 3;
    private static final int EATING = 4;
    private static final int ROCK = 5;
    private static final int ANTIFREEZE_2 = 6;
    private static final int SHOE = 7;
    private static final int IDLE_1 = 8;
    private static final int IDLE_2 = 9;
    private static final int IDLE_3 = 10;
    private static final int IDLE_4 = 11;
    private static final int MOUSE = 12;
    private static final int START_2 = 13;
    private static final int IDLE_5 = 14;

    // Events
    private static final int FIRST_STEP_MADE = 1;

    private boolean isActive = true;
    // Trigger booleans
    private boolean firstStep;

    private int activeBubble = this.START_1;
    private int steps;
    private int lastIdleState = this.IDLE_1;
    private boolean lastAntifreeze;
    private boolean flagShoePlayed;
    private int fenceCounter;
    private int rockCounter;
    private boolean flagRockPlayed;
    private boolean flagFencePlayed;
    protected boolean isReady;
    private boolean flagAntifreeze;

    public TextBubbleManager() {
        if ((int) (Math.random() * 2) == 0) {
            lastAntifreeze = true;
        }
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
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isReady = true;
            }
        }).start();
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
        if (isReady) {
            this.activeBubble = activeBubble;
            isReady = false;
            isActive = true;
        }
    }

    /**
     * @return the isActive
     */
    public final boolean isActive() {
        return isActive;
    }

    /**
     * @param isActive
     *            the isActive to set
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
        steps++;
        if (steps % 100 == 0 && !isActive) {
            // generate new idle message
            do {
                final int ran = (int) (Math.random() * 5);
                switch (ran) {
                    case 0:
                        this.setActiveBubble(this.IDLE_1);
                        break;
                    case 1:
                        this.setActiveBubble(this.IDLE_2);
                        break;
                    case 2:
                        this.setActiveBubble(this.IDLE_3);
                        break;
                    case 3:
                        this.setActiveBubble(this.IDLE_4);
                        break;
                    case 4:
                        this.setActiveBubble(this.IDLE_5);
                        break;
                    default:
                        break;
                }
            } while (activeBubble == lastIdleState);
            this.disableIn(3000);
        }
    }

    public void shoeCollected() {
        if (!isActive && !flagShoePlayed) {
            this.setActiveBubble(this.SHOE);
            this.disableIn(3000);
            flagShoePlayed = true;
        }
    }

    public void walkedAgainstFence() {
        fenceCounter++;
        if (!flagFencePlayed && fenceCounter > 5 && !isActive) {
            this.setActiveBubble(this.FENCE);
            this.disableIn(3000);
            flagFencePlayed = true;
        }
    }

    public void walkedAgainstRock() {
        rockCounter++;
        if (!flagRockPlayed && rockCounter > 5 && !isActive) {
            this.setActiveBubble(this.ROCK);
            this.disableIn(3000);
            flagRockPlayed = true;
        }
    }

    public void seeAntifreeze() {
        if (!isActive && !flagAntifreeze) {
            this.setActiveBubble(this.ANTIFREEZE_2);
            this.disableIn(3000);
            flagAntifreeze = true;
        }
    }

    public void ateAnti() {
        if (!isActive) {
            this.setActiveBubble(this.ANTIFREEZE_1);
            this.disableIn(3000);
        }
    }
}
