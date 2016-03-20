package ga;

import java.util.Random;
import java.util.Arrays;
import java.util.HashMap;


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
	
	
	/*
	 * build_genome() is the initial function
	 * to create the genome with random 0,1 values. 
	 * Important to use it in the beginning of the GA!
	 */
	public void buid_genome()
	{
		for(int i = 0; i < length * population; i++)
		{
			genome[i] = rand.nextInt(2);
		}		
	}
	
	
	/*
	 * Function finds the best score that was achieved 
	 * by some genome in the population 
	 */
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
	
	
	/*
	 * Very similar to the best score.
	 * In this case we are looking for the genome with the best score
	 */
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
	
	
	/*
	 * Selection is used to define best genomes and push them to the 
	 * next generation! Also based on the best genomes we will 
	 * build next generation in function crossover().
	 */
	public void selection()
	{
		int next_pop = (int) (population * survive);
		
		HashMap<Double, int[]> current_pop = new HashMap<Double, int[]>();
		for(int k = 0; k < scores.length; k++)
		{
			int from, to;
			from = k*length;
			to = k*length + length;
			
			current_pop.put(scores[k], Arrays.copyOfRange(genome, from, to) );
		}
		
		Arrays.sort(scores);
		
		for(int i = 0; i<next_pop; i++)
		{
			for(int j=0; j<length; j++)
			{
				genome[i*length + j] = current_pop.get(scores[i])[j];
			}
		}
	} 
	
	
	/*
	 * Crossover takes two random genomes and build the new one.
	 * Important to know, that random genomes can be taken only 
	 * from the genomes who will be pushed to the new generation!
	 */
	public void crossover() 
	{
		int next_pop = (int) (population * survive);
		
		int parent_a_int, parent_b_int;
		int[] parent_a, parent_b, kid, crossover_points;
		
		for(int kk = next_pop; kk<population; kk++ )
		{

			parent_a_int = rand.nextInt( (int) (population * survive) );
			parent_b_int = rand.nextInt( (int) (population * survive) );
			
			parent_a = Arrays.copyOfRange(genome, parent_a_int*length, parent_a_int*length + length);
			parent_b = Arrays.copyOfRange(genome, parent_b_int*length, parent_b_int*length + length);
			
			crossover_points = new int[crossover_type];	
			for(int i=0; i<crossover_type; i++)
			{
				crossover_points[i]=rand.nextInt( rand.nextInt( length ) );
			}
			Arrays.sort(crossover_points);
			
			
			kid = new int[length];
			int pointer = 0;
					
			for(int j=0; j<length -1; j++ )
			{
				if(j == crossover_points[pointer] && pointer < crossover_points.length )
					pointer +=1;
				
				/*
				 * The idea is in the following:
				 * Imagine we have the following crossover points: 1,3,6
				 * In this case the initial value of the pointer is set to 0 
				 * And we assign values of the kid genome from parent A.
				 * 
				 * When we rich value 1, we add 1 to pointer and assign values from parent B.
				 * 
				 * Then by reaching value 3 we add 1 to pointer and assign values from parent A
				 * again.
				 * 
				 * In this case we can create as many crossover pointers as we want
				 * and it will not influence the speed of the algorithm.
				 */
				
				if( pointer % 2 == 0 )
				{
					kid[j] = parent_a[j];
				}
				else
				{
					kid[j] = parent_b[j];
				}			
			}
			
			for(int j = 0; j<length; j++)
			{
				genome[next_pop + kk*length +j ] = kid[j];
			}
		
		}
		
	}
	
	/*
	 * Mutation of the genome.
	 * We take the probability of the mutation and if the value lower then probability,
	 * we switch the value from 0 to 1 and from 1 to 0 respectively.
	 */
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
	
	
	/*
	 * returns the best score of the population
	 */
	public double get_best_score()
	{
		return best_score;
	}

	
	/*
	 * returns the best genome of the population
	 */
	public int[] get_best_genome() 
	{  
		return best_genome;
	}
	
	
	/*
	 * just used for fun and to see whether the functions work correctly.
	 * You can ignore this function!
	 */
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
