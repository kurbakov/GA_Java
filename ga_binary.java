package ga;

import java.util.Random;
import java.util.Arrays;


public class ga_binary {
	
	private int length;
	private int population;
	private double mutation_prob;
	private int crossover_type;
	private double survive;
	
	public int[] genome;
	public double[] scores;
	
	private int[] best_genome;
	private double best_score;

	
	Random rand = new Random();

	public ga_binary(
			int len, 			// length of the 1 genome
			int pop, 			// number of genoms to evaluate
			double m_prob,		// mutation probability
			int cross_type,		// type of the crossover
			double surv			// percentage of population that goes from generation i to the generation i+1
								) 
	{
		this.length 		= len;
		this.population 	= pop;
		this.mutation_prob 	= m_prob;
		this.crossover_type = cross_type;
		this.survive 		= surv;
		
		this.genome = new int[len * pop];
		this.scores = new double[pop];
		System.out.println("Class is builded successful!...");
	}
	
	
	/*comment!*/
	public void buid_genome()
	{
		for(int i = 0; i < length * population; i++)
		{
			genome[i] = rand.nextInt(2);
		}		
	}
	
	
	/*comment!*/
	public void best_score()
	{
		double tmp = scores[0] ;
		
		for(int i=1; i< scores.length; i++)
		{
			if(tmp<scores[i])
			{
				tmp = scores[i];
			}
		}
			
		best_score = tmp;
	}
	
	
	/*comment!*/
	public void best_genome()
	{
		int best_gen_int = -1;

		for(int i =0; i < scores.length; i++)
			if(scores[i] == best_score){best_gen_int = i;}
				
		int from, to;
		from = best_gen_int*length;
		to = best_gen_int*length + length;
		
		best_genome =  Arrays.copyOfRange(genome, from, to);
	}
	
	
	/*comment!*/
	public void selection(){} // TO DO!!!!!!
	
	
	/*comment!*/
	public void crossover(int i){} // TO DO!!!!!! int i = crossover type, 1 - 1 point, 2 - 2 points, etc...
	
	
	/*comment!*/
	public void mutation()
	{
		
		for(int i=0; i< genome.length; i++ )
		{
			if(rand.nextDouble() <= mutation_prob )
			{
				genome[i] ^= 1;
			}
		}
	} 
	
	
	/*comment!*/
	public double get_best_score()
	{
		return best_score;
	}
	
	
	/*comment!*/
	public int[] get_best_genome() 
	{  
		return best_genome;
	}
	
	
	/*comment!*/
	public void print_results()
	{
		System.out.println("The best score is: " + best_score);
		String str, str1, str2;
		str1 = "The scores are: ";
		
		for(int k =0; k<scores.length; k++)
			str1 += " "+scores[k];
		
		System.out.println(str1);
		
		str = "The best genome:";
		
		for(int j =0; j<best_genome.length; j++)
			str += " "+best_genome[j];
		
		System.out.println(str);
		
		str2 = "The best genome:";
		
		for(int l =0; l<genome.length; l++)
			str2 += " "+genome[l];
		
		System.out.println(str2);
	}

}
