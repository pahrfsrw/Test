package plugins.gameTest;

import misc.Observer;
import sim.Simulation;

public class ByteGame implements Simulation{
	
	ByteIndividual input;
	boolean hasOutput = false;
	Double output = -1.0;

	@Override
	public void run() {
		try{
			Thread.sleep(10);
		} catch(InterruptedException e){
			System.out.println("Interrupted.");
		}
		byte[] genes = input.getGenes();
		int ones = 0;
		for(int i = 0; i < genes.length; i++){
			if(genes[i] == 1){
				ones++;
			}
		}
		output = (double)ones/(double)genes.length;
		hasOutput = true;
	}

	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInput(Object o) {
		this.input = (ByteIndividual) o;
	}

	@Override
	public Object getOutput() {
		if(this.hasOutput){
			return output;
		}
		else{
			throw new IllegalStateException("Error: This game has not completed its run.");
		}
	}

}
