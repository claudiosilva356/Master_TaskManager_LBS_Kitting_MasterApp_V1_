package Master_TaskManager;

import javafx.event.*;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import javafx.concurrent.Task;

@SuppressWarnings("restriction")
public class ServerGui extends Application {

	Scene scene;
	String tagId = "tagbeanair";
	Button newTaskBtn;
	ServerCore serverCore;
	ListView<String> usersView = new ListView<>();
	ListView<String> taskView = new ListView<>();
	ListView<String> eventView = new ListView<>();
	Task<Void> task;
	Thread thread;
	String userNameSelected;
	TextField ntUSerName;
	TextField ntTaskName;
	TextField ntTaskDetails;
	TextField ntTaskQuantity;


	@SuppressWarnings("restriction")
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		serverCore = ServerApp.getServerCore();
		scene = new Scene(new Group(), 1000, 600);
		
		usersView = new ListView<>();
		usersView.setPrefHeight(300);
		taskView = new ListView<>();
		taskView.setPrefHeight(300);
		taskView.setPrefWidth(350);
		eventView = new ListView<>();
		eventView.setPrefHeight(300);
		eventView.setPrefWidth(350);
		
		ntUSerName = new TextField();
		ntTaskName = new TextField();
		ntTaskDetails = new TextField();
		ntTaskQuantity = new TextField();
		
		GridPane grid = new GridPane();
	    GridPane GaugeGrid = new GridPane();
	    grid.setVgap(5);
	    grid.setHgap(5);
	    grid.setAlignment(Pos.CENTER);
	    grid.setPadding(new Insets(5, 5, 5, 5));
	    

	    serverCore.addActivity("José", "Get apples", "From basket 4", "", "4");
	    serverCore.addActivity("Rute", "Close APP xpto", "Computer 5", "", "");
	    serverCore.addActivity("Joaquim","Change oil Machine 1", "oil capacity 4L", "", "1");
	    serverCore.addActivity("Joaquim","Change oil Machine 2", "oil capacity 4L", "", "1");
	    serverCore.addActivity("Joaquim","Change oil Machine 3", "oil capacity 4L", "", "1");
	    serverCore.addActivity("Joaquim","tightning screw 1", "screwdriver N10", "", "1");
	    serverCore.updateActivity("Joaquim","Change oil Machine 1", "1L is yet missing");
	    serverCore.updateActivityState("Joaquim","Change oil Machine 1",2);
	    serverCore.updateActivityState("Joaquim","tightning screw 1",0);
	    

	    setUserViewsHandler();
	    setTaskViewsHandler();
	    setEventViewsHandler();
	    
	    newTaskBtn = new Button("New User Task");
	  
	    
	    newTaskBtn.setOnAction(new EventHandler<ActionEvent>() {
       
            public void handle(ActionEvent event) {
            	
            	if(!(ntUSerName.getText().isEmpty() && ntTaskName.getText().isEmpty())) {
            		System.out.println("New task were created");
            		serverCore.addActivity(ntUSerName.getText(), ntTaskName.getText(), ntTaskDetails.getText(), "", ntTaskQuantity.getText());
            		forceRefreshUserView();
            	}
            }
            
        });
		
		grid.add(new Label("Current Tasks:"), 0, 1);
		grid.add(new Label("Task Details:"), 1, 1);
		grid.add(new Label("Event Done"), 2, 1);
		grid.add(usersView, 0, 2);
		grid.add(taskView, 1, 2);
		grid.add(eventView, 2, 2);
		grid.add(new Label("--------------------------------------------------"), 0, 3);
		grid.add(new Label("-----------------------------------------------------------------------"), 1, 3);
		grid.add(new Label("-----------------------------------------------------------------------"), 2, 3);
		grid.add(new Label("User Name"), 0, 4);
		grid.add(ntUSerName, 0, 5);
		grid.add(new Label("Task Name"), 0, 6);
		grid.add(ntTaskName, 0, 7);
		grid.add(new Label("Task Details"), 0, 8);
		grid.add(ntTaskDetails, 0, 9);
		grid.add(new Label("Quantity"), 0, 10);
		grid.add(ntTaskQuantity, 0, 11);
		grid.add(newTaskBtn, 0, 12);
		
		
		 ImageView img1 = new ImageView(new Image(getClass().getResourceAsStream("Altran_logo.png")));
	     grid.add(img1, 2, 13);
		
		//refreshUserView();
		
		new Timer().schedule(
			    new TimerTask() {

			        @Override
			        public void run() {
			            System.out.println("refreshUserView");
			            refreshUserView();
			        }
			    }, 0, 2000);
		
		
		Group root = (Group)scene.getRoot();
	    root.getChildren().add(grid);
	    
	    primaryStage.setScene(scene);
        primaryStage.setTitle("LBS_Kitting_MasterApp_V1");
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            System.exit(0);
        });
	}
	
	public String parseName(String str) {
		str = str.substring(str.indexOf("*") + 1);
		str = str.substring(0, str.indexOf("*"));
		return str;
		
	}
	
	public synchronized void refreshUserView() {
	    //fullfill usersView with all users with tasks ongoing
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println(usersView.getItems().size());
				if(usersView.getItems().size()==0) { //fullfill
					for(String arrListObj : serverCore.listAllUsers()) {
				    	usersView.getItems().add(arrListObj.toString());
				    }
				}
				else if(usersView.getItems().size()!=serverCore.listAllUsers().size()){ //check if there are changes, if yes, remove all and fullfill again
				    		usersView.getItems().clear();
				    		taskView.getItems().clear();
				    		eventView.getItems().clear();
				    		userNameSelected = "";
				    		for(String arrListObj_ : serverCore.listAllUsers()) {
						    	usersView.getItems().add(arrListObj_.toString());
						    }
				    		return;
				    	}

	
				    }
				});
	}
	
	public synchronized void forceRefreshUserView() {
	    //fullfill usersView with all users with tasks ongoing
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 usersView.getItems().clear();
				 taskView.getItems().clear();
				 eventView.getItems().clear();
				 userNameSelected = "";
				 for(String arrListObj_ : serverCore.listAllUsers()) {
						  usersView.getItems().add(arrListObj_.toString());
				}
			}
		});
	}
				
	
	public void setUserViewsHandler() {
	    usersView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(item);
                }
            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    System.out.println("You clicked on " + cell.getItem());
                    //get name
                    System.out.println("Name: " + parseName(cell.getItem()));
                    //fullfill taskView for selected user tasks
                    userNameSelected = parseName(cell.getItem());
                    taskView.getItems().clear();
                    eventView.getItems().clear();
                    for(String arrListObj : serverCore.listUserActivities(parseName(cell.getItem()))) {
				    	taskView.getItems().add(arrListObj.toString());
				    }
                    e.consume();
                }
            });
            return cell;
        });
        
	    usersView.setOnMouseClicked(e -> {
            System.out.println("You clicked on an empty cell");
            
        });
	}
	
	
    public void setTaskViewsHandler(){
	    taskView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(item);
                }
            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    System.out.println("You clicked on " + cell.getItem());
                    //get name
                    System.out.println("Name: " + parseName(cell.getItem()));
                    //fullfill taskView for selected user tasks
                    eventView.getItems().clear();
                    for(String arrListObj : serverCore.listUserEvents(userNameSelected, parseName(cell.getItem()))) {
                    	eventView.getItems().add(arrListObj.toString());
				    }
                    e.consume();
                }
            });
            return cell;
        });
    }
    
    public void setEventViewsHandler(){
    	
    }

	
	

}
