/**
 * VideoStore class.
 * 
 * 
 * @version 1.0
 */

public class videoStore {
	Video[] catalogue = new Video[10];
	int videoCount;

	public void addVideo(String title) {
		if (videoCount > 10) {
			System.out.println("Sorry, the shelves are full");
		} else {
			catalogue[videoCount] = new Video();
			catalogue[videoCount].setTitle(title);
			videoCount++;
		}
	}

	public void checkOutVideo(int video) {
		catalogue[video].checkOut();
	}

	public void returnVideo(int video) {
		catalogue[video].returnToStore();
	}

	public void rateVideo(int video, int rating) {
		catalogue[video].addRating(rating);
	}

	public double ratingForVideo(int video) {
		return catalogue[video].getRating();
	}

	public int getVideoByTitle(String title) {
		for (int i = 0; i < videoCount; i++) {
			if (title.equals(catalogue[i].getTitle()))
				return i;
		}
		return 0;
	}
	
	public String getVideoByIndex(int index) {
		if (catalogue[index] == null){
			return "Video #" + index + " not found.";
		} else {
		return catalogue[index].getTitle();
		}
	}

	public void listInventory() {
		for (int i = 0; i < videoCount; i++) {
			System.out.println(i + ": " + catalogue[i].getTitle());
			System.out.println("\tRating:" + ratingForVideo(i));
			if (catalogue[i].isCheckedOut()) {
				System.out.println("\tChecked out: Yes");
			} else {
				System.out.println("	Checked out: No");
			}
		}
	}
}