/**
 * Model of a Video for the VideoStore scenario
 * 
 * 
 * @version 2.0
 */
//to user array to store max and min values. 
import java.util.ArrayList; 
import java.util.Collections;

public class Video {

	private String title;
	private Boolean checkedOut = false;
	private double averageRating;
	private ArrayList<Integer> ratings = new ArrayList<>();
	
	
	/**
	 * Sets the title.
	 */
	public void setTitle(String iTitle) {
		title = iTitle;
	}

	/**
	 * Gets the title.
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * Adds a rating.
	 * 
	 * @param rate
	 *            The user rating for this video.
	 */
	public void addRating(int rate) {
		ratings.add(rate); // store rating
		calculateAverage();// Recalculate averages using new policy
	}
	
	/**
	 * Recalculate average applying the min/max discard rule. 
	 */
	
	private void calculateAverage() 
	{
		int count = ratings.size();
		if (count == 0)
		{
			averageRating = 0.0;
			return;
			
		}
		
		ArrayList<Integer> tempRatings = new ArrayList<>(ratings); // Make a copy so we don't destroy the original history 
		
		if (count > 4)
		{
			//remove one instance of the highest and one instance of the lowest
			tempRatings.remove(Collections.max(tempRatings)); //removes max
			tempRatings.remove(Collections.min(tempRatings)); //removes min
		}
		
		int sum = 0;
		for (int r: tempRatings)
		{
			sum+= r;
		}
		
		averageRating = (double) sum / tempRatings.size();
	}
	
	

	/**
	 * Gets a rating.
	 */
	public double getRating() {
		return averageRating;
	}


	/**
	 * Sets video checked out
	 */
	public void checkOut() {
		checkedOut = true;
	}

	/**
	 * Sets video not checked out.
	 */
	public void returnToStore() {
		checkedOut = false;
	}

	/**
	 * Gets checkedOut value
	 */
	public Boolean isCheckedOut() {
		return checkedOut;
	}
}