package main;

import INPUT.KeyboardInputs;
import INPUT.MouseInputs;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {
	private MouseInputs mouseInputs;
	private Game game;


	public GamePanel(Game game) {
		mouseInputs = new MouseInputs(this);
		this.game = game;
		setPanelSize();
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
		addKeyListener(new KeyboardInputs(this));
	}




	private void setPanelSize() {
		Dimension size = new Dimension(1680, 1050);
		setPreferredSize(size);
	}




	public void updateGame(){

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);

	}

	public Game getGame() {
		return game;
	}


}

