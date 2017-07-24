package generation;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Generation<T extends Individual<T>> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final String typeParameterClassName;
	private T[] individuals;
	
	public Generation(int populationSize, Class<T> clazz, boolean initIndividuals){
		this.typeParameterClassName = clazz.getName();
		this.individuals = (T[]) Array.newInstance(clazz, populationSize);
		try{
			for(int i = 0; i < populationSize; i++){
				this.individuals[i] = clazz.newInstance();
				if(initIndividuals)
					this.individuals[i].initIndividual();
			}
		} catch(IllegalAccessException|InstantiationException e){
			System.out.println("Gould not create generation of type " + clazz.getName());
			e.printStackTrace();
		}
	};
	
	public synchronized T getIndividual(int index){
		return individuals[index];
	}
	
	public void sort(){
		Arrays.sort(individuals);
	}
	
	public T getFittest(){
		this.sort();
		return individuals[0];
	}
	
	public int getSize(){
		return this.individuals.length;
	}

	public String toString(){
		return  "Generation contains individuals of class: " + this.typeParameterClassName + "\n" + 
				"Generation size: " + this.getSize() + "";
			   
	}
	
	public void saveIndividual(int index, T indiv){
		individuals[index] = indiv;
	}
}
