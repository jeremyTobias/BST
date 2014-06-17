import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;

/**
* BinReflectPanel creates the display panel in which the binary search tree is reflected upon
* @author JTobias
*/
public class BinReflectPanel extends JPanel {

	private BSTReflect<Integer> disTree;
	private int width;
	private int radius = 10;
	private int verticalGap = 40;

	/**
	* Display panel constructor
	* @param tree - BST received from BSTFrame
	* @param w - width of the panel
	*/
	public BinReflectPanel(BSTReflect<Integer> tree, int w) {
		disTree = tree;
		width = w;
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
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
			g.drawOval (x - radius, y - radius, 2 * radius, 2 * radius);

			g.drawString(node.value.toString() + "", x - 6, y + 4);

			if (node.left != null) {
				connectRight(g, x - gap, y + verticalGap, x, y);
				drawTree(g, node.left, x - gap, y + verticalGap, gap / 2);				
			}

			if (node.right != null) {
				connectLeft(g, x + gap, y + verticalGap, x, y);
				drawTree(g, node.right, x + gap, y + verticalGap, gap / 2);
			}
		}
	}

	// connects two left nodes
	protected void connectLeft(Graphics g, int x1, int y1, int x2, int y2) {
		double r = Math.sqrt(Math.pow(verticalGap, 2) + Math.pow((x2 - x1), 2));
		int x11 = (int)(x1 + radius * (x2 - x1) / r);
		int y11 = (int)(y1 - radius * verticalGap / r);
		int x21 = (int)(x2 - radius * (x2 - x1) / r);
		int y21 = (int)(y2 + radius * verticalGap / r);
		g.drawLine(x11, y11, x21, y21);
	}

	// connect two right nodes
	protected void connectRight(Graphics g, int x1, int y1, int x2, int y2) {
		double r = Math.sqrt(Math.pow(verticalGap, 2) + Math.pow((x2 - x1), 2));
		int x11 = (int)(x1 - radius * (x1 - x2) / r);
		int y11 = (int)(y1 - radius * verticalGap / r);
		int x21 = (int)(x2 + radius * (x1 - x2) / r);
		int y21 = (int)(y2 + radius * verticalGap / r);
		g.drawLine(x11, y11, x21, y21);
	}
}
