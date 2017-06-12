
package controllers;
import java.util.ArrayList;

import writeAndReadXML.LocationReader;

public class LocationsCalculator implements LocationsCalculatorInterface {

	ArrayList<Double> cheapestPrices = new ArrayList<Double>();
	ArrayList<Integer> eventIds = new ArrayList<Integer>();
	ArrayList<Integer> distances = new ArrayList<Integer>();
	
	int initialXAxisVal;
	int initialYAxisVal;
	
	LocationReader objLocationReader;
	
	public LocationsCalculator(int initialXAxisVal, int initialYAxisVal){
		this.initialXAxisVal = initialXAxisVal;
		this.initialYAxisVal = initialYAxisVal;
	}
	
	public void nearestLocations(){
		/*
		locationCoordinates is a two dimensional array that holds the x-axis and y-axis shortest distance at a given time
		For example, the shortest integer distance between any two points is 1 and x-axis and y-axis values that add to one are 0,1
		For example, the next shortest integer distance between any two points is 2 and x-axis and y-axis values that add to two are 1,1; 2,0
		etc...
		locationCoordinates variable holds such value sets in a two dimensional array
		*/
		int[][] locationCoordinates = new int[0][0];
		
		//Dynamically sets array size for locationCoordinates array depending on the distance
		//Example, for distance of 4, array values will be 0,4; 1,3; 2,2 and the array size will be [3][2]
		int arraySizeForLocationCoordinates;
		
		//loops through all possible distances, starting from the shortest; longest distance between -20 and 20 for x and y axis is 80
		for (int locationDistance=1; locationDistance <=80; locationDistance++){
			
			/*For every location distance value, the x and y values that add to it are between 0 to the value
			 * For example, for a distance of 5, the x and y values that can add to it are 0,1,2,3,4,5
			 * distanceValues variable stores such values for each distance dynamically in an ArrayList
			 */
			ArrayList<Integer> distanceValues = new ArrayList<Integer>();
			for (int i = 0; i <= locationDistance; i++){
				distanceValues.add(i);
			}
			
			//Dynamically sets size for locationCoordinates
			if ((distanceValues.size() % 2) == 0){//if number of values in distanceValues variable is even
				
				//Divide by 2 because the values will be stored in pairs that give the sum in multidimensinal array
				arraySizeForLocationCoordinates = distanceValues.size()/2;
				
				//Dynamically set locationCoordinates size
				locationCoordinates=new int[arraySizeForLocationCoordinates][2];
				
			//if number of values in distanceValues variable is odd, it means one of the numbers (middle number) will add to itself to produce required pair
			//Example, for distance of 2, values are 0,1,2; in this case 1 will add to itself to produce 2
			}else{
				
				//add one to original size before dividing for array size since one of the numbers (middle number) will add to itself to produce required pair
				arraySizeForLocationCoordinates = (distanceValues.size() + 1)/2;
				
				//Dynamically set locationCoordinates size
				locationCoordinates=new int[arraySizeForLocationCoordinates][2];
				
				//Assigning the middle number which will add to itself to the last index location of locationCoordinates
				locationCoordinates[arraySizeForLocationCoordinates-1][0] = distanceValues.get(distanceValues.size()/2);
				locationCoordinates[arraySizeForLocationCoordinates-1][1] = distanceValues.get(distanceValues.size()/2);
				
				//removing that middle number since it has now been added to the 2-dimensional array, this now leaves distanceValues with an even size
				distanceValues.remove(distanceValues.size()/2);
			}
			
			//this will help to determine appropriate index to store values
			int locationCoordinatesIndex = 0;
			
			//Loop until distanceValues ArrayList is empty
			while(!distanceValues.isEmpty()){
				
				//Loop the values of distanceValues starting from the second value
				for (int j = 1; j <= distanceValues.size(); j++) {
					
					//if first value + current value = locationDistance, then a pair has been achieved
					if(distanceValues.get(0) + distanceValues.get((j)) == locationDistance){
						
						//Store the pair in locationCoordinates array
						locationCoordinates[locationCoordinatesIndex][0] = distanceValues.get(0);
						locationCoordinates[locationCoordinatesIndex][1] = distanceValues.get((j));
						
						//increment index value
						locationCoordinatesIndex++;
						
						//remove the pair because it has been stored
						distanceValues.remove((j));
						distanceValues.remove(0);
						
						//exit while loop because pair has been gotten
						break;
				
					}
				}
			}
			
			//Pass coordinate pairs to checkLocation method
			//This method returns true if the closest 5 pairs have been achieved
			if (checkLocation(locationCoordinates) == true){
				//break out of for loop because only 5 locations are required
				break;
			}
			
		}
		
	}
	
	public boolean checkLocation(int[][] locationCoordinates){
		
		//returns true if the closest 5 coordinate pairs have been achieved
		boolean completeCoordinates = false;
		
		/*For each pair of distance, there are four possible locations with the same distance
		 * Example, if initial location is 6,7; and distance pair is 0,1
		 * There are four possible new locations being 7,7; 6,8; 5,7; 6,6.
		 * This is achieved with the for loop
		 */
		for(int i=0; i<locationCoordinates.length; i++) {
			
			int newLocationXaxisOne = initialXAxisVal + locationCoordinates[i][0];
			int newLocationYaxisOne = initialYAxisVal + locationCoordinates[i][1];
			int newLocationXaxisTwo = initialXAxisVal + locationCoordinates[i][1];
			int newLocationYaxisTwo = initialYAxisVal + locationCoordinates[i][0];
			int newLocationXaxisThree = initialXAxisVal - locationCoordinates[i][0];
			int newLocationYaxisThree = initialYAxisVal - locationCoordinates[i][1];
			int newLocationXaxisFour = initialXAxisVal - locationCoordinates[i][1];
			int newLocationYaxisFour = initialYAxisVal - locationCoordinates[i][0];
			
			//if coordinate values are within range of world for first instance
			if (newLocationXaxisOne<=20 && newLocationYaxisOne<=20 && newLocationXaxisOne>= -20 && newLocationYaxisOne >= -20){
				
				objLocationReader = new LocationReader(newLocationXaxisOne, newLocationYaxisOne);
				
				//if an event and corresponding tickets are available for the selected coordinates
				if(objLocationReader.retrieveEventIdAndPrice() == true){
					cheapestPrices.add(objLocationReader.getTicketPrice());
					eventIds.add(objLocationReader.getEventId());
					distances.add(locationCoordinates[i][0] + locationCoordinates[i][1]);
					
					//if 5 values have been received
					if((eventIds.size() == 5)){
						completeCoordinates = true;
						
						//5 values are received so exit for loop
						break;
					}
					
				}
				
			}
			
			//if coordinate values are within range of world for second and coordinate pairs are not equal (that would lead to duplication of pairs with first instance)
			if (newLocationXaxisTwo<=20 && newLocationYaxisTwo<=20 && newLocationXaxisTwo>= -20 && newLocationYaxisTwo >= -20  && (locationCoordinates[i][0] != locationCoordinates[i][1])){

				objLocationReader = new LocationReader(newLocationXaxisTwo, newLocationYaxisTwo);
				
				//if an event and corresponding tickets are available for the selected coordinates
				if(objLocationReader.retrieveEventIdAndPrice() == true){
					cheapestPrices.add(objLocationReader.getTicketPrice());
					eventIds.add(objLocationReader.getEventId());
					distances.add(locationCoordinates[i][0] + locationCoordinates[i][1]);
					
					//if 5 values have been received
					if((eventIds.size() == 5)){
						completeCoordinates = true;
						
						//5 values are received so exit for loop
						break;
					}
					
				}
			}
			
			//if coordinate values are within range of world for third instance
			if (newLocationXaxisThree<=20 && newLocationYaxisThree<=20 && newLocationXaxisThree>= -20 && newLocationYaxisThree >= -20){
				
				objLocationReader = new LocationReader(newLocationXaxisThree, newLocationYaxisThree);
				
				//if an event and corresponding tickets are available for the selected coordinates
				if(objLocationReader.retrieveEventIdAndPrice() == true){
					cheapestPrices.add(objLocationReader.getTicketPrice());
					eventIds.add(objLocationReader.getEventId());
					distances.add(locationCoordinates[i][0] + locationCoordinates[i][1]);
					
					//if 5 values have been received
					if((eventIds.size() == 5)){
						completeCoordinates = true;
						
						//5 values are received so exit for loop
						break;
					}
					
				}
				
			}
			
			//if coordinate values are within range of world for fourth and coordinate pairs are not equal (that would lead to duplication of pairs with third instance)
			if (newLocationXaxisFour<=20 && newLocationYaxisFour<=20 && newLocationXaxisFour>= -20 && newLocationYaxisFour >= -20  && (locationCoordinates[i][0] != locationCoordinates[i][1])){
				
				objLocationReader = new LocationReader(newLocationXaxisFour, newLocationYaxisFour);
				
				//if an event and corresponding tickets are available for the selected coordinates
				if(objLocationReader.retrieveEventIdAndPrice() == true){
					cheapestPrices.add(objLocationReader.getTicketPrice());
					eventIds.add(objLocationReader.getEventId());
					distances.add(locationCoordinates[i][0] + locationCoordinates[i][1]);
					
					//if 5 values have been received
					if((eventIds.size() == 5)){
						completeCoordinates = true;
						
						//5 values are received so exit for loop
						break;
					}
					
				}
				
			}
			
	    }
		
		return completeCoordinates;
		
	}
	
	public int getEventId(int indexValue){
		return eventIds.get(indexValue);
	}
	
	public int getDistance(int indexValue){
		return distances.get(indexValue);
	}
	
	public double getPrice(int indexValue){
		return cheapestPrices.get(indexValue);
	}
	
	
}
