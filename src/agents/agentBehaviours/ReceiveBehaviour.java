package agents.agentBehaviours;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class ReceiveBehaviour extends Behaviour {
	private boolean finish = false;
	private ACLMessage msg;
	
	@Override
	public void action() {
		msg = myAgent.receive();
        if(msg != null){
            // process msg
            if(msg.getPerformative() == ACLMessage.INFORM){
                finish = true;
            }
            else{
                System.out.println("Hello World !");
            }
        }
        else{
            block();
        }
	}

	@Override
	public boolean done() {
		return finish;
	}
}
