package main;

import INPUT.KeyboardInputs;
import INPUT.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
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
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(size);
		System.out.println("GamePanel size set to: " + GAME_WIDTH + "x" + GAME_HEIGHT);
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

