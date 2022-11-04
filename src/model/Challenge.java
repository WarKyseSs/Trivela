package model;

import java.util.Random;

import view.GameBoardAP;

public class Challenge {

	/**
	 * Method that calls the challenges randomly
	 */
	public static void randomChallenge() {
		// Create a random number who choose the challenge
		Random random = new Random();
		int nb;
		nb = random.nextInt(4);
		
		// Test who call the right method
		if(nb==1) {
			resetPosition();
		} else if(nb==2) {
			randomPosition();
		} else if(nb==3) {
			switchPostition();
		}
	}

	/**
	 * Method that resets the position of the player at the beginning of the board
	 */
	public static void resetPosition() {

		int tmpX = GameBoardAP.getPlayerX().get(GameBoardAP.getPlaying());
		int tmpY = GameBoardAP.getPlayerY().get(GameBoardAP.getPlaying());
		
		GameBoardAP.getPlayerX().replace(GameBoardAP.getPlaying(), 0);
		GameBoardAP.getPlayerY().replace(GameBoardAP.getPlaying(), 0);
		
		// Image
		GameBoardAP.getPositionImg().get(GameBoardAP.getPlaying())[GameBoardAP.getY().get(tmpY)][GameBoardAP.getX().get(tmpX)].setImage(null);
		
		tmpX = GameBoardAP.getPlayerX().get(GameBoardAP.getPlaying());
		tmpY = GameBoardAP.getPlayerY().get(GameBoardAP.getPlaying());

		GameBoardAP.getPositionImg().get(GameBoardAP.getPlaying())[GameBoardAP.getY().get(tmpY)][GameBoardAP.getX().get(tmpX)].setImage(GameBoardAP.getPlayers().get(GameBoardAP.getPlaying()).getImg());
		GameBoardAP.getPositionImg().get(GameBoardAP.getPlaying())[GameBoardAP.getY().get(tmpY)][GameBoardAP.getX().get(tmpX)].setScaleY(-1.0);
		GameBoardAP.getPositionImg().get(GameBoardAP.getPlaying())[GameBoardAP.getY().get(tmpY)][GameBoardAP.getX().get(tmpX)].setRotate(90);
		GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[0] = GameBoardAP.getX().get(GameBoardAP.getPlayerY().get(GameBoardAP.getPlaying()));
		GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[1] = GameBoardAP.getY().get(GameBoardAP.getPlayerY().get(GameBoardAP.getPlaying()));
		GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[3] = GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[1];
		GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[2] = GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[0];
		
		String previousName = GameBoardAP.getPlayers().get(GameBoardAP.getPlaying()).getName();
		GameBoardAP.setPlaying();
		
		GameBoardAP.getLblPlayer().setText(previousName + " goes back to square one. " + GameBoardAP.getPlayers().get(GameBoardAP.getPlaying()).getName() + " turn to play");
		GameBoardAP.imgSize(GameBoardAP.getPlaying());
	}

	/**
	 * Method that sets the player's position to a random place
	 */
	public static void randomPosition() {
		Random random = new Random();
		int nb;
		nb = random.nextInt((GameBoardAP.getX().size()));
		
		int tmpX = GameBoardAP.getPlayerX().get(GameBoardAP.getPlaying());
		int tmpY = GameBoardAP.getPlayerY().get(GameBoardAP.getPlaying());
		
		GameBoardAP.getPlayerX().replace(GameBoardAP.getPlaying(), nb);
		GameBoardAP.getPlayerY().replace(GameBoardAP.getPlaying(), nb);
		
		// Image
		GameBoardAP.getPositionImg().get(GameBoardAP.getPlaying())[GameBoardAP.getY().get(tmpY)][GameBoardAP.getX().get(tmpX)].setImage(null);
		
		tmpX = GameBoardAP.getPlayerX().get(GameBoardAP.getPlaying());
		tmpY = GameBoardAP.getPlayerY().get(GameBoardAP.getPlaying());

		GameBoardAP.getPositionImg().get(GameBoardAP.getPlaying())[GameBoardAP.getY().get(tmpY)][GameBoardAP.getX().get(tmpX)].setImage(GameBoardAP.getPlayers().get(GameBoardAP.getPlaying()).getImg());
		GameBoardAP.getPositionImg().get(GameBoardAP.getPlaying())[GameBoardAP.getY().get(tmpY)][GameBoardAP.getX().get(tmpX)].setScaleY(-1.0);
		GameBoardAP.getPositionImg().get(GameBoardAP.getPlaying())[GameBoardAP.getY().get(tmpY)][GameBoardAP.getX().get(tmpX)].setRotate(90);
		GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[0] = GameBoardAP.getX().get(GameBoardAP.getPlayerY().get(GameBoardAP.getPlaying()));
		GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[1] = GameBoardAP.getY().get(GameBoardAP.getPlayerY().get(GameBoardAP.getPlaying()));
		GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[3] = GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[1];
		GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[2] = GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[0];
		
		String previousName = GameBoardAP.getPlayers().get(GameBoardAP.getPlaying()).getName();
		GameBoardAP.setPlaying();
		
		GameBoardAP.getLblPlayer().setText(previousName + " randomly changed position. " + GameBoardAP.getPlayers().get(GameBoardAP.getPlaying()).getName() + " turn to play");
		GameBoardAP.imgSize(GameBoardAP.getPlaying());
	}

	/**
	 * Method that switches the position of the player with another randomly
	 */@SuppressWarnings("unlikely-arg-type")
	public static void switchPostition() {		
		// Create a random number who choose the other player to switch position with
		Random random = new Random();
		int nb;
		nb = random.nextInt((GameBoardAP.getPlayers().size()));
		
		while(nb == GameBoardAP.getPlaying()) {
			if(GameBoardAP.getEnding().contains(nb)) {
				nb = GameBoardAP.getPlaying();
			} else {
				nb = random.nextInt(GameBoardAP.getPlayers().size());
			}
		}
		
		int tmpX = GameBoardAP.getPlayerX().get(GameBoardAP.getPlaying());
		int tmpY = GameBoardAP.getPlayerY().get(GameBoardAP.getPlaying());
		int tmpXO = GameBoardAP.getPlayerX().get(nb);
		int tmpYO = GameBoardAP.getPlayerY().get(nb);
		
		// Set current player position with random player position
		GameBoardAP.getPlayerX().replace(GameBoardAP.getPlaying(), tmpXO);
		GameBoardAP.getPlayerY().replace(GameBoardAP.getPlaying(), tmpYO);
		// Set the position of the random player with that of the current player
		GameBoardAP.getPlayerX().replace(nb, tmpX);
		GameBoardAP.getPlayerY().replace(nb, tmpY);		
		
		// Image
		GameBoardAP.getPositionImg().get(GameBoardAP.getPlaying())[GameBoardAP.getY().get(tmpY)][GameBoardAP.getX().get(tmpX)].setImage(null);
		GameBoardAP.getPositionImg().get(nb)[GameBoardAP.getY().get(tmpYO)][GameBoardAP.getX().get(tmpXO)].setImage(null);
		
		GameBoardAP.getPositionImg().get(GameBoardAP.getPlaying())[GameBoardAP.getY().get(tmpYO)][GameBoardAP.getX().get(tmpXO)].setImage(GameBoardAP.getPlayers().get(GameBoardAP.getPlaying()).getImg());
		GameBoardAP.getPositionImg().get(GameBoardAP.getPlaying())[GameBoardAP.getY().get(tmpYO)][GameBoardAP.getX().get(tmpXO)].setScaleY(-1.0);
		GameBoardAP.getPositionImg().get(GameBoardAP.getPlaying())[GameBoardAP.getY().get(tmpYO)][GameBoardAP.getX().get(tmpXO)].setRotate(90);
		GameBoardAP.getPositionImg().get(nb)[GameBoardAP.getY().get(tmpY)][GameBoardAP.getX().get(tmpX)].setImage(GameBoardAP.getPlayers().get(nb).getImg());
		GameBoardAP.getPositionImg().get(nb)[GameBoardAP.getY().get(tmpY)][GameBoardAP.getX().get(tmpX)].setScaleY(-1.0);
		GameBoardAP.getPositionImg().get(nb)[GameBoardAP.getY().get(tmpY)][GameBoardAP.getX().get(tmpX)].setRotate(90);
		
		GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[0] = GameBoardAP.getX().get(GameBoardAP.getPlayerY().get(GameBoardAP.getPlaying()));
		GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[1] = GameBoardAP.getY().get(GameBoardAP.getPlayerY().get(GameBoardAP.getPlaying()));
		GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[3] = GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[1];
		GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[2] = GameBoardAP.getPosition().get(GameBoardAP.getPlaying())[0];
		
		GameBoardAP.getPosition().get(nb)[0] = GameBoardAP.getX().get(GameBoardAP.getPlayerX().get(nb));
		GameBoardAP.getPosition().get(nb)[1] = GameBoardAP.getY().get(GameBoardAP.getPlayerY().get(nb));
		GameBoardAP.getPosition().get(nb)[3] = GameBoardAP.getPosition().get(nb)[1];
		GameBoardAP.getPosition().get(nb)[2] = GameBoardAP.getPosition().get(nb)[0];
		
		String previousName = GameBoardAP.getPlayers().get(GameBoardAP.getPlaying()).getName();
		GameBoardAP.setPlaying();
		GameBoardAP.getLblPlayer().setText(previousName + " exchange of position with " + GameBoardAP.getPlayers().get(nb).getName() + ". " + GameBoardAP.getPlayers().get(GameBoardAP.getPlaying()).getName() + " turn to play");
		GameBoardAP.imgSize(GameBoardAP.getPlaying());
	}
}
