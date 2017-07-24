package generation;

import java.io.Serializable;

public interface Individual<T extends Individual<T>> extends Comparable<T>, Serializable{

	public Object getGenes();
	public Object getAttributes();
	public void setAttributes(Object attributes);
	public T mix(T individual);
	public void initIndividual();
	public void mutate();
	public int compareTo(T individual);
	
}
