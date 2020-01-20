package agents;

import java.util.ArrayList;

import agents.agentBehaviours.PreneurAnnounceBehaviour;
import jade.core.Agent;
import model.Enchere;

public class PreneurAgent extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7728858608190050506L;
	private String myName;
	private boolean finish = false;
	private ArrayList<Enchere> enchereList;
	private int budget;
	
	@Override
	protected void setup() {
		System.out.println("Hello! Agent "+getAID().getName()+" is ready.");
		
		enchereList = new ArrayList<>();
		
		addBehaviour(new PreneurAnnounceBehaviour(this));
		
		// Get the name of the agent as a start-up argument
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			myName = (String) args[0];
			budget = Integer.parseInt((String) args[1]);
			
			// Printout the name
		    System.out.println("My name is " + myName);
		    System.out.println("My budget is " + budget);
		    
		    
		    /*new Thread() {
	            @Override
	            public void run() {
	                
	            }
	        }.start();*/
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

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public ArrayList<Enchere> getEnchereList() {
		return enchereList;
	}

	public void setEnchereList(ArrayList<Enchere> enchereList) {
		this.enchereList = enchereList;
	}
}