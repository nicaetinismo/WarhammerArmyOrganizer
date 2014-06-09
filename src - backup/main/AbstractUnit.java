package main;

public abstract class AbstractUnit {
	
	public String getName(){
		return name;
	}

	protected String name;
	protected String race;
	protected int currentSize;
	protected int maxSize;
	protected int minSize;
	public int cost;
}
