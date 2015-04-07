package dagmaking;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;


/** 
 * Unit testing for HashNode class: Test the different methods
 * in HashNode
 * @author Albert Owusu-Asare
 */
public class HashNodeTest
{
  
 @Test
 public void setterGetterTests(){
   HashNode sample = new HashNode("sum");
   sample.addChildNumber(1);
   sample.addChildNumber(2);
   sample.setNodeNumber(3);
   int result = sample.getNodeNumber();
   assertEquals(3,result);
   assertEquals("sum",sample.getNodeVal());
   ArrayList<Integer> expected = new ArrayList<Integer>();
   expected.add(1);
   expected.add(2);
   assertEquals(expected, sample.getChildrenNumbers()); 
 }//setterGetterTests

  @Test
  public void toStringTest()
  {
    HashNode testNode = new HashNode("x");
    String expectedResults = "[ (0) x () ]";
    assertEquals(expectedResults,testNode.toString());
  }//toStringTest
  
  @Test 
  public void equalsTest(){
    HashNode allNull = new HashNode("");
    assertEquals(new HashNode(""), allNull);
    
    HashNode sample1 = new HashNode("x");
    assertEquals(new HashNode("x"), sample1);
   
    HashNode sample2 = new HashNode("y");
    sample2.addChildNumber(1);
    assertEquals(sample2, sample2);
    
    sample2.setNodeNumber(3);
    assertEquals(sample2, sample2);
  }//equalsTest()
  
  @Test
  public void testHashCode(){
   // we shall test the functionality of hashCode() by using the 
   // HashMaps.objects with the same HashCode should be in the same bucket
    HashMap<HashNode, Integer> map = new HashMap<HashNode,Integer>();
    HashNode sample = new HashNode("x");
    sample.addChildNumber(1);
    map.put(sample, 1);
    assertEquals(1,map.keySet().size());
    HashNode sample2 = new HashNode("x");
    sample2.addChildNumber(2);
  }//testHashCode()

}
