import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;

/**
* BinDisplayPanel creates the display panel in which the binary search tree is displayed
* @author JTobias
*/
public class BinDisplayPanel extends JPanel {

	private BST<Integer> disTree;
	private int width;
	private int radius = 10; // oval radius
	private int verticalGap = 40; // distance between nodes

	/**
	* Display panel constructor
	* @param tree - BST received from BSTFrame
	* @param w - width of the panel
	*/
	public BinDisplayPanel(BST<Integer> tree, int w) {
		disTree = tree;
		width = w;
		setForeground(Color.BLACK);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (disTree.getRoot() != null) {
			this.drawTree(g, disTree.getRoot(), width / 2, 15, width / 4);
		} 
	}

	// displays the tree with the root at the top of the panel, centered horizontally
	protected void drawTree(Graphics g, Node node, int x, int y, int gap) {

		Font font = new Font("TimesNewRoman",Font.PLAIN,10);

		g.setFont(font);

		if (node != null) {
			// draws a pretty circle around the node values
			g.drawOval (x - radius, y - radius, 2 * radius, 2 * radius);

			// draws the node value starting with the root value
			g.drawString(node.value.toString() + "", x - 5, y + 4);

			if (node.left != null) {
				// connect to a left node
				connectLeft(g, x - gap, y + verticalGap, x, y);
				// recursive call to check for anymore left nodes
				drawTree(g, node.left, x - gap, y + verticalGap, gap / 2);
			}

			if (node.right != null) {
				// connect to a right node
				connectRight(g, x + gap, y + verticalGap, x, y);
				// recursive call to check for anymore right nodes
				drawTree(g, node.right, x + gap, y + verticalGap, gap / 2);
			}
		}
	}

	/*
	* uses the location of two left nodes to draw a line between them, scaling the length of the line
	* according to the distance between them
	* @param x1 - child node's x
	* @param y1 - child node's y
	* @param x2 - parent node's x
	* @param y2 - parent node's y
	*/
	protected void connectLeft(Graphics g, int x1, int y1, int x2, int y2) {
		double r = Math.sqrt(Math.pow(verticalGap, 2) + Math.pow((x2 - x1), 2)); // scaling
		int x11 = (int)(x1 + radius * (x2 - x1) / r); // line starting x at child
		int y11 = (int)(y1 - radius * verticalGap / r); // line starting y at child
		int x21 = (int)(x2 - radius * (x2 - x1) / r); // line ending x at parent
		int y21 = (int)(y2 + radius * verticalGap / r); // line ending y at parent
		g.drawLine(x11, y11, x21, y21); // draw the line
	}

	/*
	* uses the location of two left nodes to draw a line between them, scaling the length of the line
	* according to the distance between them
	* @param x1 - child node's x
	* @param y1 - child node's y
	* @param x2 - parent node's x
	* @param y2 - parent node's y
	*/
	protected void connectRight(Graphics g, int x1, int y1, int x2, int y2) {
		double r = Math.sqrt(Math.pow(verticalGap, 2) + Math.pow((x2 - x1), 2)); // scaling
		int x11 = (int)(x1 - radius * (x1 - x2) / r); // line starting x at child
		int y11 = (int)(y1 - radius * verticalGap / r); // line starting y at child
		int x21 = (int)(x2 + radius * (x1 - x2) / r); // line ending x at parent
		int y21 = (int)(y2 + radius * verticalGap / r); // line ending y at parent
		g.drawLine(x11, y11, x21, y21); // draw the line
	}
}
