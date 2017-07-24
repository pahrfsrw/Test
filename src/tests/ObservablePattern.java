package tests;

import java.util.ArrayList;

import misc.Observable;
import misc.Observer;

public class ObservablePattern implements Observer {
	
	private int numberOfObservables;
	private RunnableObservable[] observables;
	private int timesNotified = 0;
	
	public final boolean useThreads = true;
	
	public ObservablePattern(int numberOfObservables){
		this.numberOfObservables = numberOfObservables;
		this.observables = new RunnableObservable[this.numberOfObservables];
		for(int i = 0; i < this.numberOfObservables; i++){
			RunnableObservable ro = new MyRunnableObservable();
			ro.addObserver(this);
			this.observables[i] = ro;
		}
	}
	
	@Override
	public synchronized void handleNotification(Observable observable) {
		this.timesNotified++;
		System.out.println("Times notified: " + Integer.toString(this.timesNotified));
	}
	
	public int getNumberOfObservables(){
		return this.numberOfObservables;
	}
	
	public RunnableObservable getObservable(int index){
		return this.observables[index];
	}
	
	public static void main(String[] args){
		ObservablePattern op = new ObservablePattern(50);
		for(int i = 0; i < op.getNumberOfObservables(); i++){
			if(op.useThreads){
				Thread t = new Thread(op.getObservable(i));
				t.start();
			} else {
				op.getObservable(i).run();
			}
		}
	}
	
	private class MyRunnableObservable implements RunnableObservable{
		
		private ArrayList<Observer> observers;
		
		public MyRunnableObservable(){
			this.observers = new ArrayList<Observer>();
		}

		@Override
		public void run() {
			System.out.println("Running thread...");
			try{
				Thread.sleep(20);
			} catch(InterruptedException e){
				System.out.println("Interrupted.");
			}
			notifyObservers();
		}

		@Override
		public void addObserver(Observer observer) {
			this.observers.add(observer);
		}

		@Override
		public void notifyObservers() {
			for(int i = 0; i < observers.size(); i++){
				observers.get(i).handleNotification(this);
			}
			
		}
		
	}

}
