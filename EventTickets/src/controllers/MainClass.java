// ***********************************************************************
// Assembly         	: Tickets Display
// Author           	: Iniobong Mbuk
// Created          	: 12-06-2017
// Programming Language	: Java
// IDE Used				: Eclipse
// 
// Last Modified By : Iniobong Mbuk
// Last Modified On : 12-06-2017
//
// PLEASE YOU MUST INSTALL THE JDOM LIBRARY TO BE ABLE TO RUN THIS APPLICATION AS THIS PROGRAM WRITES TO AND READS FROM XML
// ***********************************************************************

/*
 * (1.) Assumptions made:
 * (a.) Coordinates can only be integer values
 * 
 * (2.) If I needed to support multiple events at the same location, I would program my seed data to also generate
 * multiple events, that is, in DataGenerating class (line 75), the numberOfEvents would equal generateRandomNumber(x), where x > 1.
 * 
 * (3.) If I was working with a much larger world size, I would use a RDBMS for storing data due to performance
 * I would also increase the range of my programmed world size to accommodate it (see line 37 of LocationsCalculator class).
 */

package controllers;

import views.DataEntryAndDisplay;
import writeAndReadXML.DataGenerating;

public class MainClass {

	public static void main(String[] args) {
		
		//Generates seed data
		DataGenerating objDataGenerating = new DataGenerating();
		objDataGenerating.generateSeedData();
		
		//Gets coordinates from user
		DataEntryAndDisplay objDataEntryAndDisplay = new DataEntryAndDisplay();
		objDataEntryAndDisplay.receiveCoordinates();
		
		//Computes the 5 nearest locations
		LocationsCalculator objLocationsCalculator = new LocationsCalculator(objDataEntryAndDisplay.getInitialXAxisValue(), objDataEntryAndDisplay.getInitialYAxisValue());
		objLocationsCalculator.nearestLocations();
		
		//Displays result
		objDataEntryAndDisplay.displayHeader();
		for (int i = 0; i < 5; i++) {
			objDataEntryAndDisplay.displayResult(objLocationsCalculator.getPrice(i), objLocationsCalculator.getEventId(i), objLocationsCalculator.getDistance(i));
		}
		
	}
	
}
