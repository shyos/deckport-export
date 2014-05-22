package extracter.GUI.WB.winlose;

public class KeyHookThread extends Thread{
	
	   @Override
	   public void run()
	   {
		   KeyHook.main(null);
	   }

	public void endJob() {
		KeyHook.close();
	}
}
