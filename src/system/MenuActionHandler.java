package system;

import ui.CutscenePanel;
import ui.MainGameFrame;

import javax.swing.*;
import java.awt.event.*;

public class MenuActionHandler implements ActionListener, MouseListener {
    private MainGameFrame action;

    public MenuActionHandler(MainGameFrame action){
        this.action = action;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String scanAction = e.getActionCommand();

        if (scanAction.equals("Start")) {
            action.startCutscene();
        }
        else if (scanAction.equals("Resume")) {
            action.transitionToGame();
        }
        else if (scanAction.equals("Setting")) {
            action.toggleSetting(true, false);
        }
        else if (scanAction.equals("Exit")) {
            System.exit(0);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        AudioManager.playSFX("src/res/sound/MouseClick.wav", 0.0f);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        AudioManager.playSFX("src/res/sound/MouseHover.wav", -30.0f);
    }

    @Override
    public void mouseExited(MouseEvent e) {}
}