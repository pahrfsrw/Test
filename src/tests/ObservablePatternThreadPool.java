package tests;

import java.util.ArrayList;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import misc.Observable;
import misc.Observer;

public class ObservablePatternThreadPool implements Observer {
	
	private int numberOfObservables;
	private RunnableObservable[] observables;
	private int timesNotified = 0;
	private ExecutorService pool;
	private CompletionService completion;
	
	public ObservablePatternThreadPool(int numberOfObservables){
		this.pool = Executors.newCachedThreadPool();
		this.completion = new ExecutorCompletionService(this.pool);
		
		this.numberOfObservables = numberOfObservables;
		this.observables = new RunnableObservable[this.numberOfObservables];
		for(int i = 0; i < this.numberOfObservables; i++){
			RunnableObservable ro = new MyRunnableObservable();
			ro.addObserver(this);
			this.observables[i] = ro;
		}
	}
	
	@Override
	public synchronized void handleNotification(Observable observable){
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
		ObservablePatternThreadPool op = new ObservablePatternThreadPool(50);
		for(int i = 0; i < op.getNumberOfObservables(); i++){
			RunnableObservable r = op.getObservable(i);
			op.pool.execute(r);
		}
		op.pool.shutdown();
		try{
			op.pool.awaitTermination(600, TimeUnit.SECONDS);
		} catch(InterruptedException e){
			System.out.println("Interrupted.");
		}
		System.out.println("Done.");
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
				Thread.sleep(2000);
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
