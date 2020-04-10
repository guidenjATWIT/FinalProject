package application;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
	public static String apps_loc = "C:\\Users\\guidenj\\eclipse-workspace\\FinalProject2\\src\\application\\apps.txt";
	public static String books_loc = "C:\\Users\\guidenj\\eclipse-workspace\\FinalProject2\\src\\application\\books.txt";
	@Override
	public void start(Stage primaryStage) {
		try {
			
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,800,400);
			HBox nav = new HBox(20);
			FlowPane apps = new FlowPane(20,20);
			FlowPane books = new FlowPane(20,20);
			Text apps_header = new Text();
			Text books_header = new Text();
			apps_header.setText("APPLICATIONS");
			books_header.setText("BOOKMARKS");
			
			Button add_new_app = new Button("Add App");
			add_new_app.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						String[] inputs = getInputs().split(","); 
						addApplication(inputs[0], inputs[1]);
						removeApplications(apps);
						apps.getChildren().add(apps_header);
						apps.getChildren().addAll(getApplications());
					}
					catch (NullPointerException e) {} 
				}
				
			});
			
			Button add_new_book = new Button("Add Bookmark");
			add_new_book.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						String[] inputs = getInputs().split(","); 
						addBookmark(inputs[0], inputs[1], inputs[2]);
						removeApplications(books);
						books.getChildren().add(books_header);
						books.getChildren().addAll(getBookmarks());
					}
					catch (NullPointerException e) {} 
				}
				
			});
			
			Button clear_apps = new Button("Clear Apps");
			clear_apps.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					clearStorage(apps_loc);
					removeApplications(apps);
					apps.getChildren().add(apps_header);
				}
				
			});
			clear_apps.setAlignment(Pos.TOP_RIGHT);
			Button clear_books = new Button("Clear Bookmarks");
			clear_books.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					clearStorage(books_loc);
					removeApplications(books);
					books.getChildren().add(books_header);
				}
				
			});
			
			// build apps
			apps.getChildren().add(apps_header);
			nav.getChildren().add(add_new_app);
			nav.getChildren().add(add_new_book);
			nav.getChildren().add(clear_apps);
			nav.getChildren().add(clear_books);
			root.setTop(nav);
			apps.getChildren().addAll(getApplications());
			root.setCenter(apps);
			
			//build books
			books.getChildren().add(books_header);
			books.getChildren().addAll(getBookmarks());
			root.setBottom(books);
			
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public String getInputs() {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Add New");
		dialog.setHeaderText("Format: <NAME>,<FILE_LOCATION>,(URL)");
		dialog.setContentText("Enter: ");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){ 
			return result.get();
		}
		else { return null; }
	}
	public static void clearStorage(String file_name) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(file_name);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {}
	}
	public static void removeApplications(FlowPane content) { content.getChildren().removeAll(content.getChildren()); }
	public static void addApplication(String name, String loc) {
		try {
			FileWriter w = new FileWriter(apps_loc, true);
			Scanner sc = new Scanner(new File(apps_loc));
			while (sc.hasNextLine()) {
				String[] data = sc.nextLine().split(",");
				if (data[0].equalsIgnoreCase(name)) { return; }
			}
			w.write(String.format("%s,%s%n", name,loc));
			w.close();
		} catch (IOException e) {
			System.out.println("File not found");
			System.exit(0);
		}
	}
	
	public static void addBookmark(String name, String loc, String url) {
		try {
			FileWriter w = new FileWriter(books_loc, true);
			Scanner sc = new Scanner(new File(books_loc));
			while (sc.hasNextLine()) {
				String[] data = sc.nextLine().split(",");
				if (data[0].equalsIgnoreCase(name)) { return; }
			}
			w.write(String.format("%s,%s,%s%n", name,loc,url));
			w.close();
		} catch (IOException e) {
			System.out.println("File not found");
			System.exit(0);
		}
	}
	
	public ArrayList<Button> getApplications() {
		ArrayList<Button> list = new ArrayList<Button>();
		Scanner sc;
		try {
			sc = new Scanner(new File(apps_loc));
			while (sc.hasNextLine()) {
				String[] data = sc.nextLine().split(",");
				Button btn = new Button(data[0]);
				App app = new App(data[0], data[1]);
				
				btn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						app.launchApplication();
					}
					
				});
				
				list.add(btn);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(0);
		}
		
		return list;
		
	}
	
	public ArrayList<Button> getBookmarks() {
		ArrayList<Button> list = new ArrayList<Button>();
		Scanner sc;
		try {
			sc = new Scanner(new File(books_loc));
			while (sc.hasNextLine()) {
				String[] data = sc.nextLine().split(",");
				Button btn = new Button(data[0]);
				Bookmark mark = new Bookmark(data[0], data[1], data[2]);
				
				btn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						mark.launchApplication();
					}
					
				});
				
				list.add(btn);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(0);
		}
		
		return list;
		
	}
}
