package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import application.Main;
import exception.NullAnswerException;
import exception.OverAnswerException;
import exception.OverTrueOrFalseAnswerException;
import exception.SameQuestionException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import model.Card;
import model.Category;
import model.ConnectJson;
import model.Question;
import model.Write;

public class MenuCardManagementAP extends AnchorPane {
	
	/* *** Declaration of class attributes *** */	
	private Label lblAuthors;
	private Map<Integer, Label> lblCategory;
	private Map<Category, Integer> categoryCheck;
	private Map<Integer, TextField> txtQuestion;
	private Map<Integer, List<TextField>> txtAnswer;
	private Map<Integer, ToggleGroup> groupAnswer;
	private Map<Integer, List<RadioButton>> rBtnAnswer;
	
	// Card
	private TableView<Card> tvCard;
	
	int y, i;
	double hgap, vgap, dist;
	
	
	// For TableView
	int nbCard = 0;
	String previousAuthor = "";
	
	// Button
	private MenuButtonSP btnBack;
	private MenuButtonSP btnConfirm;
	private MenuButtonSP btnAdd;
	private MenuButtonSP btnDel;
	private TextField txtAuthor;
	
	public MenuCardManagementAP() {
		setTvCard(null);
				
		this.getChildren().addAll(getTvCard(), getBtnBack(), getBtnConfirm(), getBtnAdd(), getBtnDel(), getTxtAuthor());
		
		// Back
		AnchorPane.setLeftAnchor(getBtnBack(), Screen.getPrimary().getVisualBounds().getWidth() * 0.80);
		AnchorPane.setTopAnchor(getBtnBack(), Screen.getPrimary().getVisualBounds().getHeight() * 0.80);
		
		// Confirm
		AnchorPane.setLeftAnchor(getBtnConfirm(), Screen.getPrimary().getVisualBounds().getWidth() * 0.45);
		AnchorPane.setTopAnchor(getBtnConfirm(), Screen.getPrimary().getVisualBounds().getHeight() * 0.30);
		
		// Delete
		AnchorPane.setLeftAnchor(getBtnDel(), Screen.getPrimary().getVisualBounds().getWidth() * 0.45);
		AnchorPane.setTopAnchor(getBtnDel(), Screen.getPrimary().getVisualBounds().getHeight() * 0.35);
		
		// Add
		AnchorPane.setLeftAnchor(getBtnAdd(), Screen.getPrimary().getVisualBounds().getWidth() * 0.45);
		AnchorPane.setTopAnchor(getBtnAdd(), Screen.getPrimary().getVisualBounds().getHeight() * 0.40);
		
		// Author textfield
		AnchorPane.setLeftAnchor(getTxtAuthor(), Screen.getPrimary().getVisualBounds().getWidth() * 0.45);
		AnchorPane.setTopAnchor(getTxtAuthor(), Screen.getPrimary().getVisualBounds().getHeight() * 0.45);
		
		// TableView
		AnchorPane.setLeftAnchor(getTvCard(), (Screen.getPrimary().getVisualBounds().getWidth() * 0.50) - (getTvCard().getPrefWidth() / 2));
		AnchorPane.setTopAnchor(getTvCard(), (Screen.getPrimary().getVisualBounds().getHeight() * 0.50));
		
		create();
	}
	
	public void create() {
		// Add components
		List<Rectangle> cards = new ArrayList<>();
		for (i = 0; i < Category.getNb(); i = i + 2) {
			cards.add(new Rectangle(400, 115));
			cards.add(new Rectangle(400, 115));
			
			// Position
			cards.get(i).setY(Screen.getPrimary().getVisualBounds().getHeight() * (0.20 + ((double) i/10.0)));
			cards.get(i).setX(Screen.getPrimary().getVisualBounds().getWidth() * 0.19);
			cards.get(i + 1).setY(Screen.getPrimary().getVisualBounds().getHeight() * (0.20 + (((double) i)/10.0)));
			cards.get(i + 1).setX(Screen.getPrimary().getVisualBounds().getWidth() * 0.59);
			
			// Set rounded rectangle
			cards.get(i).setArcWidth(30.0); 
			cards.get(i).setArcHeight(30.0);
			cards.get(i + 1).setArcWidth(30.0); 
			cards.get(i + 1).setArcHeight(30.0);
			
			cards.get(i).setFill(Color.BLACK);
			cards.get(i).setOpacity(0.4);
			cards.get(i + 1).setFill(Color.BLACK);
			cards.get(i + 1).setOpacity(0.4);
			getChildren().addAll(cards.get(i), cards.get(i + 1));
		}
		
		categoryCheck = new TreeMap<Category, Integer>();
		for (Category cat : Category.values()) {
			categoryCheck.put(cat, y);
			y++;
		}
		
		for (Map.Entry<Category, Integer> entry : categoryCheck.entrySet()) {
			Category key = entry.getKey();
		    Object value = entry.getValue();
		    y = Integer.parseInt(value.toString());
	    	getLblAuthors().setText("");
			getLblCategory().put(y, new Label(key.toString()));
			getTxtQuestion().put(y, new TextField(""));
			
			getTxtAnswer().put(y, new ArrayList<>());
		    for(i = 0; i < 3; i++){
		    	getTxtAnswer().get(y).add(new TextField(""));
		    	getTxtAnswer().get(y).get(i).setFont(Font.font("Arial",  FontWeight.NORMAL, 15));
		    	this.getChildren().add(getTxtAnswer().get(y).get(i));
		    }

		    // Create radio buton
		    getGroupAnswer().put(y, new ToggleGroup());
			getrBtnAnswer().put(y, new ArrayList<>());
		    
			getrBtnAnswer().get(y).add(new RadioButton("True"));
			getrBtnAnswer().get(y).get(0).setToggleGroup(getGroupAnswer().get(y));
			getrBtnAnswer().get(y).get(0).setTextFill(Color.WHITE);
			
			getrBtnAnswer().get(y).add(new RadioButton("True"));
			getrBtnAnswer().get(y).get(1).setToggleGroup(getGroupAnswer().get(y));
			getrBtnAnswer().get(y).get(1).setTextFill(Color.WHITE);
			
			getrBtnAnswer().get(y).add(new RadioButton("True"));
			getrBtnAnswer().get(y).get(2).setToggleGroup(getGroupAnswer().get(y));
			getrBtnAnswer().get(y).get(2).setTextFill(Color.WHITE);	

			getChildren().addAll(getrBtnAnswer().get(y).get(0), getrBtnAnswer().get(y).get(1), getrBtnAnswer().get(y).get(2));
			getChildren().addAll(getLblCategory().get(y), getTxtQuestion().get(y));
			
			// Position
			position(y);
		}
	}
	
	/* Position */
	public void position(int y) {
		if (y == 3) {
			hgap = 0.40;
			dist = 0.0;
		}
		
		vgap = ((double) dist);
		dist += 0.2;
		
		getLblCategory().get(y).setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() * (0.202 + vgap));
		getLblCategory().get(y).setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * (0.20 + hgap));
		getLblCategory().get(y).setFont(Font.font ("Verdana", 20));
		getLblCategory().get(y).setTextFill(Color.WHITE);
		getTxtQuestion().get(y).setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() * (0.23 + vgap));
		getTxtQuestion().get(y).setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * (0.20 + hgap));
		getTxtQuestion().get(y).setPrefWidth(360);
		getTxtAnswer().get(y).get(0).setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() * (0.26 + vgap));
		getTxtAnswer().get(y).get(0).setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * (0.20 + hgap));
		getTxtAnswer().get(y).get(0).setPrefWidth(110);
		getTxtAnswer().get(y).get(1).setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() * (0.26 + vgap));
		getTxtAnswer().get(y).get(1).setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() *( 0.265 + hgap));
		getTxtAnswer().get(y).get(1).setPrefWidth(110);
		getTxtAnswer().get(y).get(2).setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() * (0.26 + vgap));
		getTxtAnswer().get(y).get(2).setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * (0.330 + hgap));
		getTxtAnswer().get(y).get(2).setPrefWidth(110);
		getrBtnAnswer().get(y).get(0).setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() * (0.29 + vgap));
		getrBtnAnswer().get(y).get(0).setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * (0.20 + hgap));
		getrBtnAnswer().get(y).get(1).setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() * (0.29 + vgap));
		getrBtnAnswer().get(y).get(1).setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * (0.265 + hgap));
		getrBtnAnswer().get(y).get(2).setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() * (0.29 + vgap));
		getrBtnAnswer().get(y).get(2).setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * (0.330 + hgap));
	}
		
	public void save() {	
		Question question;
		for(int i = 0; i <  Category.getNb(); i++) {					
			int n = 0;
			
			question = getTvCard().getSelectionModel().getSelectedItem().getListQuestions().get(i);
			question.setInterrogation(getTxtQuestion().get(i).getText());
			question.getChoices().clear();
			question.getChoices().put(getTxtAnswer().get(i).get(0).getText(), getrBtnAnswer().get(i).get(0).isSelected());
			question.getChoices().put(getTxtAnswer().get(i).get(1).getText(), getrBtnAnswer().get(i).get(1).isSelected());
			question.getChoices().put(getTxtAnswer().get(i).get(2).getText(), getrBtnAnswer().get(i).get(2).isSelected());

			getTxtQuestion().get(i).setText(question.getInterrogation());
			for (Map.Entry<String, Boolean> entry : question.getChoices().entrySet()) {
			    String key = entry.getKey();
			    Boolean choices = entry.getValue();
			    getTxtAnswer().get(i).get(n).setText(key);
			    getrBtnAnswer().get(i).get(n).setSelected(choices);
			    if(!choices) {
					getTxtAnswer().get(i).get(n).setOpacity(0.5);
				} else {
					getTxtAnswer().get(i).get(n).setOpacity(1);
				}
			    n++;
			}
		}
	}
	
	/* Getters / Setters */
	/* BtnBack */
	public MenuButtonSP getBtnBack() {
		// Initialize if null 
		if (btnBack == null) { btnBack = new MenuButtonSP("Back");
			
			// Set size
			btnBack.setPrefWidth(90);
			
			// Add MouseClickedEvent
			btnBack.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					Write.writeJson(ConnectJson.getDeck());
					
					// Initialize
					MainMenuVB mn = new MainMenuVB();	
					Pane root = new  Pane();
					ImageView imgView = new ImageView(new Image(Main.getMainBg()));	
					
					// Add components
					root.getChildren().addAll(imgView, mn);
					
					// Set this root in the main scene -> stage 
					Main.getStage().getScene().setRoot(root); 
				}
			});	  
		}
		return btnBack;
	}

	@SuppressWarnings("unchecked")
	public TableView<Card> getTvCard() {
		if(tvCard == null) {
			// Initialize table
			tvCard = new TableView<Card>();
			tvCard.setPrefWidth(250.);
			
			// Add cards
			ObservableList<Card> data = FXCollections.observableArrayList(ConnectJson.getDeck().getListCard());
			tvCard.setItems(data);
			
			// Create and settings columns
			tvCard.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

			// Editable table
			tvCard.setEditable(true);
			
			// Add columns
			TableColumn<Card, List<Question>> questions = new TableColumn<>("Cards");
			
			questions.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getListQuestions()));
			questions.setCellFactory(tc -> new TableCell<Card, List<Question>>() {				
		        @Override
		        public void updateItem(List<Question> item, boolean empty) {
		            super.updateItem(item, empty);
		            if (item == null || empty) {
		                setText("");
		            } else {
		                setText(item.get(0).getAuthor() + " : " + item.get(0).getInterrogation());
		            }
		        }
		    });
						
			tvCard.getColumns().addAll(questions);
			
			// Set action
			tvCard.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			    if (newSelection != null) {
			    	getBtnDel().setDisable(false);
					for(i = 0; i < 6; i++) {
						getTxtQuestion().get(i).setText(newSelection.getListQuestions().get(i).getInterrogation());
						y = 0;
						for(String choices: newSelection.getListQuestions().get(i).getChoices().keySet()){
							getTxtAnswer().get(i).get(y).setText(choices);
							y++;
						}
						y = 0;
						for(Boolean choices: newSelection.getListQuestions().get(i).getChoices().values()){
							getrBtnAnswer().get(i).get(y).setSelected(choices);
							if(!choices) {
								getTxtAnswer().get(i).get(y).setOpacity(0.5);
							} else {
								getTxtAnswer().get(i).get(y).setOpacity(1);
							}
							y++;
						}
					}			
			    }
			});
		}
		return tvCard;
	}
	public void setTvCard(TableView<Card> tvCard) {
		this.tvCard = tvCard;
	}
	public MenuButtonSP getBtnConfirm() {
		if(btnConfirm == null) {
			btnConfirm = new MenuButtonSP("Confirm");
			
			btnConfirm.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					save();
				}
			});	  
		}
		return btnConfirm;
	}
	public MenuButtonSP getBtnAdd() {
		if(btnAdd == null) {
			btnAdd = new MenuButtonSP("Add card");
			
			btnAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					if(!getTxtAuthor().getText().isEmpty()) {
						Card c = new Card();
						Question q;
						for(Category cat: Category.values()) {
							q = new Question(getTxtAuthor().getText(), cat, cat.toString());
							try {
								q.addAnswer("1", true);
								q.addAnswer("2", false);
								q.addAnswer("3", false);
								c.addQuestion(q.clone());
							} catch (OverAnswerException e) {
								e.printStackTrace();
							} catch (SameQuestionException e) {
								e.printStackTrace();
							} catch (OverTrueOrFalseAnswerException e) {
								e.printStackTrace();
							} catch (NullAnswerException e) {
								e.printStackTrace();
							}
						}
						
						ConnectJson.getDeck().addcard(c.clone());
						getTvCard().getItems().add(c.clone());	
						getTvCard().refresh();
					}
				}
			});	  
		}
		return btnAdd;
	}
	public MenuButtonSP getBtnDel() {
		if(btnDel == null) {
			btnDel = new MenuButtonSP("Delete card");
			btnDel.setDisable(true);
			
			btnDel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					ConnectJson.getDeck().getListCard().remove(tvCard.getSelectionModel().getSelectedItem());
					getTvCard().getItems().remove(tvCard.getSelectionModel().getSelectedItem());
					getTvCard().refresh();
				}
			});	  
		}
		return btnDel;
	}
	public Label getLblAuthors() {
		if(lblAuthors == null) {
			lblAuthors = new Label("");
		}
		return lblAuthors;
	}
	public Map<Integer, Label> getLblCategory() {
		if(lblCategory == null) {
			lblCategory = new HashMap<Integer, Label>();
		}
		return lblCategory;
	}
	public Map<Integer, TextField> getTxtQuestion() {
		if(txtQuestion == null) {
			txtQuestion = new HashMap<>();
		}
		return txtQuestion;
	}
	public Map<Integer, List<TextField>> getTxtAnswer() {
		if(txtAnswer == null) {
			txtAnswer = new HashMap<Integer, List<TextField>>();
		}
		return txtAnswer;
	}
	public Map<Integer, ToggleGroup> getGroupAnswer() {
		if(groupAnswer == null) {
			groupAnswer = new HashMap<Integer, ToggleGroup>();
		}
		return groupAnswer;
	}
	public Map<Integer, List<RadioButton>> getrBtnAnswer() {
		if(rBtnAnswer == null){
			rBtnAnswer = new HashMap<Integer, List<RadioButton>>();
		}
		return rBtnAnswer;
	}
	public int getNbCard() {
		return nbCard;
	}
	public void setNbCard(int nbCard) {
		this.nbCard = nbCard;
	}
	public String getPreviousAuthor() {
		return previousAuthor;
	}
	public void setPreviousAuthor(String previousAuthor) {
		this.previousAuthor = previousAuthor;
	}
	public TextField getTxtAuthor() {
		if(txtAuthor == null) {
			txtAuthor = new TextField();
		}
		return txtAuthor;
	}
}