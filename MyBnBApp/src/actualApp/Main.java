package actualApp;
/**
 * 
 */
/**
 * @author 2943644
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CommandLine commandLine = new CommandLine();
		if (commandLine.startSession() && commandLine.execute()) {
			commandLine.endSession();
		}

	}

}
