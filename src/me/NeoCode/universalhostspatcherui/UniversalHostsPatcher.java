package me.NeoCode.universalhostspatcherui;

import javafx.application.Application;
import javafx.stage.Stage;
import me.NeoCode.universalhostspatcherui.OsCheck.OSType;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class UniversalHostsPatcher extends Application {

	public static String customPath = "notset";

	@Override
	public void start(Stage primaryStage) {
		try {
			System.out.println("|-------------------------------------------|");
			System.out.println("|                                           |");
			System.out.println("| Starting UniversalHostsPatcher by NeoCode |");
			System.out.println("|                                           |");
			System.out.println("|-------------------------------------------|");
			System.out.println();
			System.out.println();
			if (OsCheck.getOperatingSystemType().equals(OSType.Other)) {
				if ("notset".equals(customPath)) {
					System.out.println("UniversalHostsPatcher not working on " + System.getProperty("os.name"));
					Runtime.getRuntime().exit(0);
				}
				System.out.println("Running on custom path: " + customPath);
			}
			System.out.println("Running on " + System.getProperty("os.name"));
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Layout.fxml"));
			Scene scene = new Scene(root, 355, 176);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("UniversalHostsPatcher");
			primaryStage.getIcons().add(new Image(UniversalHostsPatcher.class.getResourceAsStream("icon.png")));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length == 1) {
			customPath = args[0];
		}
		launch(args);
	}
}
