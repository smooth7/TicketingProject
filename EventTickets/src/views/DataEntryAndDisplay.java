package views;

import java.text.NumberFormat;
import java.util.Scanner;

public class DataEntryAndDisplay implements DataEntryInterface, DataDisplayInterface {

	Scanner userInput = new Scanner(System.in);
	
	int initialXAxisValue;
	int initialYAxisValue;
	
	public void receiveCoordinates(){
		
		System.out.println("This program will get your coordinates and return the five nearest events.");
		System.out.println();
		
		System.out.println("Please input your integer x-axis coordinate location");
		if (userInput.hasNextInt()){
			this.initialXAxisValue = userInput.nextInt();
			
			if(!(initialXAxisValue >= -20 && initialXAxisValue <= 20)){
				System.err.println("The x-axis value you entered is out of range, please try again");
				System.exit(0);
			}
			
		}else{
			System.err.println("Invalid Entry, please try again");
			System.exit(0);
		}
		
		System.out.println("Please input your integer y-axis coordinate location");
		if (userInput.hasNextInt()){
			this.initialYAxisValue = userInput.nextInt();
			
			if(!(initialYAxisValue >= -20 && initialYAxisValue <= 20)){
				System.err.println("The y-axis value you entered is out of range, please try again");
				System.exit(0);
			}
			
		}else{
			System.err.println("Invalid Entry, please try again");
			System.exit(0);
		}
		
	}
	
	public void displayHeader(){
		System.out.println();
		System.out.println("The closest events to (" + initialXAxisValue + "," + initialYAxisValue + ") are:");
		
	}
	
	public void displayResult(double cheapestPrice, int eventId, int distance){
		
		String formattedEventId = String.format("%03d", eventId);
		NumberFormat formattedPrice = NumberFormat.getCurrencyInstance();
		String formattedPriceValue = formattedPrice.format(cheapestPrice);
		
		System.out.println("Event " + formattedEventId + " - " + formattedPriceValue + ", Distance " + distance);
		
	}
	

	public int getInitialXAxisValue() {
		return initialXAxisValue;
	}

	public int getInitialYAxisValue() {
		return initialYAxisValue;
	}
	
}
