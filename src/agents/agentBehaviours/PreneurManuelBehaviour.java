package agents.agentBehaviours;

import agents.PreneurAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class PreneurManuelBehaviour extends Behaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8056054096804068926L;
	
	private boolean finish = false;
	private boolean biding = true;
	private boolean updating = true;
	private ACLMessage msgReceived;
	private PreneurAgent owner;
	
	private ACLMessage to_bid = new ACLMessage(ACLMessage.PROPOSE);
	private ACLMessage to_pay = new ACLMessage(ACLMessage.CONFIRM);
	
	public PreneurManuelBehaviour(PreneurAgent agent) {
		owner = agent;
	}
	@Override
	public void action() {
		if(biding) {
			//System.out.println("Manuel behaviour: i have " + owner.getEnchereList());
			//owner.getManuelController().setList(owner.getEnchereList());
			for(int i = 0; i < owner.getEnchereList().size(); i++) {
				//System.out.println("i have: " + owner.getEnchereList().get(i));
				owner.getManuelController().updateEnchere(owner.getEnchereList().get(i));
			}
		}
		//finish = true;
		//System.out.println("Manuel behaviour: start");
		/*if(biding) {
			for(int i = 0; i < owner.getEnchereList().size(); i++) {
				//System.out.println("i have: " + owner.getEnchereList().get(i));
				owner.getAutoController().updateEnchere(owner.getEnchereList().get(i));
				String name = owner.getEnchereList().get(i).getVendeur();
				System.out.println("Manuel behaviour: i send a to_bid to: " + name);
				to_bid.addReceiver(new AID(name, AID.ISLOCALNAME));
			}
			owner.send(to_bid);
			biding = false;
		}
		
		msgReceived = owner.receive();
		
		if(msgReceived != null) {
			String name = msgReceived.getSender().getName().substring(0, 2);
			System.out.println("Manuel behaviour: msgReceived from " + name);
			if(msgReceived.getPerformative() == ACLMessage.INFORM) {
				System.out.println("Manuel behaviour: it's a rep_bid.");
				String content = msgReceived.getContent();
				System.out.println("Manuel behaviour: content " + content);
			}
			if(msgReceived.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
				System.out.println("Manuel behaviour: it's a to_attribute.");
				to_pay.addReceiver(new AID(name, AID.ISLOCALNAME));
				owner.send(to_pay);
			}
			if(msgReceived.getPerformative() == ACLMessage.AGREE) {
				System.out.println("Manuel behaviour: it's a tp_give.");
				System.out.println("Manuel behaviour: i received my fish.");
			}
		}
		else {
			block();
		}*/
	}

	@Override
	public boolean done() {
		return finish;
	}

}
