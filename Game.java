import java.awt.*;
import java.util.concurrent.Semaphore;

public class Game {
    static Semaphore threadControl = new Semaphore(1);
    static boolean playerHitAShip = false;
    static boolean computerTurn = false;
    final static int numberOfShips = 10;
    final static int fieldSize = 10;

    public static void main(String[] args) throws InterruptedException {

        //to put the frame at the same position on different systems(screen sizes)
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        //construct the player board and allow a player to place the ships
        PlayerGui playerGui = new PlayerGui(450, 500, screenSize.width * 1 / 5, screenSize.height * 1 / 4);
        InfoMessages.infoBox("Place your " + numberOfShips + " ships and press Ready", "Game Preparation");

        // to wait for the player to place his ships, semaphore is already acquired in PlayerGui constructor
        // and can be released in "Ready" button
        threadControl.acquire();

        threadControl.release();

        //construct the computer gui and place computer ships
        CompGui compGui = new CompGui(450, 500, screenSize.width * 3 / 5, screenSize.height * 1 / 4);

        InfoMessages.infoBox("Make your shot", "Game, finally!");

        threadControl.acquire();
        outer:
        while (true) {
                // To continue execution semaphore can be released in PlayerMoveListener,
                // i.e when player makes a move and presses a button on the field of the computer
            threadControl.acquire();
            // PlayerHitAShip can be set to true in PlayerMoveListener, if player hits a ship
            if (playerHitAShip) {
                playerHitAShip=false;
                if (CompGui.computerShips.size() == 0) {
                    InfoMessages.infoBox("You won!", "Congratulations");
                    break;
                } else {
                    continue;
                }
            }

            computerTurn = true;

            while (computerTurn) {
                // Let's get new random point
                int x = RandomInt.getRandomInt(fieldSize);
                int y = RandomInt.getRandomInt(fieldSize);
                FieldButton b = PlayerGui.allPlayerFieldButtons[x][y];
                // Check if this shot is already made, if not we set the Hit flag
                if (!b.isHit()) {
                    b.setHit(true);
                    // another check if it's a ship
                    if (b.isAShip()) {
                        b.setBackground(Color.RED);
                        PlayerGui.playerShips.remove(b);
                        // Computer hit a ship, we add nearby points to made shots, and check for computer win
                        UtilStatMeth.addShots(b, PlayerGui.allPlayerFieldButtons);
                        if (PlayerGui.playerShips.size() == 0) {
                            InfoMessages.infoBox("You lost to a randomizer!", "Bad news");
                            break outer;
                        }
                    // If it was a miss
                    } else {
                        b.setBackground(Color.BLUE);
                        computerTurn = false;
                    }

                }
            }
        }
    }
}

