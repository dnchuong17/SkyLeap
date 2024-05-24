package gameStates;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Option extends State implements Statemethods{
    private BufferedImage optionImage;
    private int optionX, optionY, optionWidth, optionHeight;
    private Color helpColor;
    private Font helpFont;

    private String Help1 = "Movement: Use the left and right arrow keys to move your character. ";
    private String Help2 = "Jumping: The spacebar is your friend for leaps of faith (and hopefully, success).";
    public Option(Game game) throws IOException {
        super(game);
        loadOptionImage();
    }

    private void loadOptionImage() throws IOException {
        optionImage = LoadSave.getSpriteAtlas(LoadSave.HELP_IMAGE);
        optionWidth =  Game.GAME_WIDTH +80;
        optionHeight =Game.GAME_HEIGHT +250;
        optionX = Game.GAME_WIDTH/2 - 875;
        optionY = (int) (Game.SCALE-250);

        helpColor = new Color(128,0,0);
        helpFont = new Font("Century Gothic", Font.PLAIN, 28);
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(optionImage, optionX, optionY, optionWidth, optionHeight, null);

        g.setColor(helpColor);
        g.setFont(helpFont);
        g.drawString(Help1,150, Game.GAME_HEIGHT/2);
        g.drawString(Help2,150, Game.GAME_HEIGHT/2 + 40);


    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            Gamestate.state = Gamestate.MENU;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
