package gameStates;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Option extends State implements Statemethods{
    private BufferedImage optionImage,optionBg;
    private int optionX, optionY, optionWidth, optionHeight;
    private Color helpColor;
    private Font helpFont;

    private String Help1 = "Movement: Use the left and right arrow keys";
    private String Help2 = "or 'A' and 'D' to move your character.";
    private String Help3 = "Jumping: The spacebar is your friend ";
    private String Help4= "for leaps of faith (and hopefully, success).";
    public Option(Game game) throws IOException {
        super(game);
        loadOptionImage();
    }

    private void loadOptionImage() throws IOException {
        optionBg = LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        optionImage = LoadSave.getSpriteAtlas(LoadSave.HELP_IMAGE);
        optionWidth =  Game.GAME_WIDTH +10 ;
        optionHeight =Game.GAME_HEIGHT -200;
        optionX = Game.GAME_WIDTH/2 - optionWidth/2;
        optionY = (int) (Game.SCALE + 80);

        helpColor = new Color(255,255,252);
        helpFont = new Font("Century Gothic", Font.BOLD, 26);
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(optionBg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(optionImage, optionX, optionY, optionWidth, optionHeight, null);

        g.setColor(helpColor);
        g.setFont(helpFont);
        g.drawString(Help1,190, Game.GAME_HEIGHT - 500);
        g.drawString(Help2,190, Game.GAME_HEIGHT - 460);
        g.drawString(Help3,190, Game.GAME_HEIGHT - 400);
        g.drawString(Help4,190, Game.GAME_HEIGHT - 350);
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
