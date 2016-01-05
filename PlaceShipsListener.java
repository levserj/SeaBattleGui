import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PlaceShipsListener implements ActionListener {
    private FieldButton button;

    public PlaceShipsListener(FieldButton fieldButton) {
        this.button = fieldButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (PlayerGui.playerShips.size()== Game.numberOfShips) {
            InfoMessages.infoBox("You've already placed "+ Game.numberOfShips, "Too many ships");
        }
        else if(button.isAShip()) {
            button.setBackground(button.getDefaultColor());
            PlayerGui.playerShips.remove(button);
        }
        else if (UtilStatMeth.checkButtonForNewShip(button, PlayerGui.playerShips)) {
            button.setBackground(Color.GREEN);
            button.setAShip(true);
            PlayerGui.playerShips.add(button);
        } else {
            InfoMessages.infoBox("This ship is too close to another one", "Incorrect position");
        }
    }
}
