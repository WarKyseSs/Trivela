package view;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import application.Main;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class AdminMenuVB extends VBox {
	
	/* *** Declaration of class attributes *** */
	VBox adminLog = new VBox(6);
	VBox adminBtn = new VBox(25);
	
	private Label lblLog, lblPassword;
	private TextField txtLog;
	private PasswordField pswLog;
	
	private MenuButtonSP btnSign;
	private MenuButtonSP btnBack;
	
	private Background bg;
	
	
	/* *** Constructor *** */
	public AdminMenuVB() {
		// Add components
		getChildren().addAll(getAdminLog(), getAdminBtn());
	}

	
	/* *** Getters *** */
	/* BtnLog */
	public VBox getAdminBtn() {
		
		// Initialize if null
		if(adminBtn == null) { adminBtn = new VBox(6); }
		
		// Replace the menu
		adminBtn.setTranslateX((Screen.getPrimary().getVisualBounds().getWidth() / 2) - 90);
		adminBtn.setTranslateY((Screen.getPrimary().getVisualBounds().getHeight() / 2));	
		
		// Add components
		adminBtn.getChildren().addAll(getBtnSign(), getBtnBack());	
		
		return adminBtn;
	}
	
	/* AdminLog */
	public VBox getAdminLog() {
		
		// Initialize if null
		if(adminLog == null) { adminLog = new VBox(25); }
		
		// Replace the menu
		adminLog.setTranslateX((Screen.getPrimary().getVisualBounds().getWidth() / 2) - 90);
		adminLog.setTranslateY((Screen.getPrimary().getVisualBounds().getHeight() / 2) - 50);	
		
		// Add components
		adminLog.getChildren().addAll(getLblLog(), getTxtLog(), getLblPassword(),getPswLog(), getBtnSign(), getBtnBack());	
		
		return adminLog;
	}

	/* LblLog */
	public Label getLblLog() {
		if(lblLog == null) {
			lblLog = new Label("Login");
			lblLog.setPrefWidth(90);
			lblLog.setTextFill(Color.WHITE);
			lblLog.setBackground(getBg());
		}	
		return lblLog;
	}
	
	/* LblPassword */
	public Label getLblPassword() {
		if(lblPassword == null) {
			lblPassword = new Label("Password");
			lblPassword.setPrefWidth(90);
			lblPassword.setTextFill(Color.WHITE);
			lblPassword.setBackground(getBg());
		}
		return lblPassword;
	}
	
	/* LblPassword */
	public TextField getTxtLog() {
		if(txtLog == null) {
			txtLog = new TextField();
		}
		return txtLog;
	}
	
	/* LblPassword */
	public PasswordField getPswLog() {
		if(pswLog == null) {
			pswLog = new PasswordField();
		}
		return pswLog;
	}
	
	/* LblPassword */
	public MenuButtonSP getBtnSign() {
		if(btnSign == null) { btnSign = new MenuButtonSP("Sign in");

			// Add MouseClickedEvent
			btnSign.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					
					if(getTxtLog().textProperty().getValue().equals("admin")) {
						MessageDigest md = null;
						try {
							md = MessageDigest.getInstance("SHA1");
							md.update(getPswLog().textProperty().getValue().getBytes());
							
							byte[] digest = md.digest();
							StringBuffer sb = new StringBuffer();
							for (byte b : digest) {
								sb.append(String.format("%02x", b & 0xff));
							}
							
							if(!sb.toString().equals("3c32b727afdbbf9fa84473d464d2f1d1252dfcd8")) {
								return;
							}	
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						}						
					} else {
						return;
					}
					
					// Initialize
					MenuCardManagementAP mn = new MenuCardManagementAP();	
					Pane root = new  Pane();
					ImageView imgView = new ImageView(new Image(Main.getBg()));	
					
					// Add components
					root.getChildren().addAll(imgView, mn);
					
					// Set this root in the main scene -> stage
					Main.getStage().getScene().setRoot(root);
				}
			});
		}
		return btnSign;
	}
	
	/* LblPassword */
	public MenuButtonSP getBtnBack() {
		if (btnBack == null) { btnBack = new MenuButtonSP("Back");
			
			// Set size
			btnBack.setPrefWidth(90);
			
			btnBack.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					
					// Initialize
					Pane root = new  Pane();
					Image img = new Image(Main.getMainBg());
					ImageView imgView = new ImageView(img);	
					MainMenuVB gameMenu = new MainMenuVB();
					
					// Add components
					root.getChildren().addAll(imgView, gameMenu);
					
					// Set this root in the main scene -> stage
					Main.getStage().getScene().setRoot(root);
				}
			});			
		}
		return btnBack;
	}
	
	/* LblPassword */
	public Background getBg() {
		if (bg == null) {
			bg = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
		}
		return bg;
	}
}