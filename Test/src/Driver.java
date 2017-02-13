
public class Driver {
	public static void main(String[] args) {
		double prob = 0;
		int i = 1;
		
		do
		{
			prob = 1;
			i++;
			
			for(int j = 1; j < i; j++)
			{
				prob *= (365 - j) / 365.0; 
			}
			
			prob = 1 - prob;
			
			System.out.println(prob);
			
		} while(prob < .5);
	}
}
