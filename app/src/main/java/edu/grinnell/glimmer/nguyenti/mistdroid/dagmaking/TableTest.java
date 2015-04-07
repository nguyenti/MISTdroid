package dagmaking;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;



public class TableTest
{
  @Test
  public void settersGetterTest()
  {
    HashMap<HashNode, Integer> map = new HashMap<HashNode, Integer>();
    Table table = new Table(map);
    HashNode root = new HashNode("rgb");
    table.setRootNode(root);
    assertEquals(new HashNode("rgb"), table.getRootNode());
    table.incrementAvailableNum(); 
    int result =table.getAvailableNumber();
    assertEquals(2,result );
    assertEquals(map,table.getMap());
  }//setterGetterTest()
}//TableTest
