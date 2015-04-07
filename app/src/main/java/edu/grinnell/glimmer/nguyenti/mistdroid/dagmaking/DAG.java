package dagmaking;

import java.io.PrintWriter;
import java.util.HashMap;


import parsing.Parser;
import parsing.TreeNode;


/**
 * Creates a directed acyclic graph with all unique nodes from 
 * an n-ary tree with potentially repeated nodes.
 * @author Albert Owusu-Asare<br>
 *         Vasilisa Bashlovkina<br>
 *         Jason Liu<br>
 * @since 1.0
 *
 */
public class DAG
{
  /**
   * Creates a new DAG.
   * @param root 
   * the root of the tree
   * @return 
   * the root of a corresponding DAG
   */
  public static TreeNode makeDAG(TreeNode root)
  {
    HashMap<HashNode, Integer> map = new HashMap<>();
    HashMap<Integer, HashNode> reverseMap = new HashMap<>();
    Table table = new Table(map);
    HashNode rootNode = makeTable(root, table, reverseMap);
    HashMap<HashNode, TreeNode> auxMap = new HashMap<>();
    return makeDAGHelper(rootNode, reverseMap, auxMap);
  }// makeDAG(TreeNode)

  /**
   * Populate two maps that are inverses of each other
   * with (hash)nodes from the tree starting at root
   * @param root
   *  the original tree
   * @param table
   *  forward map encapsulated with a counter 
   * @param reverseMap
   *  reverse of the table map
   * @post the two maps are populated
   * @return the hashnode representing the root 
   */
  private static HashNode makeTable(TreeNode root, Table table,
                                    HashMap<Integer, HashNode> reverseMap)
  {
    HashNode key = new HashNode(root);
    //if root is not a leaf : process all its children
    if (!root.isLeaf())
      {
        for (TreeNode child : root.getChildren())
          {
            HashNode childNode = makeTable(child, table, reverseMap);
            //update parent key with child Number
            key.addChildNumber(childNode.getNodeNumber());
          }//for
      }//if
    
    //for a leaf, check if it exists in the table
    if (!table.map.containsKey(key))
      {
        // if not, set the key's number 
        key.setNodeNumber(table.getAvailableNumber());
        // put it in the map and the reverse map
        table.map.put(key, key.getNodeNumber());
        reverseMap.put(key.getNodeNumber(), key);
        // increment the counter in the table
        table.incrementAvailableNum();
      }//if
    else
      // if it's already in the table, retrieve its number 
      key.setNodeNumber(table.map.get(key));

    return key;
  }//makeDAG(Treenode, Table, HashMap<Integer, HashNode>)

  /**
   * A helper method that builds a DAG given the
   * populated map of hashnodes
   * @param hashRoot
   *    the root of the tree represented in the map
   * @param reverseMap
   * @return
   */
  private static TreeNode makeDAGHelper(HashNode hashRoot,
                                        HashMap<Integer, HashNode> reverseMap,
                                        HashMap<HashNode, TreeNode> auxMap)
  {
    // if the corresponding TreeNode was already made, pass a reference to it
    if (auxMap.containsKey(hashRoot))
        return auxMap.get(hashRoot);
    
    // otherwise, make a new TreeNode
    TreeNode dagRoot = new TreeNode(hashRoot.getNodeVal());
    // recurse on its children, if there are any
    for (Integer kid : hashRoot.getChildrenNumbers())
      {
        HashNode temp = reverseMap.get(kid);
        dagRoot.addChild(makeDAGHelper(temp, reverseMap, auxMap));
      }
    auxMap.put(hashRoot, dagRoot);
    return dagRoot;
  }// makeDAGHelper(HashNode, HashMap<Integer, HashNode>)

  /**
   * A simple experiment
   * 
   */
  public static void main(String[] args)
    throws Exception
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    String code = "sum(neg(x), neg(x), x, y)";
    TreeNode root = Parser.parse(code);
    pen.println("After parsing, the tree is:\n " + root);
    TreeNode dagRoot = makeDAG(root);
    pen.println("After making a DAG, the tree is:\n " + dagRoot);
  }// main

}//DAG class
