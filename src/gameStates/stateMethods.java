package gameStates;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface stateMethods {
    public void update();
    public void draw(Graphics g);
    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);
}
