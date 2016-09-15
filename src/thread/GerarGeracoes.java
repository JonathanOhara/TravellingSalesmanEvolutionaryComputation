package thread;

import listener.ActionsListener;

public class GerarGeracoes implements Runnable{

	private ActionsListener action = null;
	
	private boolean run;
	
	public GerarGeracoes(ActionsListener actionsListener) {
		action = actionsListener;
		run = true;
	}
	
	@Override
	public void run() {
		try {
			while( run ){
				action.gerarNovaGeracao();
				Thread.sleep(20);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void stop(){
		run = false;
	}

}
