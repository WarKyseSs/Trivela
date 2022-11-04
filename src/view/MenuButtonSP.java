package view;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuButtonSP extends StackPane {
	
	/* *** Declaration of attributes *** */
	private Text text;

	/* *** Constructor *** */
	public MenuButtonSP(String name) {
		
		int rectangleY = 0;
		int rectangleX = 0;
		
		text = new Text(name);
		
		if(name.equals("PLAY")) {
			text.getFont();
			text.setFont(Font.font(52));
			rectangleY = 62;
			rectangleX = 242;
		} else {
			if(!name.equals("CREDITS")) {
				text.getFont();
				text.setFont(Font.font(28));
				rectangleY = 38;
				rectangleX = 168;
			} else {
				text.getFont();
				text.setFont(Font.font(25));
			}
		}

		text.setFill(Color.WHITE);
		
		Rectangle bg = new Rectangle(rectangleX,rectangleY);
		
		bg.setArcWidth(30.00);
		bg.setArcHeight(20.00);
		
		if(!name.equals("CREDITS")) {
			bg.setOpacity(0.4);
			bg.setFill(Color.BLACK);
			bg.setEffect(new GaussianBlur(3.5));
		}
		
		setAlignment(Pos.CENTER);
		setRotate(-0.5);
		getChildren().addAll(bg,text);
		
		this.setOnMouseEntered(event -> {
			if(!name.equals("CREDITS")){
				bg.setTranslateX(10);
				text.setTranslateX(10);
			}
			if(name.equals("EXIT")) {
				text.setFill(Color.RED);
			} else {
				text.setFill(Color.AQUAMARINE);
			}
		});
		
		this.setOnMouseExited(event -> {
			if(!name.equals("CREDITS")) {
				bg.setTranslateX(0);
				text.setTranslateX(0);
			}
			bg.setFill(Color.BLACK);
			text.setFill(Color.WHITE);
		});
		
		DropShadow drop = new DropShadow(50, Color.WHITE);
		drop.setInput(new Glow());
		
		setOnMousePressed(event -> setEffect(drop));
		setOnMouseReleased(event -> setEffect(null));
	}
}