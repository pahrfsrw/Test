package sim;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import generation.Generation;
import generation.GenerationFactory;
import generation.Individual;
import misc.Observable;
import misc.Observer;

public class GenerationSimulator<T extends Individual<T>> implements Runnable, Observer{
	
	private int generationSize;
	private int currentIndividual = 0;
	private int doneIndividuals = 0;
	
	private static ExecutorService pool = Executors.newCachedThreadPool();
	
	//private final ExecutorService poolTest;
	private boolean useThreadPooling = true;
	
	private Generation<T> gen;
	
	public GenerationSimulator(int generationSize){
		this.generationSize = generationSize;
		//poolTest = Executors.newCachedThreadPool();
	}
	
	public GenerationSimulator(Generation<T> gen){
		this.gen = gen;
		this.generationSize = gen.getSize();
		//poolTest = Executors.newCachedThreadPool();
	}
	
	public void init(){
		gen = GenerationFactory.createGen(this.generationSize, true);
	}
	
	public Generation<T> getGeneration(){
		return this.gen;
	}
	
	@Override
	public void run(){
		if(useThreadPooling){
			runThreadPool();
		} else {
			runConcurrently();
		}
	}
	
	private synchronized void runThreadPool(){
		T individual;
		for(int i = 0; i < generationSize; i++){
			individual = this.getNextIndividual();
			InstanceSimulator<T> is = new InstanceSimulator<T>(individual);
			is.addObserver(this);
			pool.execute(is);
		}
		//pool.shutdown(); // ONLY IF creating a new pool every generation!
		try{
			//pool.awaitTermination(600, TimeUnit.SECONDS);
			this.wait();
		} catch(InterruptedException e){
			System.out.println("Interrupted.");
		}
	}
	
	/* NOT recommended unless running very simple games. This will slow complex games WAY down. */
	private void runConcurrently(){
		T individual;
		for(int i = 0; i < generationSize; i++){
			individual = this.getNextIndividual();
			InstanceSimulator<T> is = new InstanceSimulator<T>(individual);
			is.run();
		}
	}

	@Override
	public synchronized void handleNotification(Observable observable) {
		this.doneIndividuals++;
		if(this.doneIndividuals == this.generationSize){
			this.notify();
		}
		
	}
	
	public synchronized T getNextIndividual(){
		if(currentIndividual >= gen.getSize()){
			return null;
		}
		return gen.getIndividual(currentIndividual++);
	}
	
}
