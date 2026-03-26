package Minigame.UnlockBoxPackage;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class ActionHandler implements ActionListener {
    private SlotJPanel slot;
    public ActionHandler(JPanel slot) {
        this.slot = (SlotJPanel) slot;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String opp = e.getActionCommand();
        if(!UnlockBox.isFinished()){
            switch (opp) {
                case "Down" -> slot.updateSlot(-1);
                case "Up" -> slot.updateSlot(1);
            }
        }
    }
}
