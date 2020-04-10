package application;

public abstract class Launchable {
	public String name, location, url;
	public Launchable(String name, String location) {
		this.name = name;
		this.location = location;
	}
	public Launchable(String name, String location, String url) {
		this.name = name;
		this.location = location;
		this.url = url;
	}
	
	public abstract void launchApplication();
	public String getName() { return this.name; }
	public String getLocation() { return this.location; } 
	public String getURL() { return this.url; }
}
