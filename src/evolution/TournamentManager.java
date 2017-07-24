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
		//this.pool = Executors.newCachedThreadPool();
	}
	
	public <T extends Individual<T>> Generation<T> holdTournament(Generation<T> oldGen){
		this.generationSize = oldGen.getSize();
		this.completedIndividuals = 0;
		
		Generation<T> newGen = null;
		if(useThreadPooling){
			newGen = runThreadPool(oldGen);
		} else{
			newGen = runConcurrently(oldGen);
		}
		return newGen;
		
	}
	
	private synchronized <T extends Individual<T>> Generation<T> runThreadPool(Generation<T> oldGen){
		Generation<T> newGen = GenerationFactory.createGen(oldGen.getSize(), false);
		
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
		
		for(int i = elitismOffset; i < oldGen.getSize(); i++){
			TournamentSelection<T> ts = new TournamentSelection<T>(oldGen, newGen, tournamentSize);
			ts.addObserver(this);
			pool.execute(ts);
		}
		
		//pool.shutdown(); // ONLY IF creating a new pool every generation!
		try{
			//pool.awaitTermination(600, TimeUnit.SECONDS);
			this.wait();
		} catch(InterruptedException e){
			System.out.println("Interrupted.");
		}
		
		// Mutate population
        	// Population mutaded inside TournamentSelection.
		return newGen;
	}
	
	private <T extends Individual<T>> Generation<T> runConcurrently(Generation<T> oldGen){
		Generation<T> newGen = GenerationFactory.createGen(oldGen.getSize(), false);
		
		int elitismOffset;
        if (elitism) {
            elitismOffset = defaultElitismOffset;
        } else {
            elitismOffset = 0;
        }
        
        if (elitism) {
        	for(int i = 0; i < elitismOffset; i++){
        		newGen.saveIndividual(i, oldGen.getFittest());
        	}
        }
		
		// Breed population
 		for (int i = elitismOffset; i < oldGen.getSize(); i++) {
         	 T indiv1 = tournamentSelection(oldGen);
             T indiv2 = tournamentSelection(oldGen);
             T newIndiv = Evolution.crossover(indiv1, indiv2);
             newGen.saveIndividual(i, newIndiv);
         }
 		
 		// Mutate population
         for (int i = elitismOffset; i < oldGen.getSize(); i++) {
             Evolution.mutate(newGen.getIndividual(i));
         }
         
         return newGen;
	}
	
	private static <T extends Individual<T>> T tournamentSelection(Generation<T> gen){
		Generation<T> tournament = GenerationFactory.createGen(tournamentSize, false);
		for(int i = 0; i < tournamentSize; i++){
            int randomId = (int) (Math.random() * gen.getSize());
            tournament.saveIndividual(i, gen.getIndividual(randomId));
		}
		
		// Get the fittest
        T fittest = tournament.getFittest();
        return fittest;
	}

	@Override
	public synchronized void handleNotification(Observable observable) {
		completedIndividuals++;
		if(this.generationSize == completedIndividuals){
			this.notify();
		}
	}

}
