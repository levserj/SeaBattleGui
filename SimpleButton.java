import javax.swing.*;
import java.awt.event.ActionListener;

public class SimpleButton extends JButton {

    public SimpleButton() {
    }

    public SimpleButton(String text) {
        super(text);
    }

    void removeListeners(){
        for(ActionListener al: this.getActionListeners()){
            this.removeActionListener(al);
        }
    }
}
