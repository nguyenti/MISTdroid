package dagmaking;
import java.util.HashMap;



public class Table
{
  /**
  * Encapsulates a HashMap and an Integer counter
  * @author Albert Owusu-Asare
  * @since 1.0
  */

  HashMap<HashNode, Integer> map;
  Integer availableNumber;
  HashNode rootNode;

  /**
   * Constructs a <code>table</code>
   * @param map a HashMap of key <code>HashNode</code> value:
   *            <code>Integer</code>
   */
  public Table(HashMap<HashNode, Integer> map)
  {
    this.map = map;
    this.availableNumber = 1;
    this.rootNode = null;
  }// Table(HashMap)
  
  /**
   * Sets the rootNode
   * @param rootNode
   */
  public void setRootNode(HashNode rootNode)
  {
    this.rootNode = rootNode;
  }//setRootNode(HashNode)
  
  /**
   * Increments the availableNumber counter to help associate 
   * values for keys in HashMap.
   */
  public void incrementAvailableNum()
  {
    this.availableNumber++;
  }//incrementAvailableNum()

  /**
   * Gets the rootNode
   * @return
   */
  public HashNode getRootNode()
  {
    return this.rootNode;
  }//getRootNode()

  /**
   * Returns the available number
   */
  public Integer getAvailableNumber()
  {
    return availableNumber;
  }// getAvailableNumber()  
  
  /**
   * Returns the HashMap
   */
  public HashMap<HashNode,Integer> getMap(){
    return this.map;
  }//getMap()  
  
  /**
   * Convert to string
   */
  public String toString()
  {
    return map.toString() + " #" + this.availableNumber;
  }
}// Table

