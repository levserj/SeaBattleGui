import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class CompGui extends JFrame {

    static FieldButton[][] allComputerFieldButtons = new FieldButton[Game.fieldSize+1][Game.fieldSize+1];
    static HashSet<FieldButton> computerShips = new HashSet<>();

    public CompGui(int sizeX, int sizeY, int LeftCorX, int LeftCorY) throws InterruptedException {

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

        //panel with ships
        JPanel playingBoard = new JPanel(new GridLayout(10,10));
        mainPanel.add(playingBoard);

        for (int i = 1; i<= Game.fieldSize; i++){
            for (int j = 1; j<= Game.fieldSize; j++){
                FieldButton button = new FieldButton(j,i);
                button.addActionListener(new PlayerMoveListener(button));
                allComputerFieldButtons[j] [i] = button;
                playingBoard.add(button);
            }
        }

        //placing computer ships
        UtilStatMeth.shipAutoplacement(allComputerFieldButtons, computerShips);

        setVisible(true);
        Game.threadControl.release();
    }
}
