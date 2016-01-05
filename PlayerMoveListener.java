import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 class PlayerMoveListener implements ActionListener {

    private FieldButton button;

    PlayerMoveListener(FieldButton button) {
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Works only if it's not computer turn
        if (!Game.computerTurn) {
            button.setHit(true);
            if (button.isAShip()) {
                button.setBackground(Color.RED);
                Game.playerHitAShip = true;
                CompGui.computerShips.remove(button);
                button.removeListeners();
                InfoMessages.infoBox("You've hit a ship!", "Congratulations");
                UtilStatMeth.addShots(button, CompGui.allComputerFieldButtons);
                Game.threadControl.release();
            } else {
                button.setBackground(Color.BLUE);
                button.removeListeners();
                Game.computerTurn = true;
                Game.threadControl.release();
            }
        }
    }
}
