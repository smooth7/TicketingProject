package writeAndReadXML;
import java.io.*;

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class DataGenerating implements DataGeneratingInterface{
	
	//This method checks if ticketsFile.xml exists and deletes it before creating new one 
	public void deleteOldFile(){
		
	File ticketFile = new File("./src/ticketsFile.xml");
	
	if(ticketFile.exists()) { 
		
		try{

    		File file = new File("./src/ticketsFile.xml");

    		if(file.delete()){
    			System.out.println("Old tickets file and data has been deleted, new seed data will now be generated!");
    			System.out.println();
    		}

    	}catch(Exception e){
    		e.printStackTrace();
    	}
	    
	}
	}
	
	
	//This method randomly generates number of events, number of tickets, prices of tickets ranging from zero to maxValue
	public int generateRandomNumber(int maxValue){
		
		int upperRangeValue = ++maxValue;
		return (int)(Math.random() * upperRangeValue);
		
	}
	
	
	//This method generates XML seed data
	public void generateSeedData(){
		
		//Generating seed file will involve creating a new XML file. Hence, this method will check and delete if the file name already exists before creating new one.
		deleteOldFile();
		
		Document eventsDocument = new Document();
		
		//sets root element
		Element rootElement = new Element("world");
		eventsDocument.setRootElement(rootElement);
		
		int numberOfEvents;
		int eventId = 0;
		int numberOfTickets;
		int ticketDollarPrice;
		int ticketCentPrice;
		double ticketTotalPrice;
		
		//loops through y-axis world - from -20 to 20
		for(int yAxisValue=-20; yAxisValue<=20; yAxisValue++){
			
			//loops through x-axis world - from -20 to 20
			for(int xAxisValue=-20; xAxisValue<=20; xAxisValue++){
				
				//sets coordinate elements with each having y_axis and x_axis attribute
				Element coordinate = new Element("coordinate");
				coordinate.setAttribute("x_axis", Integer.toString(yAxisValue));
				coordinate.setAttribute("y_axis", Integer.toString(xAxisValue));
				rootElement.addContent(coordinate);
				
				//Generates number of events for each coordinate which may be 0 or 1, 0 means the given coordinate has no event
				numberOfEvents = generateRandomNumber(1);
				
				if(numberOfEvents == 1) {
					
					Element event = new Element("event");
					
					//increments unique event ID
					eventId++;
					
					//sets event elements with each having unique eventId integer attribute
					event.setAttribute("eventId", Integer.toString(eventId));
					coordinate.addContent(event);
					
					//Generates number of tickets for each event which may be between 0 to 10, 0 means tickets are exhausted
					numberOfTickets = generateRandomNumber(10);
					
					//loops through number of tickets
					for(int ticketCounter = numberOfTickets; ticketCounter>0; ticketCounter--){
						
						Element ticket = new Element("ticket");
						
						//Generates dollar price, cent price, and computes total price
						ticketDollarPrice = generateRandomNumber(800);
						ticketCentPrice = generateRandomNumber(99);
						ticketTotalPrice = ticketDollarPrice + (0.01 * ticketCentPrice);
						
						//sets ticket elements with each having Price attribute
						ticket.setAttribute("Price", Double.toString(ticketTotalPrice));
						ticket.addContent(new Text("Ticket " + ticketCounter));
						event.addContent(ticket);
						
					}
					
				}
				
			}
			
		}
		
		//formats XML to be neat and readable
		XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
		
		//Generates seed data
		try {
			xmlOutput.output(eventsDocument, new FileOutputStream(new File("./src/ticketsFile.xml")));
			System.out.println("New seed data has been generated in XML format to your ./src/ticketsFile.xml location");
			System.out.println();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
