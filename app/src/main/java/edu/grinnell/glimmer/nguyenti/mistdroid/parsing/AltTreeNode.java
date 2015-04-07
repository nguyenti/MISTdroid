package parsing;

import java.util.ArrayList;


public class AltTreeNode extends TreeNode {

    AltTreeNode parent;

    public AltTreeNode(String str) {
   	super(str);
       }// TreeNode 
    
    public AltTreeNode(String str, AltTreeNode par) {
	super(str);
	parent = par;
    }// AltTreeNode(String, AltTreeNode)

    public void addChild(String child) {
	this.children.add(new AltTreeNode(child, this));

    }// addChild

    public int numChildren() {
	return children.size();
    }// numChildren

    public boolean hasChildren() {
	return children.size() > 0;
    }// hasChildren

    public static void main(String[] args) {
	// TODO Auto-generated method stub
	AltTreeNode t = new AltTreeNode("t");
	t.addChild("h");
	t.addChild("j");
	System.out.println(t);
    }

}// class AltTreeNode
