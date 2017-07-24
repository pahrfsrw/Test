package evolution;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import generation.Generation;
import generation.GenerationFactory;
import generation.Individual;
import misc.Observable;
import misc.Observer;

public class TournamentManager implements Observer {
	
	private static final boolean elitism = false;
	private static final int defaultElitismOffset = 1;
	private static final int tournamentSize = 10;
	
	private boolean useThreadPooling = false;
	private static final ExecutorService pool = Executors.newCachedThreadPool();
	
	private int generationSize;
	private int completedIndividuals;
	
	public TournamentManager(){
	}
	
	public <T extends Individual<T>> Generation<T> holdTournament(Generation<T> oldGen){
		Generation<T> newGen = GenerationFactory.createGen(oldGen.getSize(), false);
		
		this.generationSize = oldGen.getSize();
		this.completedIndividuals = 0;
		
		int elitismOffset;
        if (elitism) {
            elitismOffset = defaultElitismOffset;
        } else {
            elitismOffset = 0;
        }
        
        if (elitism) {
        	for(int i = 0; i < elitismOffset; i++){
        		newGen.saveIndividual(oldGen.getFittest());
        	}
        }
        
    	if(useThreadPooling){
			runThreadPool(oldGen, newGen, elitismOffset);
		} else{
			runConcurrently(oldGen, newGen, elitismOffset);
		}
    		
        return newGen;
		
	}
	
	private synchronized <T extends Individual<T>> void runThreadPool(Generation<T> oldGen, Generation<T> newGen, int elitismOffset){
		
		for(int i = elitismOffset; i < oldGen.getSize(); i++){
			TournamentSelection<T> ts = new TournamentSelection<T>(oldGen, newGen, tournamentSize);
			ts.addObserver(this);
			pool.execute(ts);
		}
		
		try{
			this.wait();
		} catch(InterruptedException e){
			System.out.println("Interrupted.");
		}

	}
	
	private <T extends Individual<T>> void runConcurrently(Generation<T> oldGen, Generation<T> newGen, int elitismOffset){
		for(int i = elitismOffset; i < oldGen.getSize(); i++){
         	 TournamentSelection<T> ts = new TournamentSelection<T>(oldGen, newGen, tournamentSize);
         	 ts.run();
         }
	}

	@Override
	public synchronized void handleNotification(Observable observable) {
		completedIndividuals++;
		if(this.generationSize == completedIndividuals){
			this.notify();
		}
	}

}
