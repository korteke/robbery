package net.kvak.SahkoBot;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

public class SahkoBot {
    
	// Espoo
	private final static double PRICE_ESP = 0.0271;
	private final static double ROBBERY_PRICE_ESP = 0.0314;
	private final static double ROBBERY_MONTHLY_ESP = 5.90 - 2.95;
	// Muu maa
	private final static double PRICE = 0.0295;
	private final static double ROBBERY_PRICE = 0.0398;
	private final static double ROBBERY_MONTHLY = 22.60 - 15.67;
	private final static int MIN_KWH = 1000;
	private final static int MAX_KWH = 30000;

	public static void main(String[] args) throws TwitterException {
		final int kwh = ThreadLocalRandom.current().nextInt(MIN_KWH, MAX_KWH + 1);
		Twitter twitter = TwitterFactory.getSingleton();
		
        try {
    		Status status = twitter.updateStatus("Yleissiirron hinnankorotus vuosikulutuksella: " + kwh + "kWh on Espoossa " + calculateRobbery(kwh, ROBBERY_PRICE_ESP, PRICE_ESP, ROBBERY_MONTHLY_ESP)
    		+ "€/vuosi. Muualla suomessa "+calculateRobbery(kwh, ROBBERY_PRICE, PRICE, ROBBERY_MONTHLY)+"€/vuosi.");
    		System.out.println("Posted tweet: " +status.getText() + " ID: " + status.getId());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	static double robberyRound(double d) {
		DecimalFormat rr = new DecimalFormat("#.##");
		return Double.valueOf(rr.format(d));
	}

	static double calculateRobbery(int kwh, double robbery, double price, double kyla) {
		return robberyRound((kwh) * (robbery - price) + (12 * kyla));
	}

}
