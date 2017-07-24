package sim;

import misc.Observable;

public interface Simulation extends Runnable, Observable {
	
	public void setInput(Object o);
	public Object getOutput();

}
