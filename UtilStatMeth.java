import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;

public class UtilStatMeth {

    //Checks if it's possible to place a new ship  at given button
    static boolean checkButtonForNewShip(FieldButton button, HashSet<FieldButton> placedShips) {
        for (FieldButton b : placedShips) {
            if ((Math.abs(button.getXCoor() - b.getXCoor()) <= 1) &&
                    (Math.abs(button.getYCoor() - b.getYCoor()) <= 1))
                return false;
        }
        return true;
    }

    // This method adds shots around a hit ship
    static void addShots(FieldButton button, FieldButton[][] arrayOfAllButtons) {
        int x = button.getXCoor();
        int y = button.getYCoor();
        //check coordinates of all surrounding buttons
        for (int i = (x - 1); i < x + 2; i++) {
            if (!coorCheck(i)) continue;
            for (int j = (y - 1); j < y + 2; j++) {
                if (!coorCheck(j)) continue;
                //settings for new shot
                FieldButton b = arrayOfAllButtons[i][j];
                if (b != button) b.setBackground(Color.BLUE);
                b.setHit(true);
                if (!Game.computerTurn)
                    b.removeListeners();//redundant during computer move-there are already no listeners in PlayerGui
            }
        }
    }
    //Used only in previous method addShots
    static boolean coorCheck(int coordinate) {
        return (coordinate > 0 && coordinate <= Game.fieldSize);
    }

    // Places new ships before the game randomly
    static void shipAutoplacement(FieldButton[][] arrayOfAllButtons, HashSet<FieldButton> placedShips) {

        while (true) {
            if (placedShips.size() == Game.numberOfShips) {
                break;
            }
            int x = RandomInt.getRandomInt(Game.fieldSize);
            int y = RandomInt.getRandomInt(Game.fieldSize);
            if (checkButtonForNewShip(new FieldButton(x, y), placedShips)) {
                FieldButton b = arrayOfAllButtons[x][y];
                // We don't need to change the color of the button if it's computer's ship
                if (Arrays.equals(arrayOfAllButtons, PlayerGui.allPlayerFieldButtons)) {
                    b.setBackground(Color.GREEN);
                }
                b.setAShip(true);
                placedShips.add(b);
            }
        }
    }
}
