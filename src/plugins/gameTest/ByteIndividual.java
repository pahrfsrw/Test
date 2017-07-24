package plugins.gameTest;

import generation.Individual;

public class ByteIndividual implements Individual<ByteIndividual> {
	
	/**
	 * 
	 */
	private static double defaultMutationRate = 0.001;
	private static int defaultGeneLength = 1000;
	private static final long serialVersionUID = 1L;
	private byte[] genes;
	private Double attributes;
	
	public ByteIndividual(){
		this.genes = new byte[defaultGeneLength];			
	}

	@Override
	public byte[] getGenes() {
		return genes;
	}

	@Override
	public Double getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(Object attributes) {
		// TODO Auto-generated method stub
		this.attributes = (Double) attributes;
	}

	@Override
	public ByteIndividual mix(ByteIndividual individual) {
		byte[] hisGenes = this.getGenes();
		byte[] herGenes = individual.getGenes();
		
		ByteIndividual child = new ByteIndividual();
		
		for(int i = 0; i < hisGenes.length; i++){
			double r = Math.random();
			if(r < 0.5){
				child.genes[i] = herGenes[i];
			} else {
				child.genes[i] = hisGenes[i];
			}
		}
		
		return child;
	}

	@Override
	public void initIndividual() {
		for(int i = 0; i < this.genes.length; i++){
			byte temp = (byte) Math.floor(2*Math.random());
			this.genes[i] = temp;
		}
		
	}

	@Override
	public void mutate() {
		double r;
		for(int i = 0; i < this.genes.length; i++){
			r = Math.random();
			if(r < defaultMutationRate){
				this.genes[i] = randomGene();
			}
		}
	}

	@Override
	public int compareTo(ByteIndividual individual) {
		if(individual.getAttributes() < this.getAttributes()){
			return -1;
		} else if(individual.getAttributes() > this.getAttributes()){
			return 1;
		}
		return 0;
	}
	
	private byte randomGene(){
		return (byte) Math.floor(2*Math.random());
	}
	
	public String toString(){
		return Double.toString(this.attributes);
	}

}
