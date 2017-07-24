package generation;

import plugins.gameTest.ByteIndividual;
import plugins.shepherd.generation.ShepherdIndividual;

public class GenerationFactory {
	
	private GenerationFactory(){}

	public static <T extends Individual<T>> Generation<T> createGen(int populationSize, boolean initIndividuals){
		if(!Individual.class.isAssignableFrom(ShepherdIndividual.class)){
			System.out.println("Class does not implement Individual.");
		}
		//Generation<ShepherdIndividual> gen = new Generation<ShepherdIndividual>(populationSize, ShepherdIndividual.class);
		/*for(int i = 0; i < populationSize; i++){
			ShepherdIndividual indiv = new ShepherdIndividual();
			indiv.initIndividual();
			gen.addIndividual(i, indiv);
		}*/
		Generation<ByteIndividual> gen = new Generation<ByteIndividual>(populationSize, ByteIndividual.class, initIndividuals);
		return (Generation<T>) gen;
	}
}
