package parsing;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;


public class AltParser {

    /**
     * Parse Mist image code into a *-ary tree
     * TODO Can this parse simple statements like "x"?
     * 
     * @param code
     * @pre code represents valid Mist dialect
     * @return the resulting operation-value tree
     */
    public static AltTreeNode parse(String code) {
	String[] splits = magicSplit(code);
	AltTreeNode root = new AltTreeNode(splits[0]);
	return parse(splits, root);
    }

    /**
     * Magic split splits Mist dialect at commas and parens, throwing away
     * commas but keeping parens as members of the resulting array.
     * 
     * @param code
     * @pre code is valid Mist dialect - no ")(" back to back.
     * @return an array of operators, values, and parens, in order in which they
     *         appear in the code
     */
    static String[] magicSplit(String code) {
	// Throw away the commas
	String[] commaSplit = code.split(",");
	char[] withParens;
	String withoutParens;
	ArrayList<String> result = new ArrayList<>();
	char c;
	for (String str : commaSplit) {
	    withParens = str.toCharArray();
	    withoutParens = "";
	    for (int i = 0; i < withParens.length; i++) {
		if ((c = withParens[i]) == '(' || c == ')' || c == ',') {
		    // put the withoutParens so far into the ArrayList
		    if (!withoutParens.equals(""))
			result.add(new String(withoutParens));
		    // clear without parens
		    withoutParens = "";
		    // put the paren into the arraylist, but skip commas
		    if (c != ',')
			result.add(c + "");
		} 
		else
		    withoutParens += c;
	    }// for each char

	    // Deal with remaining withoutParens
	    if (!withoutParens.equals(""))
		result.add(new String(withoutParens));
	}// for each str in the commaSplit

	// put the resulting ArrayList into a regular array
	// TODO (DO WE NEED THIS? CAN WE JUST KEEP splits AS AN ARRAYLIST?)
	String[] toReturn = new String[result.size()];
	for (int i = 0; i < toReturn.length; i++) {
	    toReturn[i] = result.get(i).trim();
	}
	return toReturn;
    }// magicSplit(String)

    /**
     * The helper function for parsing that does the actual work
     * 
     * @param splits
     * @param root
     * @return
     */
    static AltTreeNode parse(String[] splits, AltTreeNode root) {
	AltTreeNode currentRoot = root;
	// the first string is the root, the next must be the paren
	for (int i = 2; i < splits.length; i++) {
	    if (splits[i].equals("(")) // open paren means previously added node
				       // has children
		currentRoot = (AltTreeNode) currentRoot.getChildren().get(currentRoot
			.numChildren() - 1); // will be adding to the last child
	    else if (splits[i].equals(")"))
		currentRoot = currentRoot.parent; // closed paren means we need
						  // to return to the parent
	    else
		currentRoot.addChild(splits[i]); // otherwise, add the next
						 // child to the current root
	}
	return root;
    }// parse(String[], AltTreeNode)

    public static void main(String[] args) {
	PrintWriter pen = new PrintWriter(System.out, true);
	String test = "rgb(sq(sum(x,y)),x,y)";
	String okay = "rgb(wsum(x, y, y, t.s), sum(sum(wsum(x, x, y, neg(t.s), x), wsum(y, y, x, neg(t.s), y)), sum(wsum(y, x, x, t.s), wsum(x, y, y, t.s))), sum(wsum(x, x, y, neg(t.s), x), wsum(y, y, x, neg(t.s), y)))";
	String zoes = "mult(sum(cos(square(sum(sin(x), cos(mult(t.m, 5))))), cos(square(sum(sin(y), cos(mult(t.m, 5)))))), 10)";
	pen.println("Printing out test tree:\n" + test + "\n"
		+ parse(test));
	pen.println("Printing out okay tree:\n" + okay + "\n"
		+ parse(okay));
	pen.println("Printing out Zoe's tree:\n" + zoes + "\n"
		+ parse(zoes));

    }

}
