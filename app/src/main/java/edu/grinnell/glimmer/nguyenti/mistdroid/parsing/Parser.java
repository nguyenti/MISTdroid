
package parsing;
import java.io.PrintWriter;
import java.util.*;




/**
 * Main Parser
 * 
 * @author Albert Owusu-Asare
 * 
 */
public class Parser {

    public static TreeNode parse(String code) throws Exception {
	if (code.isEmpty())
	    throw new Exception("Code is an empty string");
	Tokenizer t = new Tokenizer(code);
	return parseHelper(t);
    }

    static TreeNode parseHelper(Tokenizer t) throws Exception {
	TreeNode temp = new TreeNode(t.next());
	String peeked = t.peek();
	if (peeked.equals("(")) {
	    t.next(); // consume the open paren
	    temp.addChild(parseHelper(t));
	    while (t.peek().equals(",")) {
		t.next(); // consume the comma
		temp.addChild(parseHelper(t));
	    }// while comma
	    t.next(); // consume close paren
	    return temp;
	}// if open paren
	else if (peeked.equals(")") || peeked.equals(",") || peeked.isEmpty()) {
	    return temp;
	}// if close paren or comma or empty
	else
	    throw new Exception("Unexpected token " + peeked + " at index "
		    + t.index);
    }// parseHelper

    

}
