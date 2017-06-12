package writeAndReadXML;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import org.jdom2.input.SAXBuilder;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

public class LocationReader implements LocationReaderInterface {
	
	private int xAxisVal;
	private int yAxisVal;
	
	private double ticketPrice;
	private int eventId;
	
	static ArrayList<Double> priceList = new ArrayList<Double>();
	
	public LocationReader(int xAxisVal, int yAxisVal){
		this.xAxisVal = xAxisVal;
		this.yAxisVal = yAxisVal;
	}
	
	public boolean retrieveEventIdAndPrice(){
		
		boolean returnValue = false;
		
		try{
			
			//for communication with XML file
			SAXBuilder builder = new SAXBuilder();
			
			Document documentReader = builder.build(new File("./src/ticketsFile.xml"));
			
			Element root = documentReader.getRootElement();
			
			//loops through all coordinates
			for (Element currentElementCoordinate : root.getChildren("coordinate")) {
				
				//checks if coordinate equals current nearest x and y location
				if((currentElementCoordinate.getAttributeValue("x_axis").equals(Integer.toString(xAxisVal))) && (currentElementCoordinate.getAttributeValue("y_axis").equals(Integer.toString(yAxisVal)))){
					
					//if there is an event in the coordinate location
					if(!(currentElementCoordinate.getChildren().isEmpty())){
						
						Element currentElementEvent = currentElementCoordinate.getChild("event");
						
						//if there is one or more ticket for the Event
						if(!(currentElementEvent.getChildren().isEmpty())){
							
							//Loop through all tickets available and add their prices to priceList ArrayList
							for (Element currentElementTicket : currentElementEvent.getChildren("ticket")) {
								priceList.add(Double.parseDouble(currentElementTicket.getAttributeValue("Price")));
							}
							
							//Returns true because there is an event and ticket for this coordinate
							returnValue = true;
							
							//sets minimum price to ticket price
							ticketPrice = priceList.get(priceList.indexOf (Collections.min(priceList)));
							priceList.clear();
							//sets unique eventId
							eventId = Integer.parseInt(currentElementEvent.getAttributeValue("eventId"));
							
						}
						
					}
				}
				
			}
			
			}
			
			catch (JDOMException ex1) {
				ex1.printStackTrace();
			} 
			
			catch (IOException ex2) {
				ex2.printStackTrace();
			} 
		return returnValue;
	}

	public double getTicketPrice() {
		return ticketPrice;
	}
	
	public int getEventId() {
		return eventId;
	}
	
	}
