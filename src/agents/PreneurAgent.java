package agents;

import agents.agentInterfaces.PreneurFX;
import jade.core.Agent;

public class PreneurAgent extends Agent {
	private String myName;
	private int step = 0;
	private boolean finish = false;
	
	@Override
	protected void setup() {
		System.out.println("Hello! Agent "+getAID().getName()+" is ready.");

		// Get the name of the agent as a start-up argument
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			myName = (String) args[0];
			
			// Printout the name
		    System.out.println("My name is "+myName);
		    
		    //Attend de recevoir au moins un to_announce
		    
		    //traite la/les annonce(s)
		    //encheri ou pas
		    
		    //si on recoit un rep_bid(NOK)
		    //alors on attend de recevoir un autre to_announce
		    
		    //sinon on attend un to_attribute, on paye, et on attend un to_give
		    
		    new Thread() {
	            @Override
	            public void run() {
	                javafx.application.Application.launch(PreneurFX.class);
	            }
	        }.start();
		}
		else {
			// Make the agent terminate
			System.out.println("No name specified");
			doDelete();
		}
	}
	
	protected void takeDown() {
		// Printout a dismissal message
		System.out.println("Agent "+getAID().getName()+" terminating.");
	}
}
