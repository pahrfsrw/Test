package generation;

import java.lang.reflect.Array;

import plugins.shepherd.generation.ShepherdIndividual;

public class Test {
	public static void main(String[] args){
		int i = 10;
		Math.abs(i);
		System.out.println(ShepherdIndividual.class.getName());
		Generation<ShepherdIndividual> gen = GenerationFactory.createGen(2, true);
		i = 10;
		
		String[] s = (String[]) Array.newInstance(String.class, 10);
	}
}
