package evolution;

import java.util.ArrayList;

import generation.Generation;
import generation.GenerationFactory;
import generation.Individual;
import misc.Observable;
import misc.Observer;

public class TournamentSelection<T extends Individual<T>> implements Runnable, Observable {
	
	private ArrayList<Observer> observers;
	private Generation<T> oldGen;
	private Generation<T> newGen;
	private int tournamentSize;
	
	public TournamentSelection(Generation<T> oldGen, Generation<T> newGen, int tournamentSize){
		this.observers = new ArrayList<Observer>();
		this.oldGen = oldGen;
		this.newGen = newGen;
		this.tournamentSize = tournamentSize;
	}

	@Override
	public void run() {
		T indiv1 = tournamentSelection(oldGen);
        T indiv2 = tournamentSelection(oldGen);
        T newIndiv = indiv1.mix(indiv2);
        
        newIndiv.mutate();
        newGen.saveIndividual(newIndiv);
        this.notifyObservers();
	}

	@Override
	public void addObserver(Observer observer) {
		this.observers.add(observer);
		
	}

	@Override
	public void notifyObservers() {
		for(int i = 0; i < this.observers.size(); i++){
			this.observers.get(i).handleNotification(this);
		}
	}
	
	private T tournamentSelection(Generation<T> gen){
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
