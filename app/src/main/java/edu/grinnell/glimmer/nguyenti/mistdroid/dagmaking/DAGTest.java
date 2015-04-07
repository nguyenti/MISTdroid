package dagmaking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;

import parsing.Parser;
import parsing.TreeNode;

public class DAGTest
{

  /*
   * Helper procedures for testing 
   */
  /**
   * Counts the number of nodes in a DAG.
   * @param root
   * @return the number of nodes
   */
  public int countNodes(TreeNode root)
  {
    return countNodes(root, new HashSet<TreeNode>());
  }//countNodes(TreeNode)

  public int countNodes(TreeNode root, HashSet<TreeNode> seen)
  {
    //Simplest form of problem : when root has no children
    if (seen.contains(root))
      return 0;
    int counter = 0;
    // add root to set
    seen.add(root);
    counter++;
    //add all the children to set
    for (TreeNode child : root.getChildren())
      counter += countNodes(child, seen);
    return counter;
  }//countNodes(TreeNode,HashSet<TreeNode>)

  /**
   * Tests for when the root Node from non directed graph is a leaf
   */
  @Test
  public void rootLeafTest()
  {
    TreeNode root = new TreeNode("x");
    assertTrue("leaf tree", root.equals(DAG.makeDAG(root)));
  }//rootLeafTest

  @Test
  public void oneLevelTreeTest()
    throws Exception
  {
    TreeNode tree = Parser.parse("sum(x, x)");
    TreeNode dag = DAG.makeDAG(tree);
    assertTrue("tree and dag must have the same string representation",
               dag.equals(tree));
    TreeNode treeKid1 = tree.getChildren().get(0);
    TreeNode treeKid2 = tree.getChildren().get(1);
    TreeNode dagKid1 = dag.getChildren().get(0);
    TreeNode dagKid2 = dag.getChildren().get(1);
    assertTrue("two children are the same", dagKid1.equals(dagKid2));
    // hashCode() is not overridden for TreeNode class, so it depends on the reference
    assertFalse("two identical tree children must not have the same hash",
                treeKid1.hashCode() == treeKid2.hashCode()); // PASS
    assertTrue("two identical dag children must have the same hash",
               dagKid1.hashCode() == dagKid2.hashCode()); // PASS
    // checking references directly
    assertFalse("two identical tree children must not have the same reference",
                treeKid1 == treeKid2); // PASS
    assertTrue("two identical children must have the same reference",
               dagKid1 == dagKid2); // PASS

  }

  @Test
  public void twoLevelTreeTest()
    throws Exception
  {
    TreeNode tree = Parser.parse("wsum(mult(x, x),mult(x, x), x)");
    TreeNode dag = DAG.makeDAG(tree);
    assertTrue("tree and dag must have the same string representation",
               dag.equals(tree));
    TreeNode multKid1 = dag.getChildren().get(0);
    TreeNode multKid2 = dag.getChildren().get(1);
    TreeNode xKidLevel2 = dag.getChildren().get(2);
    TreeNode xKidLevel3 = multKid1.getChildren().get(0);

    assertTrue("two mult subtrees must have the same hash",
               multKid1.hashCode() == multKid2.hashCode());
    assertTrue("two x subtrees on different levels must have the same hash",
               xKidLevel2.hashCode() == xKidLevel3.hashCode());
  }

  @Test
  public void testNodeCount()
    throws Exception
  {
    //Testing for single node:
    TreeNode root = new TreeNode("x");
    assertEquals("test root count", 1, countNodes(root));
    TreeNode dagRoot = DAG.makeDAG(root);
    assertEquals("test root count DAG", 1, countNodes(dagRoot));
    //both DAG and Tree must have same number of nodes for a single node
    assertEquals(countNodes(dagRoot), countNodes(root));

    //Testing one level tree count :
    TreeNode tree = Parser.parse("sum(x, x)");
    TreeNode dag = DAG.makeDAG(tree);
    //There are 3 nodes in tree 
    assertEquals("asserting num Nodes in one level tree", 3, countNodes(tree));
    //There are 2 nodes in the equivalent DAG
    assertEquals("2 child references point to the same node", 2,
                 countNodes(dag));

    //Testing a two level tree count:
    TreeNode twoLeveltree = Parser.parse("wsum(mult(x, x),mult(x, x), x)");
    TreeNode twoLeveldag = DAG.makeDAG(twoLeveltree);
    //There are 8 nodes in tree 
    assertEquals("every reference to a node is a distinct node structure", 8,
                 countNodes(twoLeveltree));
    //There are 3 nodes in dag
    assertEquals("some references are to the same node", 3,
                 countNodes(twoLeveldag));
    //Testing 
  }//testNumberofNodes()
}
