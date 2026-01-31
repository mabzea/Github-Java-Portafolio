/**
 *
 * 
 */
public class VideoStoreLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Test a Video.
		Video vid1 = new Video();
		vid1.setTitle("The Godfather");

		vid1.addRating(3);
		vid1.addRating(2);
		vid1.addRating(5);
		vid1.getRating();

		String s1 = String.format("%s: %s", vid1.getTitle(), vid1.getRating());
		System.out.println(s1);

		vid1.checkOut();
		getStatus(vid1);
		vid1.returnToStore();
		getStatus(vid1);

		// Test a VideoStore.
		videoStore vs = new videoStore();

		vs.addVideo("Battleship Potemkin");
		vs.addVideo("The Godfather");
		vs.addVideo("City of Angels");

		// Add user ratings for 'Battleship Potemkin'.
		vs.rateVideo(0, 5);
		vs.rateVideo(0, 4);
		vs.rateVideo(0, 4);
		vs.rateVideo(0, 3);
		vs.rateVideo(0, 5);
		vs.rateVideo(0, 4);
		vs.rateVideo(0, 4);
		vs.rateVideo(0, 3);
		System.out.println(vs.getVideoByIndex(3));

		vs.checkOutVideo(0);
		vs.checkOutVideo(2);

		System.out.println("Average Rating for video #0: "
				+ vs.ratingForVideo(0));
		
		vs.listInventory();
	}

	public static void getStatus(Video v) {
		String title = v.getTitle();
		if (v.isCheckedOut()) {
			String s = String.format("'%s' is checked out.", title);
			System.out.println(s);
		} else {
			String s = String.format("'%s' is on the shelves.", title);
			System.out.println(s);

		}
	}

}