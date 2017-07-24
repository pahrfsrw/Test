package evolution;

import generation.Generation;
import generation.GenerationFactory;
import generation.Individual;

public class TournamentManager {
	
	private static final TournamentManager INSTANCE = new TournamentManager();
	
	private static final boolean elitism = false;
	private static final int defaultElitismOffset = 1;
	private static final int tournamentSize = 10;
	
	private TournamentManager(){}
	public static TournamentManager getInstance(){
		return INSTANCE;
	}
	
	public <T extends Individual<T>> Generation<T> holdTournament(Generation<T> oldGen){
		
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

}
