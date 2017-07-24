package plugins.shepherd.generation;

import generation.Individual;

public class ShepherdIndividual implements Individual<ShepherdIndividual> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte[] genes;
	private Object attributes;	
	
	private static int defaultGeneLength = 4000000;
	
	public ShepherdIndividual(){
		this.initGenes();
	}

	@Override
	public byte[] getGenes(){
		return genes;
	}
	
	@Override
	public Object getAttributes(){
		return attributes;
	}
	
	public void setAttributes(Object attributes){
		this.attributes = attributes;
	}

	@Override
	public ShepherdIndividual mix(ShepherdIndividual individual) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mutate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initIndividual() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(ShepherdIndividual indiv) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private void initGenes(){
		this.genes = new byte[defaultGeneLength];
		for(int i = 0; i < this.genes.length; i++){
			byte temp = (byte) Math.floor(3*Math.random());
			this.genes[i] = temp;
		}
	}
	
	public String toString(){
		if(this.attributes == null){
			return "This individual has not yet had its attributes determined.";
		} else{
			return this.attributes.toString();
		}
	}
}
