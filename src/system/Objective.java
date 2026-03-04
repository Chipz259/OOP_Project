package system;

import entities.Inventory;

import java.awt.*;
import java.util.LinkedHashMap;

public class Objective {
    public String name;
    public String displayText;
    public LinkedHashMap<String, String> subItems;
    public Objective(String name, String displayText) {
        this.name = name;
        this.displayText = displayText;
        this.subItems = null;
    }
    public Objective(String name, String displayText, LinkedHashMap<String, String> subItems) {
        this.name = name;
        this.displayText = displayText;
        this.subItems = subItems;
    }
}
