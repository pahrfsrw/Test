package sim;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import generation.Generation;
import generation.GenerationFactory;
import generation.Individual;
import misc.Observable;
import misc.Observer;

public class GenerationSimulator<T extends Individual<T>> implements Runnable, Observer{
	
	private int generationSize;
	private int currentIndividual = 0;
	
	//private final ExecutorService pool;
	
	private Generation<T> gen;
	private Simulation sim;
	
	public GenerationSimulator(int generationSize){
		this.generationSize = generationSize;
		//pool = Executors.newCachedThreadPool();
	}
	
	public GenerationSimulator(Generation<T> gen){
		this.gen = gen;
		this.generationSize = gen.getSize();
		//pool = Executors.newCachedThreadPool();
	}
	
	public void init(){
		gen = GenerationFactory.createGen(this.generationSize, true);
	}
	
	public Generation<T> getGeneration(){
		return this.gen;
	}
	
	@Override
	public void run(){
		T individual;
		for(int i = 0; i < generationSize; i++){
			sim = SimulationFactory.createSim();
			individual = getNextIndividual();
			sim.setInput(individual);
			sim.run();
			Object output = sim.getOutput();
			individual.setAttributes(output);
			
			sim = null;
			individual = null;
		}
	}

	@Override
	public void handleNotification(Observable observable) {
		// TODO Auto-generated method stub
		
	}
	
	public synchronized T getNextIndividual(){
		if(currentIndividual >= gen.getSize()){
			return null;
		}
		return gen.getIndividual(currentIndividual++);
	}
	
}
