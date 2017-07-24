package plugins.shepherd.sim;

import java.util.Iterator;
import java.util.List;

import misc.Observer;
import sim.Simulation;

public class Game implements Simulation {
	
	private List<Observer> observers;
	
	public void run(){
		
	}
	
	public void setInput(Object input){
		
	}
	
	public Object getOutput(){
		return new Object();
	}
	
	public void addObserver(Observer observer){
		observers.add(observer);
	}
	
	public void removeObserver(Observer observer){
		observers.remove(observer);
	}
	
	public void notifyObservers(){
		Iterator<Observer> i = observers.iterator();
		while(i.hasNext()){
			i.next().handleNotification(this);
		}
	}
	
}
