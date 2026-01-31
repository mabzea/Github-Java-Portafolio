public class StarPattern {
	
	public static void main (String[] args)
	{
		int rows = 10; //Constant for how many rows it can be
		
		// Pattern (a)
		System.out.println("(a)");
		for (int i= 1; i<rows; i++)
		{
			for (int j = 1; j <= i; j++)
			{
				System.out.print("*");
			}
			System.out.println();
		}
		System.out.println();
		
		//Pattern (b)
		System.out.println("(b)");
		for (int i = rows; i >= 1; i--)
		{
			for (int j = 1; j <= i; j++)
			{
				System.out.print("*");
			}
			System.out.println();
		}
		System.out.println();
		
		
		//Pattern (c)
		System.out.println("(c)");
		for (int i = rows; i >= 1; i--) {
            for (int j = 1; j <= rows - i; j++) {
                System.out.print(' ');
            }
            for (int j = 1; j <= i; j++) {
                System.out.print('*');
            }
            System.out.println();
        }
        System.out.println();
        
        //Pattern (d)
        System.out.println("(d)");
        for (int i =1; i <+ rows; i++)
        {
        	for (int j = 1; j <+ rows - i; j++)
        	{
        		System.out.print(" ");
        	}
        	for (int j = 1; j <= i; j++)
        	{
        		System.out.print("*");
        	}
        	System.out.println();
        }

	}
				
}
