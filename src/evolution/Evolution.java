package evolution;

import generation.Individual;

public class Evolution {

    public static <T extends Individual<T>> T crossover(T indiv1, T indiv2) {
    	return indiv1.mix(indiv2);
    }
    
    public static <T extends Individual<T>> void mutate(Individual<T> indiv){
    	indiv.mutate();
    }

}
