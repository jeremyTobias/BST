import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.Integer;
import java.util.*;
import javax.swing.*;
import javax.swing.SwingUtilities.*;

/**
* BSTFrame creates the GUI for the binary search tree to be displayed on.
* @author JTobias
*/
public class BSTFrame extends JFrame implements ActionListener, Serializable {
	// set height and width for app
	private int width = 1000;
	private int height = 600;

	// incrementer for creating a limit to the BST
	private int limit = 0;

	// globals and stuff
	private JTextField leafField;
	private JButton leafButt,
			killLeafButt,
			reflectorButt,
			clearButt,
			saveButt;
	private JPanel controlPane,
			showTreePane,
			reflectTreePane;

	// create a new BST for application instance
	BST<Integer> binaryTree = new BST<Integer>();
	BSTReflect<Integer> binReflectTree = new BSTReflect<Integer>();

	/**
	* Create the core GUI structure for the application.
	* @param title
	*/
	public BSTFrame(String title) {
		super(title);		
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		addComponents(getContentPane());
		addListeners();
		setVisible(true);
		setResizable(false);
		loadBST();
	}

	// local references
	private String leafString() { return leafField.getText(); }
	private int leaf() {
		int leafInt = Integer.parseInt(leafField.getText()); 
		return leafInt; 
	}

	/**
	* Establish the main GUI frame and components.
	* @param contentPane
	*/
	private void addComponents(Container contentPane) {
		contentPane.setLayout(new BorderLayout());

		addControlPanel(contentPane);
		addViewPort(contentPane);
	}

	/**
	* Create the control panel to add, remove, and find values within the BST,
	* and to reflect the tree to the reflection pane and clear the BST.
	* @param contentPane
	*/
	private void addControlPanel(Container contentPane) {
		controlPane = new JPanel();
		controlPane.setLayout(new BorderLayout());

		JPanel cpInnerLeft = new JPanel();
		cpInnerLeft.setLayout(new BoxLayout(cpInnerLeft,BoxLayout.LINE_AXIS));
		JPanel cpInnerRight = new JPanel();
		cpInnerRight.setLayout(new BoxLayout(cpInnerRight,BoxLayout.LINE_AXIS));

		// create buttons
		leafButt = new JButton("Add To Tree");
		leafButt.setToolTipText("Click me to add a random number to the tree.");
		killLeafButt = new JButton("Remove From Tree");
		reflectorButt = new JButton("Reflect Tree");
		clearButt = new JButton("Clear Tree");
		saveButt = new JButton("Save Tree");

		// create text field
		leafField = new JTextField();
		leafField.setBorder(BorderFactory.createLoweredBevelBorder());
		leafField.setPreferredSize(new Dimension(30,20));

		// add components to control panel
		cpInnerLeft.add(leafButt);
		cpInnerLeft.add(Box.createRigidArea(new Dimension(75,0)));
		cpInnerLeft.add(leafField);
		cpInnerLeft.add(Box.createRigidArea(new Dimension(15,0)));	
		cpInnerLeft.add(killLeafButt);

		cpInnerRight.add(reflectorButt);
		cpInnerRight.add(Box.createRigidArea(new Dimension(20,0)));
		cpInnerRight.add(clearButt);
		cpInnerRight.add(Box.createRigidArea(new Dimension(20,0)));
		cpInnerRight.add(saveButt);
		
		controlPane.add(cpInnerLeft,BorderLayout.LINE_START);
		controlPane.add(cpInnerRight,BorderLayout.LINE_END);
		controlPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		contentPane.add(controlPane,BorderLayout.SOUTH);
	}

	/**
	* Create the viewing panes for the BST
	* @param contentPane
	*/
	private void addViewPort(Container contentPane) {
		// create BST viewing panes
		showTreePane = new BinDisplayPanel(binaryTree, width / 2);
		
		reflectTreePane = new BinReflectPanel(binReflectTree, (width / 2) - 25);	

		// create a split pane and insert viewing components
		JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, showTreePane, reflectTreePane) {
			
			// hack to keep divider from being able to be moved
			private final int location = width / 2;

			{ setDividerLocation(location); }

			@Override
			public int getDividerLocation() { return location; }

			@Override
			public int getLastDividerLocation() { return location; }
		};

		splitter.setResizeWeight(0.5);

		contentPane.add(splitter,BorderLayout.CENTER);
	}

	/**
	* Create action listeners for the buttons.
	*/
	private void addListeners() {
		leafButt.addActionListener(this);
		killLeafButt.addActionListener(this);
		reflectorButt.addActionListener(this);
		clearButt.addActionListener(this);
		saveButt.addActionListener(this);
	}

	/**
	* Create actions to perform once buttons are pressed.
	*/
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();		

		if (command.equals("Add To Tree")) { // add a node to the BST
			if (limit == 20) { // limits the BST to 20 nodes
				limitReached();
				return;
			} else {
				binaryTree.add(ranNum()); // add a random number to BST

				// ************ This stuff for debugging ************ //
				// ************							 ************ //
				Iterator<Integer> bstItr = binaryTree.iterator();

				//System.out.println(binaryTree);
				System.out.println("****");
				System.out.println("Root of tree: " + binaryTree.root.value.toString());
				System.out.println("Size of tree: "  + binaryTree.getSize());
				while (bstItr.hasNext()) {
					System.out.print(bstItr.next() + ", ");
				}
				System.out.println();
				System.out.println("****");
				// ************************************************** //
				// ************************************************** //

				showTreePane.repaint();
				limit++;
			}

			if (!binReflectTree.isEmpty()) { // if there is a reflection clean it up
				binReflectTree.clear();
				reflectTreePane.removeAll();
				reflectTreePane.repaint();
			}
		}

		if (command.equals("Remove From Tree")) { // remove a node from BST
			if (leafString().trim().length() == 0) {
				oopsMsg("remove from the tree.");
			} else if (!binaryTree.exists(leaf())) {
				noExist(leafString());				
				leafField.setText("");
			} else {
				binaryTree.remove(leaf());
				leafField.setText("");
				showTreePane.repaint();

				if (!binReflectTree.isEmpty()) { // if there is a reflection clean it up
					binReflectTree.clear();
					reflectTreePane.removeAll();
					reflectTreePane.repaint();
				}

				limit--;
			}
		}

		if (command.equals("Reflect Tree")) { // reflect the BST
			Iterator<Integer> bstItr = binaryTree.iterator();
			
			while (bstItr.hasNext()) {
				binReflectTree.add(bstItr.next());
			}
			
			reflectTreePane.repaint();
		}

		if (command.equals("Clear Tree")) { // clear everything
				limit = 0;
				binaryTree.clear();
				binReflectTree.clear();
				showTreePane.removeAll();
				showTreePane.repaint();
				reflectTreePane.removeAll();
				reflectTreePane.repaint();
		}

		if (command.equals("Save Tree")) { // save the BST
			if (binaryTree.isEmpty()) {
				emptyTreeMsg();
			} else {
				try { // attempts to save the current BST to a file
					String home = System.getProperty("user.home");
					File file = new File(home + "/.bstrc");

					String content = "";

					if (!file.exists()) {
						file.createNewFile();
					}

					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					
					Iterator<Integer> bstItr = binaryTree.iterator();
			
					while (bstItr.hasNext()) {
						content = Integer.toString(bstItr.next()) + " ";
						bw.write(content);
					}

					bw.close();

					fileSaved();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	// generates a random number to be placed in BST
	private int ranNum() {
		return (int)(Math.random() * 100);
	}

	// attempts to load csc365.bstrc from the user's home directory
	private void loadBST() {
		BufferedReader fileInput;

		int nodeLoad;

		try {
			fileInput = new BufferedReader(new InputStreamReader(new FileInputStream(
				new File(System.getProperty("user.home") + "/.bstrc"))));
			Scanner input = new Scanner(fileInput);
			while (input.hasNext()) {
				nodeLoad = Integer.parseInt(input.next());
				binaryTree.add(nodeLoad);
			}
		} catch (IOException e) { }
		showTreePane.repaint();
	}

	// error message if Remove textfield is empty
	private void oopsMsg(String oops) {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,
			"Oops! Please fill out the text field in order to " + oops);
	}

	// error message if value trying to be removed does not exist
	private void noExist(String oops) {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,
			"Sorry. " + oops + " does not exist. Try again.");
	}

	// error message if node limit is reached
	private void limitReached() {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,
			"Sorry. you have reached your node limit for this Binary Search Tree. " +
			"Please remove a node(s), or clear the tree and start again.");
	}

	// error message if tree is empty and trying to save
	private void emptyTreeMsg() {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,
			"Binary Seach Tree is empty. Please add some nodes and then try to save.");
	}

	// message to confirm save
	private void fileSaved() {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,
			"File has been saved to your home directory.");
	}
}
