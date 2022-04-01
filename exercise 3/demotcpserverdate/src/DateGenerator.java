import java.util.Date;

/**
 * This class generates date.
 * 
 * @author emalianakasmuri
 *
 */
public class DateGenerator {

	
	/**
	 * This method generates current date.
	 * 
	 * @return current date
	 */
	public String getCurrentDate() {
		
		//String currentDate = new Date().toString();
		
		//return currentDate;

		String text = "Hello World!";
		String length = String.valueOf(count(text));
		
		return length;
	}

	public static int count(String text) {
		if(text == null || text.isEmpty()) {
			return 0;
		}
		String[] words = text.split("\\s+");
		return words.length;
	}
	
}
