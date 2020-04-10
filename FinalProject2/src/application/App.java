package application;

import java.io.IOException;

public class App extends Launchable {
	
	public App(String name, String location) {
		super(name,location);
	}

	@Override
	public void launchApplication() {
		Runtime run = Runtime.getRuntime();
		String[] s = new String[] { location };
		try { run.exec(s); } 
		catch (IOException e) {}
	}
}
