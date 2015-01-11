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
    private static final int FOOD_1 = 4;
    private static final int ROCK = 5;
    private static final int ANTIFREEZE_2 = 6;
    private static final int SHOE = 7;
    private static final int IDLE_1 = 8;
    private static final int IDLE_2 = 9;
    private static final int IDLE_3 = 10;
    private static final int IDLE_4 = 11;
    private static final int MOUSE_1 = 12;
    private static final int START_2 = 13;
    private static final int IDLE_5 = 14;
    private static final int WATER = 15;
    private static final int FOOD_2 = 16;
    private static final int MOUSE_2 = 17;
    private static final int IDLE_6 = 18;
    private static final int IDLE_7 = 19;
    private static final int IDLE_8 = 20;
    private static final int FOOD_3 = 21;

    // Events
    private static final int FIRST_STEP_MADE = 1;

    private boolean isActive = true;
    // Trigger booleans
    private boolean firstStep;

    private int activeBubble = START_2;
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
    private boolean flagWater;
    private int food;

    public TextBubbleManager() {
        if ((int) (Math.random() * 2) == 0) {
            activeBubble = START_1;
        }

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
        SoundManager.getInstance().playMiau();
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
                final int ran = (int) (Math.random() * 8);
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
                    case 5:
                        this.setActiveBubble(this.IDLE_6);
                        break;
                    case 6:
                        this.setActiveBubble(this.IDLE_7);
                        break;
                    case 7:
                        this.setActiveBubble(this.IDLE_8);
                        break;
                    default:
                        break;
                }
            } while (activeBubble == lastIdleState);
            this.disableIn(4000);
        }
    }

    public void shoeCollected() {
        if (!isActive && !flagShoePlayed) {
            this.setActiveBubble(this.SHOE);
            this.disableIn(4000);
            flagShoePlayed = true;
        }
    }

    public void walkedAgainstFence() {
        fenceCounter++;
        if (!flagFencePlayed && fenceCounter > 5 && !isActive) {
            this.setActiveBubble(this.FENCE);
            this.disableIn(4000);
            flagFencePlayed = true;
        }
    }

    public void walkedAgainstRock() {
        rockCounter++;
        if (!flagRockPlayed && rockCounter > 5 && !isActive) {
            this.setActiveBubble(this.ROCK);
            this.disableIn(4000);
            flagRockPlayed = true;
        }
    }

    public void seeAntifreeze() {
        if (!isActive && !flagAntifreeze) {
            this.setActiveBubble(this.ANTIFREEZE_2);
            this.disableIn(4000);
            flagAntifreeze = true;
        }
    }

    public void ateAnti() {
        if (!isActive) {
            this.setActiveBubble(this.ANTIFREEZE_1);
            SoundManager.getInstance().miauNegative();
            this.disableIn(4000);
        }
    }

    public void water() {
        if (!isActive && !flagWater) {
            this.setActiveBubble(this.WATER);
            SoundManager.getInstance().miauNegative();
            this.disableIn(4000);
            flagWater = true;
        } else {
            flagWater = false;
        }
    }

    public void food() {
        food++;
        if (!isActive && food % 3 == 0) {
            switch ((int) (Math.random() * 3)) {
                case 0:
                    this.setActiveBubble(this.FOOD_1);
                    break;
                case 1:
                    this.setActiveBubble(this.FOOD_2);
                    break;
                case 2:
                    this.setActiveBubble(this.FOOD_3);
                    break;
                default:
                    break;
            }
            this.disableIn(4000);
        }
    }

    public void mouse() {
        if (!isActive && (int) (Math.random() * 3) == 0) {
            if ((int) (Math.random() * 2) == 0) {
                this.setActiveBubble(this.MOUSE_1);
            } else {
                this.setActiveBubble(this.MOUSE_2);
            }
            this.disableIn(4000);
        }
    }
}
