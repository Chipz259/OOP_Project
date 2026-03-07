package system;

public class Objective {
    public String name;
    public String displayText;
    public String[][] subItems;

    public Objective(String name, String displayText) {
        this.name = name;
        this.displayText = displayText;
        this.subItems = null;
    }
    public Objective(String name, String displayText, String[][] subItems) {
        this.name = name;
        this.displayText = displayText;
        this.subItems = subItems;
    }
}