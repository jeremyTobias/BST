import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.SwingUtilities.*;

/**
* BSTSwingApp is a java application which displays a binary search tree of randomly generated numbers
* through user interaction. The user can then remove nodes, clear the tree or save the tree to a file.
* The user is also capapble of reflecting the tree onto a neighboring display panel.
* @author JTobias
* @references - http://docs.oracle.com/javase/tutorial/uiswing/components/splitpane.html#divider
*				http://stackoverflow.com/questions/7065309/jsplitpane-set-resizable-false
*				http://docs.oracle.com/javase/tutorial/uiswing/components/tooltip.html
*				http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
*				http://en.wikipedia.org/wiki/Tree_traversal#Post-order
*				http://www.mkyong.com/java/how-to-write-to-file-in-java-bufferedwriter-example/
*				http://docs.oracle.com/javase/1.4.2/docs/tooldocs/windows/javadoc.html#runningjavadoc
*				http://docs.oracle.com/javase/1.4.2/docs/api/java/awt/Graphics.html
*/
public class BSTSwingApp {

	/**
	* Main method to initialize application
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new ThreadForGUI());
	}

	private static class ThreadForGUI implements Runnable {
		public void run() {
			createAndShowGUI();
		}
	}

	/**
	* Creates a new GUI
	*/
	public static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
	
		try {
	            //Turn off metal's use of bold fonts
	            //UIManager.put("swing.boldMetal", Boolean.FALSE); 
	            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	        }
		catch (Exception e) { }

		BSTSwingApp bstSwing = new BSTSwingApp();
    }

	private BSTSwingApp() {
		BSTFrame frame = new BSTFrame("Binary Search Tree");
	}
}
