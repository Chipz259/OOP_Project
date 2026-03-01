package system;

import ui.MainGameFrame;

import java.awt.event.*;

public class MenuActionHandler implements ActionListener {
    private MainGameFrame action;

    public MenuActionHandler(MainGameFrame action){
        this.action = action;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String scanAction = e.getActionCommand();

        if (scanAction.equals("Start")){
            return;
        }
        else if (scanAction.equals("New Game")){
            return;
        }
        else if (scanAction.equals("Exit")){
            System.exit(0);
        }
    }
}
