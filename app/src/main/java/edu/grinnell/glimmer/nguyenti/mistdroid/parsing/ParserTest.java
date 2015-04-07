package parsing;
import static org.junit.Assert.*;

import org.junit.Test;



public class ParserTest {

    @Test
    public void simpleTest() throws Exception {
	TreeNode res = Parser.parse("x");
	TreeNode correct = new TreeNode("x");
	System.out.println(res);
	assertTrue(res.equals(correct));
	
	try{
	TreeNode res1 = Parser.parse("");
	}
	catch( Exception e){
	   assertEquals(e.getMessage(),"Code is an empty string");   
	}
	
	
	TreeNode res2 = Parser.parse("sin(x)");
	TreeNode testSin = new TreeNode("sin");
	testSin.addChild("x");
	assertTrue(res2.equals(testSin));
	
	//function with 3 parameters
	String testString3 = "rgb(sin(x),x,y)";
	TreeNode case3 = Parser.parse(testString3);
	TreeNode case3correct = AltParser.parse(testString3);
	assertTrue(case3.equals(case3correct));
    }//simpleTest
    
    @Test
    public void manyParensTest() throws Exception{
	String input = "sin(sin(sin(sin(sin(sin(sin(x)))))))";
	TreeNode recursive = Parser.parse(input);
	TreeNode iterative = AltParser.parse(input);
	assertTrue(recursive.equals(iterative));
    }//manyParens
    
    @Test
    public void whiteSpaceTest() throws Exception{
	String input = "rgb(sq(sum(x,y))   ,x,y)";
	TreeNode recursive = Parser.parse(input);
        System.out.println(recursive);
	TreeNode iterative = AltParser.parse(input);
	System.out.println(iterative);
	assertTrue(recursive.equals(iterative));
    }//whiteSpaceTest()
}
