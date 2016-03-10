package ga;

import java.util.Arrays;

public class main_class {

	public static void main(String[] args) {
		System.out.println("Welcome!...");
		
		int pop 		= 10;
		int len 		= 4;
		int gen 		= 25;
		double m_prob 	= .01;
		int cross_type 	= 1;
		double survive	= .2; 
		
		/*comment!!!*/
		ga_binary ga_bin = new ga_binary(len, pop , m_prob , cross_type, survive);
		
		/*comment!!!*/
		ga_bin.buid_genome();
		
		main_class m = new main_class();
		
		for(int k1 = 0; k1<gen; k1++)
		{
		
			/* Here we run k1-times (number of generations) */
			
			for(int k = 0; k < pop ; k++)
			{
				int from 	= k*len;
				int to 		= k*len + len;
				
				int[] input =  Arrays.copyOfRange(ga_bin.genome, from, to) ;
				ga_bin.scores[k] = (double) m.evaluation_function( input );									
			}
			
			ga_bin.best_score();
			ga_bin.best_genome();
			//ga_bin.selection();
			ga_bin.mutation();
			//ga_bin.crossover(1);
			
		}
		
		ga_bin.print_results();

	}

	/*comment!!!*/
	public int evaluation_function(int[] genome)
	{
		int res = 0;
		for (int i=0; i<genome.length; i++)
		{
			res += (i+1) * genome[i];
		}
			
		return res;
	}
	
}
