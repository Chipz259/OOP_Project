package system;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectiveManager {
    private List<Objective> objectives;
    private int currentIndex = 0;
    private Image emptyBoxImg;
    private Image tickedBoxImg;
    public ObjectiveManager(Image emptyBoxImg, Image tickedBoxImg) {
        this.objectives = new ArrayList<Objective>();
        this.emptyBoxImg = emptyBoxImg;
        this.tickedBoxImg = tickedBoxImg;
    }
    public void addObjective(Objective objective) {
        objectives.add(objective);
    }
    public void advanceObjective() {
        if (currentIndex < objectives.size() - 1 ) {
            currentIndex++;
        }
    }

}
