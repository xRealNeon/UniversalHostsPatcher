package me.NeoCode.universalhostspatcherui;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import me.NeoCode.universalhostspatcherui.OsCheck.OSType;

public class Controller {

	@FXML
	TextField url;

	@FXML
	Button patch;

	@FXML
	Button remove;

	@FXML
	Text by;

	@FXML
	ChoiceBox<String> scrips;

	private ArrayList<String> hosts = new ArrayList<String>();
	private boolean fehler;

	@FXML
	private void initialize() {

		getScrips();

		scrips.setTooltip(new Tooltip("Script"));
		url.setTooltip(new Tooltip("Custom URL"));

		by.setOnMouseClicked((event -> {
			try {
				Desktop.getDesktop().browse(new URI("https://github.com/xRealNeon"));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}));
		patch.setOnAction((event -> {
			if (url.getText().equals("")) {
				addIndexHosts(scrips.getSelectionModel().getSelectedItem().toLowerCase());
			} else {
				addHosts(url.getText());
			}
			if (fehler) {
				patch();
			}
			fehler = true;
			hosts.clear();
		}));
		remove.setOnAction((event -> {
			if (url.getText().equals("")) {
				addIndexHosts(scrips.getSelectionModel().getSelectedItem().toLowerCase());
			} else {
				addHosts(url.getText());
			}
			if (fehler) {
				remove();
			}
			fehler = true;
			hosts.clear();
		}));
	}

	private void getScrips() {
		ArrayList<String> scripts = new ArrayList<String>();
		String logscripts = "Following Scrips Loadet: ";
		try {
			URL link = new URL(
					"https://raw.githubusercontent.com/xRealNeon/UniversalHostsPatcher/master/scripts/index.list");
			BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream()));
			String line;

			while ((line = in.readLine()) != null) {
				logscripts = logscripts + line.substring(0, 1).toUpperCase() + line.substring(1) + ", ";
				scripts.add(line.substring(0, 1).toUpperCase() + line.substring(1));
			}
			System.out.println(logscripts.substring(0, logscripts.length() - 2));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] scriptsarr = new String[scripts.size()];
		scriptsarr = scripts.toArray(scriptsarr);
		scrips.setItems(FXCollections.observableArrayList(scriptsarr));
		scrips.getSelectionModel().selectFirst();
	}

	private void patch() {
		try {
			ArrayList<String> oldindex = new ArrayList<String>();
			File file = getPath();
			Scanner s = new Scanner(file);
			while (s.hasNextLine()) {
				String line = s.nextLine();
				oldindex.add(line);
			}
			s.close();
			for (String h : hosts) {
				oldindex.add(h);
			}

			PrintWriter pw = new PrintWriter(new FileWriter(file));
			for (String st : oldindex) {
				pw.println(st);
			}
			pw.flush();
			pw.close();
			System.out.println("Succsessfully Patched Host File!");
			showDialog("UniversalHostsPatcher", "INFORMATION", AlertType.INFORMATION,
					"Succsessfully Patched Host File!");
			// JOptionPane.showMessageDialog(null, "Succsessfully Patched Host
			// File!", "UniversalHostsPatcher",
			// JOptionPane.OK_OPTION);
		} catch (Exception e) {
			System.err.println("You have not given admin rights for the program!");
			e.printStackTrace();
			showDialog("UniversalHostsPatcher", "Error", AlertType.ERROR,
					"You have not given admin rights for the program!");
			// JOptionPane.showMessageDialog(null, "You have not given admin
			// rights for the program!",
			// "UniversalHostsPatcher", JOptionPane.OK_OPTION);
		}
	}

	private void remove() {
		try {
			boolean add = true;
			ArrayList<String> oldindex = new ArrayList<String>();
			File file = getPath();
			Scanner s = new Scanner(file);
			while (s.hasNextLine()) {
				String line = s.nextLine();
				for (String h : hosts) {
					if (h.equalsIgnoreCase(line)) {
						add = false;
					}
				}
				if (add) {
					oldindex.add(line);
				}
				add = true;
			}
			s.close();

			PrintWriter pw = new PrintWriter(new FileWriter(file));
			for (String st : oldindex) {
				pw.println(st);
			}
			pw.flush();
			pw.close();
			System.out.println("Succsessfully Patched Host File!");
			showDialog("UniversalHostsPatcher", "INFORMATION", AlertType.INFORMATION,
					"Succsessfully Removed Patch of Hosts File!");
			// JOptionPane.showMessageDialog(null, "Succsessfully Removed Patch
			// of Hosts File!", "UniversalHostsPatcher",
			// JOptionPane.OK_OPTION);
		} catch (Exception e) {
			System.err.println("You have not given admin rights for the program!");
			e.printStackTrace();
			showDialog("UniversalHostsPatcher", "Error", AlertType.ERROR,
					"You have not given admin rights for the program!");
			// JOptionPane.showMessageDialog(null, "You have not given admin
			// rights for the program!",
			// "UniversalHostsPatcher", JOptionPane.OK_OPTION);
		}
	}

	private void addIndexHosts(String script) {
		URL link;
		try {
			System.out.println(
					"Connecting to \"https://raw.githubusercontent.com/xRealNeon/UniversalHostsPatcher/master/scripts/"
							+ script.toLowerCase() + "\"");
			link = new URL("https://raw.githubusercontent.com/xRealNeon/UniversalHostsPatcher/master/scripts/"
					+ script.toLowerCase());
			BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream()));
			String line;
			while ((line = in.readLine()) != null) {
				hosts.add(line);
			}
			fehler = true;
		} catch (IOException e) {
			e.printStackTrace();
			showDialog("UniversalHostsPatcher", "Error", AlertType.ERROR,
					"The URL \"https://raw.githubusercontent.com/xRealNeon/UniversalHostsPatcher/master/scripts/"
							+ script.toLowerCase() + "\" was not found!");
			// JOptionPane.showMessageDialog(null,
			// "The URL
			// \"https://raw.githubusercontent.com/xRealNeon/UniversalHostsPatcher/master/scripts/"
			// + script.toLowerCase() + "\" was not found!",
			// "UniversalHostsPatcher", JOptionPane.OK_OPTION);
			fehler = false;
		}
	}

	private void addHosts(String url) {
		URL link;
		try {
			System.out.println("Connecting to \"" + url + "\"");
			link = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream()));
			String line;
			while ((line = in.readLine()) != null) {
				hosts.add(line);
			}
			fehler = true;
		} catch (IOException e) {
			e.printStackTrace();
			showDialog("UniversalHostsPatcher", "Error", AlertType.ERROR, "The URL \"" + url + "\" was not found!");
			// JOptionPane.showMessageDialog(null, "The URL \"" + url + "\" was
			// not found!", "UniversalHostsPatcher",
			// JOptionPane.OK_OPTION);
			fehler = false;
		}
	}

	private File getPath() {
		if (OsCheck.getOperatingSystemType().equals(OSType.Windows)) {
			return new File("C:\\Windows\\System32\\drivers\\etc\\hosts");
		}
		if (OsCheck.getOperatingSystemType().equals(OSType.Linux)) {
			return new File("/etc/hosts");
		}
		if (OsCheck.getOperatingSystemType().equals(OSType.MacOS)) {
			return new File("/private/etc/hosts");
		}
		return new File(UniversalHostsPatcher.customPath);
	}

	private void showDialog(String title, String header, AlertType type, String text) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.getDialogPane().getScene().getWindow();
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(UniversalHostsPatcher.class.getResourceAsStream("icon.png")));
		alert.showAndWait().ifPresent(rs -> {
		});
	}

}
