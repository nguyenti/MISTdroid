package dagmaking;

import java.util.ArrayList;

import parsing.TreeNode;


/**
 * Implementation of a node used in creating DAG.<br>
 * Contains name of the node, the integer identification of the node,
 * all the integer identifications of the children <code> this</code> node points to.
 * @author Albert Owusu-Asare
 */
public class HashNode
{
  /*
   * FIELDS:
   * nodeVal, stores the value of the node
   * children, integer arrayList representing the keys to each node this
   * node points to.
   * nodeNumber , stores the number of this node`
   */
  private String nodeVal;
  private Integer nodeNumber;
  private ArrayList<Integer> childrenNumbers;

  /**
   * Constructs a HashNode
   * @param node, a<code>TreeNode</code>
   */
  public HashNode(TreeNode node)
  {
    this(node.getNodeVal());
  }//HashNode2(TreeNode)

  /**
   * Constructs a new HashNode
   * @param value the string of the node
   */
  public HashNode(String value)
  {
    this.nodeVal = value;
    this.nodeNumber = 0;
    this.childrenNumbers = new ArrayList<Integer>();
  }//HashNode2(String)

  /* Overridden methods from Object class */

  @Override
  /*
   * Will print:  [(nodeNumber) nodeName (childNum1, childNum2,...)]
   */
  public String toString()
  {
    String result = "";
    result += "[ (" + this.nodeNumber + ") " + this.nodeVal + " (";
    for (Integer num : this.childrenNumbers)
      {
        result += num + ",";
      }//for
    result += ") ]";
    return result;
  }//toString();

  /* Getters  and setters */

  /**
   * Returns the value of this node
   */
  public String getNodeVal()
  {
    return this.nodeVal;
  }//getNodeVal()

  /**
   * Returns the integer key corresponding to this node
   */
  public Integer getNodeNumber()
  {
    return this.nodeNumber;
  }//getNodeNumber()

  /**
   * Return the ArrayList storing all the integer keys of this nodes children
   */
  public ArrayList<Integer> getChildrenNumbers()
  {
    return this.childrenNumbers;
  }//getChildrenNumbers()

  /**
   * Sets the value of nodeNumber
   * @param number the number to set nodeNumber to 
   */
  public void setNodeNumber(Integer number)
  {
    this.nodeNumber = number;
  }//setNodeNumber(Integer)

  /**
   * Sets the value nodeVal
   * @param name the name of this node
   */
  public void setNodeVal(String nodeName)
  {
    this.nodeVal = nodeName;
  }// setNodeVal(String)

  /**
   * Adds the number of a child of this node to this node
   * @param key an integer representing the child
   */
  public void addChildNumber(Integer key)
  {
    this.childrenNumbers.add(key);
  }//addChildKey

  @Override
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (other == null)
      return false;
    if (!this.getClass().equals(other.getClass()))
      return false;
    HashNode that = (HashNode) other;
    // compare properties
    if (!this.nodeVal.equals(that.nodeVal))
      return false;
    // if(!this.nodeNumber.equals(that.nodeNumber)) return false;
    if (!this.childrenNumbers.equals(that.childrenNumbers))
      return false;
    return true;
  }//Equals

  @Override
  public int hashCode()
  {
    int hash = 17;
    for (Integer i : childrenNumbers)
      {
        if (!(i == null))
          {
            hash = hash * 23 + i.hashCode();
          }//if
      }//for

    return hash + this.nodeVal.hashCode();
  }//hashCode

}//HashNode
