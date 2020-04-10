package application;

import java.io.IOException;

public class Bookmark extends Launchable {

	public Bookmark(String name, String location, String url) {
		super(name, location, url);
	}

	@Override
	public void launchApplication() {
		Runtime run = Runtime.getRuntime();
		String[] s = new String[] { location, url };
		try { run.exec(s); } 
		catch (IOException e) {}
	}

}
