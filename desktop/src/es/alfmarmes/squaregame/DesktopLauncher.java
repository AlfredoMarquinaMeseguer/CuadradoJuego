package es.alfmarmes.squaregame;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import es.alfmarmes.squaregame.SquareGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Cuadrado Saltar\u00EDn con M\u00FAsica de Linkin Park");
		new Lwjgl3Application(new SquareGame(), config);
	}
}
