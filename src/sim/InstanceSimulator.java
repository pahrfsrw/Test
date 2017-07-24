package sim;

import java.util.ArrayList;

import generation.Individual;
import misc.Observable;
import misc.Observer;

public class InstanceSimulator<T extends Individual<T>> implements Runnable, Observable {
	
	private T individual;
	private Simulation sim;
	private ArrayList<Observer> observers;
	
	public InstanceSimulator(T individual){
		this.individual = individual;
		this.observers = new ArrayList<Observer>();
	}
	
	public void run(){
		sim = SimulationFactory.createSim();
		sim.setInput(individual);
		sim.run();
		Object output = sim.getOutput();
		individual.setAttributes(output);
		
		this.tearDown();
	}
	
	public void tearDown(){
		this.individual = null;
		this.sim = null;
	}

	@Override
	public void addObserver(Observer observer) {
		this.observers.add(observer);
		
	}

	@Override
	public void notifyObservers() {
		for(int i = 0; i < this.observers.size(); i++){
			this.observers.get(i).notify();
		}
	}

}
