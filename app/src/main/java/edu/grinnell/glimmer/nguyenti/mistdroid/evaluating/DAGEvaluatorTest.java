package evaluating;

import static org.junit.Assert.*;

import org.junit.Test;

import evaluating.DAGEvaluator.Function;

public class DAGEvaluatorTest
{
  
  
  @Test
  public void testsum() throws Exception{
    
    Function fun1 = DAGEvaluator.function1[0];
    RGBValue [] rgb1  = {new RGBValue(0.2,0.5,-0.1),new RGBValue (1.0,-0.1,0.3)};
    RGBValue exp1 = new RGBValue(1.0,0.4,0.2);
    assertEquals (exp1,fun1.apply(rgb1));
    
    RGBValue [] rgb2  = {new RGBValue(-0.2,-1.0,0),new RGBValue (-1.0,-0.1,0.3)};
    RGBValue exp2 = new RGBValue(-1.0,-1.0,0.3);
    assertEquals (exp2,fun1.apply(rgb2));
    
    RGBValue [] rgb3  = {new RGBValue(0,0,0),new RGBValue (0,0,0)};
    RGBValue exp3 = new RGBValue(0,0,0);
    assertEquals (exp3,fun1.apply(rgb3));
    
    RGBValue [] rgb4  = {new RGBValue(-0.2,-1.0,0),new RGBValue (-1.0,-0.1,0.3),new RGBValue (1.0,-0.1,0.3),new RGBValue (0.3,0.1,0.3)};
    RGBValue exp4 = new RGBValue(0.1,-1.0,0.9);
    assertEquals (exp4,fun1.apply(rgb4));
    
    RGBValue [] rgb5  = {new RGBValue(1,1,1),new RGBValue (1,1,1)};
    RGBValue exp5 = new RGBValue(1,1,1);
    assertEquals (exp5,fun1.apply(rgb5));
  }
  
  @Test
  public void testwsum() throws Exception{
    
    Function fun1 = DAGEvaluator.function1[1];
    RGBValue [] rgb1  = {new RGBValue(0.2,0.5,-0.1),new RGBValue (1.0,-0.1,0.3)};
    RGBValue exp1 = new RGBValue(-0.8,0.4,0.2);
    assertEquals (exp1,fun1.apply(rgb1));
    
    RGBValue [] rgb2  = {new RGBValue(-0.2,-1.0,0),new RGBValue (-1.0,-0.1,0.3)};
    RGBValue exp2 = new RGBValue(0.8,0.9,0.3);
    assertEquals (exp2,fun1.apply(rgb2));
    
    RGBValue [] rgb3  = {new RGBValue(0,0,0),new RGBValue (0,0,0)};
    RGBValue exp3 = new RGBValue(0,0,0);
    assertEquals (exp3,fun1.apply(rgb3));
    
    RGBValue [] rgb4  = {new RGBValue(-0.2,-1.0,0),new RGBValue (-1.0,-0.1,0.3),new RGBValue (1.0,-0.1,0.3),new RGBValue (0.3,0.1,0.5)};
    RGBValue exp4 = new RGBValue(0.1,0.9,-0.9);
    assertEquals (exp4,fun1.apply(rgb4));
    
    RGBValue [] rgb5  = {new RGBValue(1,1,1),new RGBValue (1,1,1)};
    RGBValue exp5 = new RGBValue(0,0,0);
    assertEquals (exp5,fun1.apply(rgb5));
  }
  @Test
  public void testmult() throws Exception{
    
    Function fun1 = DAGEvaluator.function1[2];
    RGBValue [] rgb1  = {new RGBValue(0.2,0.5,-0.1),new RGBValue (1.0,-0.1,0.3)};
    RGBValue exp1 = new RGBValue(0.2,-0.05,-0.03);
    assertEquals (exp1,fun1.apply(rgb1));
    
    RGBValue [] rgb2  = {new RGBValue(-0.2,-1.0,0),new RGBValue (-1.0,-0.1,0.3)};
    RGBValue exp2 = new RGBValue(0.2,0.1,0);
    assertEquals (exp2,fun1.apply(rgb2));
    
    RGBValue [] rgb3  = {new RGBValue(0,0,0),new RGBValue (0,0,0)};
    RGBValue exp3 = new RGBValue(0,0,0);
    assertEquals (exp3,fun1.apply(rgb3));
    
    RGBValue [] rgb4  = {new RGBValue(-0.2,-1.0,0),new RGBValue (-1.0,-0.1,0.3),new RGBValue (1.0,-0.1,0.3),new RGBValue (0.3,0.1,0.5)};
    RGBValue exp4 = new RGBValue(0.06,-0.001,0);
    assertEquals (exp4,fun1.apply(rgb4));
    
    RGBValue [] rgb5  = {new RGBValue(1,1,1),new RGBValue (1,1,1)};
    RGBValue exp5 = new RGBValue(1,1,1);
    assertEquals (exp5,fun1.apply(rgb5));
  }
  
  @Test
  public void testavg() throws Exception{
   
    Function fun1 = DAGEvaluator.function1[3];
    RGBValue [] rgb1  = {new RGBValue(0.2,0.5,-0.1),new RGBValue (1.0,-0.1,0.3)};
    RGBValue exp1 = new RGBValue(0.5,0.2,0.1);
    assertEquals (exp1,fun1.apply(rgb1));
    
    RGBValue [] rgb2  = {new RGBValue(-0.2,-1.0,0),new RGBValue (-1.0,-0.1,0.3)};
    RGBValue exp2 = new RGBValue(-0.5,-0.5,0.15);
    assertEquals (exp2,fun1.apply(rgb2));
    
    RGBValue [] rgb3  = {new RGBValue(0,0,0),new RGBValue (0,0,0)};
    RGBValue exp3 = new RGBValue(0,0,0);
    assertEquals (exp3,fun1.apply(rgb3));
    
    RGBValue [] rgb4  = {new RGBValue(-0.2,-1.0,0),new RGBValue (-1.0,-0.1,0.3),new RGBValue (1.0,-0.1,0.3),new RGBValue (0.3,0.1,0.3)};
    RGBValue exp4 = new RGBValue(0.025,-0.25,0.225);
    assertEquals (exp4,fun1.apply(rgb4));
    
    RGBValue [] rgb5  = {new RGBValue(1,1,1),new RGBValue (1,1,1),new RGBValue (1,1,1)};
    RGBValue exp5 = new RGBValue(1.0/3,1.0/3,1.0/3);
    assertEquals (exp5,fun1.apply(rgb5));

  }
  
  @Test
  public void testsqr() throws Exception{
   
    Function fun1 = DAGEvaluator.function1[4];
    RGBValue [] rgb1  = {new RGBValue(0.2,0.5,-0.1)};
    RGBValue exp1 = new RGBValue(0.04,0.25,0.01);
    assertEquals (exp1,fun1.apply(rgb1));
    
    RGBValue [] rgb2  = {new RGBValue(-0.2,-1.0,0)};
    RGBValue exp2 = new RGBValue(0.04,1.0,0.0);
    assertEquals (exp2,fun1.apply(rgb2));
    
    RGBValue [] rgb3  = {new RGBValue(0,0,0)};
    RGBValue exp3 = new RGBValue(0,0,0);
    assertEquals (exp3,fun1.apply(rgb3));
    
    RGBValue [] rgb4  = {new RGBValue(-0.2,-1.0,0.5)};
    RGBValue exp4 = new RGBValue(0.04,1.0,0.25);
    assertEquals (exp4,fun1.apply(rgb4));
    
    RGBValue [] rgb5  = {new RGBValue(1,1,1)};
    RGBValue exp5 = new RGBValue(1.0,1.0,1.0);
    assertEquals (exp5,fun1.apply(rgb5));
  }
  
  @Test
  public void testneg() throws Exception{
   
    Function fun1 = DAGEvaluator.function1[5];
    RGBValue [] rgb1  = {new RGBValue(0.2,0.5,-0.1)};
    RGBValue exp1 = new RGBValue(-0.2,-0.5,0.1);
    assertEquals (exp1,fun1.apply(rgb1));
    
    RGBValue [] rgb2  = {new RGBValue(-0.2,-1.0,0)};
    RGBValue exp2 = new RGBValue(0.2,1.0,0);
    assertEquals (exp2,fun1.apply(rgb2));
    
    RGBValue [] rgb3  = {new RGBValue(0,0,0)};
    RGBValue exp3 = new RGBValue(0,0,0);
    assertEquals (exp3,fun1.apply(rgb3));
    
    RGBValue [] rgb4  = {new RGBValue(-0.2,-1.0,0.5)};
    RGBValue exp4 = new RGBValue(0.2,1.0,-0.5);
    assertEquals (exp4,fun1.apply(rgb4));
    
    RGBValue [] rgb5  = {new RGBValue(1,1,1)};
    RGBValue exp5 = new RGBValue(-1.0,-1.0,-1.0);
    assertEquals (exp5,fun1.apply(rgb5));
  }
  
  
  
}
