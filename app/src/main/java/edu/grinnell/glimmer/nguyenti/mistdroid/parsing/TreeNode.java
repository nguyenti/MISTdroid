package parsing;

import java.io.PrintWriter;
import java.util.ArrayList;

import evaluating.RGBValue;


public class TreeNode
{

  String val;
  ArrayList<TreeNode> children;
  boolean set;
  RGBValue evaluation;

  /* Constructor */
  public TreeNode(String str)
  {
    val = str;
    children = new ArrayList<>();
    set = false;
  }// TreeNode

  /* Adding children */

  public void addChild(String child)
  {
    this.children.add(new TreeNode(child));
  }// addChild

  public void addChild(TreeNode child)
  {
    this.children.add(child);
  }

  /* Getters */

  /**
   * TODO We don't use it?
   * @return
   */
  public int numChildren()
  {
    return children.size();
  }// numChildren

  /**
   * TODO We don't use it?
   * @return
   */
  public boolean hasChildren()
  {
    if (children.size() > 0)
      return true;
    return false;
  }// hasChildren

  public ArrayList<TreeNode> getChildren()
  {
    return this.children;
  }//getChildren

  public String getRootVal()
  {
    return this.val;
  }

  public boolean isSet()
  {
    return set;
  }
  
  public RGBValue getEvaluation()
  {
    return this.evaluation;
  }
  /* Utility */

  public void set()
  {
    set = true;
  }
  public void evaluate(RGBValue v)
  {
    evaluation = v;
  }
  public boolean isLeaf()
  {
    return this.children.isEmpty();
  }

  // TODO
  public boolean equals(TreeNode other)
  {
    return this.toString().equals(other.toString());
  } // hacked equals

  @Override
  public String toString()
  {
    return toStringHelper(this, "");
  }// toString

  String toStringHelper(TreeNode root, String indent)
  {
    String result = indent + root.val + "\n";
    // result += indent + root.val + "\n";
    if (root.hasChildren()) // not a leaf
      {
        indent += "  ";
        for (TreeNode t : root.children)
          {
            result += toStringHelper(t, indent);
          }
      }
    return result;
  }// toString Helper

  /*
  @Override
  public int hashCode()
  {
    int hash = 17;
    // Suitable nullity checks etc, of course :)
    hash = hash * 23 + val.hashCode();
    for (TreeNode node : children)
      {
        if (!(node == null))
          {
            hash = hash * 23 + node.hashCode();
          }

      }

    return hash;

  }//hashCode()
  */
  public String getNodeVal(){
    return this.val;
  }

  public static void main(String[] args)
    throws Exception
  {
    TreeNode result = new TreeNode("sum");
    result.addChild("x");
    result.addChild("y");
    PrintWriter pen = new PrintWriter(System.out, true);
    pen.println(result);
    String test = "rgb(sq(sum(x,y)),x,y)";
    String okay =
        "rgb(wsum(x, y, y, t.s), sum(sum(wsum(x, x, y, neg(t.s), x), wsum(y, y, x, neg(t.s), y)), sum(wsum(y, x, x, t.s), wsum(x, y, y, t.s))), sum(wsum(x, x, y, neg(t.s), x), wsum(y, y, x, neg(t.s), y)))";
    TreeNode t = Parser.parse(okay);
    String recParsed = t.toString();
    String itParsed = AltParser.parse(okay).toString();
    System.out.println(recParsed.equals(itParsed));
    System.out.println(recParsed);
    System.out.println(itParsed);

    // Parser.parse("");
    TreeNode res = Parser.parse("x");
    TreeNode correct = new TreeNode("x");
    System.out.println(res);

    // Testing isLeaf()
    System.out.println("TESTING isLeaf()");
    TreeNode testNode = new TreeNode("x");
    System.out.println("Expected:true  Actual:" + testNode.isLeaf());
    testNode = result;
    System.out.println("Expected:false  Actual:" + testNode.isLeaf());

  }

}
