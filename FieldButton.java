import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

final class FieldButton extends JButton {
    private Color defaultColor = this.getBackground();
    private boolean isAShip = false;
    private boolean isHit = false;
    private int xCoor;
    private int yCoor;

    FieldButton( int x, int y) {
        this.xCoor = x;
        this.yCoor = y;
    }

    @Override
    public String toString() {
        return "FieldButton with coordinates x="+getXCoor()+", y="+getYCoor()+" Is a ship: "+isAShip();
    }

    @Override
    public int hashCode() {
        return 31*xCoor+yCoor;
    }

    @Override
    public boolean equals(Object that) {
        if (that==null) return false;
        if (this==that) return true;
        if (!(that instanceof FieldButton)) return false;
        return ((this.xCoor == ((FieldButton) that).xCoor) &&
                (this.yCoor == ((FieldButton) that).yCoor));
    }

    int getXCoor(){
        return xCoor;
    }

    int getYCoor() {
        return yCoor;
    }

    Color getDefaultColor() {
        return defaultColor;
    }

    boolean isAShip() {
        return isAShip;
    }

    boolean isHit() {
        return isHit;
    }

    void setAShip(boolean b) {
        isAShip = b;
    }

    void setHit(boolean b) {
        isHit = b;
    }

    void removeListeners(){
        for(ActionListener al: this.getActionListeners()){
            this.removeActionListener(al);
        }
    }


}
