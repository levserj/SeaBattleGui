import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class PlayerGui extends JFrame {

    static FieldButton[][] allPlayerFieldButtons = new FieldButton[Game.fieldSize+1][Game.fieldSize+1];
    static HashSet <FieldButton> playerShips = new HashSet<>();
    HashSet <SimpleButton> buttonsToBeDisabledAfterStart = new HashSet<>();

    public PlayerGui(int sizeX, int sizeY, int LeftCorX, int LeftCorY) throws InterruptedException {
        //Block main method while preparing player board and placing ships
        Game.threadControl.acquire();

        //standard options to set
        setSize(sizeX,sizeY);
        setLocation(LeftCorX, LeftCorY);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);

        //main panel will contain all other components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
        add(mainPanel);

        int x = getWidth();
        int y = getHeight();

        //panel with ships
        JPanel playingBoard = new JPanel(new GridLayout(10,10));
        playingBoard.setPreferredSize(new Dimension(x,(int)(y*0.9)));
        mainPanel.add(playingBoard);

        //panel with controls
        JPanel controls = new JPanel();
        controls.setPreferredSize(new Dimension(x,(int)(y*0.1)));
        mainPanel.add(controls);
        // Field buttons
        for (int i =1; i<Game.fieldSize+1;i++){
            for (int j =1; j<Game.fieldSize+1;j++){
                FieldButton button = new FieldButton(j,i);
                button.addActionListener(new PlaceShipsListener(button));
                allPlayerFieldButtons[j] [i] = button;
                playingBoard.add(button);
            }
        }
        //control buttons
        SimpleButton ready = new SimpleButton("Ready");
        buttonsToBeDisabledAfterStart.add(ready);
        ready.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerShips.size()!= Game.numberOfShips){
                    InfoMessages.infoBox("You placed "+ playerShips.size()+
                            " ships but you should place "+ Game.numberOfShips, "Ship placement");
                } else {
                    removeListenersFieldAndControlButtons();
                    Game.threadControl.release();
                }
            }
        });
        controls.add(ready);

        SimpleButton autoPlacement = new SimpleButton("AutoPlacement");
        buttonsToBeDisabledAfterStart.add(autoPlacement);
        autoPlacement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilStatMeth.shipAutoplacement(allPlayerFieldButtons, playerShips);
                removeListenersFieldAndControlButtons();
                Game.threadControl.release();
            }
        });
        controls.add(autoPlacement);

        JButton info = new JButton("Info");
        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InfoMessages.infoBox("Green button - placed player ship \r\n " +
                                    "Red button - the ship is hit \r\n " +
                                    "Blue button - a miss","Info");
            }
        });
        controls.add(info);

        setVisible(true);
    }
    private void removeListenersFieldAndControlButtons() {
        for (int i =1; i<Game.fieldSize+1;i++){
            for (int j =1; j<Game.fieldSize+1;j++){
                FieldButton b = allPlayerFieldButtons[i] [j];
                b.removeListeners();
            }
        }
        for (SimpleButton sb:buttonsToBeDisabledAfterStart){
            sb.removeListeners();
        }
    }
}
