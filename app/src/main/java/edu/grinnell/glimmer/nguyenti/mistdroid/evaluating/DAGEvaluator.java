package edu.grinnell.glimmer.nguyenti.mistdroid.evaluating;

import java.io.PrintWriter;
import java.util.HashMap;

import edu.grinnell.glimmer.nguyenti.mistdroid.dagmaking.DAG;
import edu.grinnell.glimmer.nguyenti.mistdroid.data.Pixel;
import edu.grinnell.glimmer.nguyenti.mistdroid.parsing.*;
//import dagmaking.DAG;
//import parsing.*;

/**
 * A class for evaluating DAGs.
 * 
 * @author Mist Parser team
 */
public class DAGEvaluator
{

  TreeNode dag;
  // Hashtable of MIST function strings and their implementations
  HashMap<String, Function> functions;

  Function function1[] = {
                          // Sum
                          new Function()
                            {
                              public Pixel apply(Pixel[] args)
                              {
                                Pixel sum = new Pixel(0, 0, 0);
                                for (Pixel arg : args)
                                  {
                                    sum.add(arg);
                                  }
                                sum.range();
                                return sum;
                              }
                            }, //Sum
                          // Wrap sum
                          new Function()
                            {
                              public Pixel apply(Pixel[] args)
                              {
                                Pixel sum = new Pixel(0, 0, 0);
                                for (Pixel arg : args)
                                  {
                                    sum.add(arg);
                                  }
                                sum.wrap();
                                return sum;
                              }
                            }, // Wrap sum
                          // Multiply
                          new Function()
                            {
                              public Pixel apply(Pixel[] args)
                              {
                                Pixel prod = new Pixel(1, 1, 1);
                                for (Pixel arg : args)
                                  {
                                    prod.multiplyBy(arg);
                                  }
                                prod.range();
                                return prod;
                              }
                            }, // Multiply
                          // Average
                          new Function()
                            {
                              public Pixel apply(Pixel[] args)
                              {
                                Pixel sum = new Pixel(0, 0, 0);
                                for (Pixel arg : args)
                                  {
                                    sum.add(arg);
                                  }
                                sum.range();
                                sum.multiplyBy(new Pixel(
                                                            1 / ((double) args.length)));
                                return sum;
                              }
                            }, // Average
                          // Square
                          new Function()
                            {
                              public Pixel apply(Pixel[] args)
                                throws Exception
                              {
                                if (args.length != 1)
                                  throw new Exception();
                                Pixel result = new Pixel(1,1,1);
                                result.multiplyBy(args[0]);
                                result.multiplyBy(args[0]);
                                result.range();
                                return result;
                              }
                            }, // Square
                          // Negate
                          new Function()
                            {
                              public Pixel apply(Pixel[] args)
                                throws Exception
                              {
                                if (args.length != 1)
                                  throw new Exception();
                                Pixel result = new Pixel();
                                for (int i = 0; i < 3; i++)
                                  {
                                    result.components[i] =
                                        -1.0 * args[0].components[i];
                                  } // for each component
                                result.range();
                                return result;
                              }
                            }, // Negate
                          // Sine
                          new Function()
                            {
                              public Pixel apply(Pixel[] args)
                                throws Exception
                              {
                                if (args.length != 1)
                                  throw new Exception();
                                Pixel result = new Pixel();
                                for (int i = 0; i < 3; i++)
                                  {
                                    result.components[i] =
                                        Math.sin(args[0].components[i]);
                                  }
                                result.range();
                                return result;
                              }
                            }, //Sine
                          // Cosine
                          new Function()
                            {
                              public Pixel apply(Pixel[] args)
                                throws Exception
                              {
                                if (args.length != 1)
                                  throw new Exception();
                                Pixel result = new Pixel();
                                for (int i = 0; i < 3; i++)
                                  {
                                    result.components[i] =
                                        Math.cos(args[0].components[i]);
                                  } // for each component
                                result.range();
                                return result;
                              }
                            }, //Cosine
                          // Absolute value
                          new Function()
                            {
                              public Pixel apply(Pixel[] args)
                                throws Exception
                              {
                                if (args.length != 1)
                                  throw new Exception();
                                Pixel result = new Pixel();
                                for (int i = 0; i < 3; i++)
                                  {
                                    result.components[i] =
                                        Math.abs(args[0].components[i]);
                                  }
                                result.range();
                                return result;
                              }
                            },//Abs
                          // Sign
                          new Function()
                            {
                              public Pixel apply(Pixel[] args)
                                throws Exception
                              {
                                if (args.length != 1)
                                  throw new Exception();
                                if (args[0].components[0] > 0
                                    && args[0].components[1] > 0
                                    && args[0].components[2] > 0)
                                  return new Pixel(1.0);
                                else
                                  return new Pixel(-1.0);
                              }
                            }, // Sign
                          // If
                          new Function()
                            {
                              public Pixel apply(Pixel[] args)
                                throws Exception
                              {
                                if (args.length != 3)
                                  throw new Exception();
                                // If each component of the test is positive
                                if (args[0].components[0] > 0
                                    && args[0].components[1] > 0
                                    && args[0].components[2] > 0)
                                  // return the first option
                                  return args[1];
                                else
                                  // otherwise, the second option
                                  return args[2];
                              }
                            },// If
                          // RGB
                          new Function()
                            {
                              public Pixel apply(Pixel[] args)
                                throws Exception
                              {
                                if (args.length != 3)
                                  throw new Exception();
                                // Take the first component of the first child, 
                                // second of the second child, etc
                                Pixel result =
                                    new Pixel(args[0].components[0],
                                                 args[1].components[1],
                                                 args[2].components[2]);
                                result.range();
                                return result;
                              }
                            } // RGB
  }; //functions array

  public enum FUN_NAMES
    {
      FUN_SUM, //0
      FUN_WSUM, //1
      FUN_MULT, //2
      FUN_AVG, //3
      FUN_SQR, //4
      FUN_NEG, //5
      FUN_SIN, //6
      FUN_COS, //7
      FUN_ABS, //8
      FUN_SIGN, //9
      FUN_IF, //10
      FUN_RGB; //11
    };

  /**
   *  Build an DAGEvaluator with a complete functions hashtable
   */
  public DAGEvaluator(TreeNode d)
  {
    dag = d;
    functions = new HashMap<>();
    populateFunctionMap();
  }// DAGEvaluator()


    public Pixel evaluate(double x, double y) throws Exception {

       Pixel p = evaluate(dag, x, y);
       clearDAG(dag);
       return p;
    }
  /**
   * Evaluate a DAG helper.
   * @param root of the DAG
   * @return the RGB value of the DAG
   * @throws Exception 
   *    if something has wrong arguments or
   *    if a function is not in the hash
   */
  private Pixel evaluate(TreeNode root, double x, double y)
    throws Exception
  {
    // If set, this has already been evaluated 
    if (root.isSet())
      return root.getEvaluation();

    // If leaf, get context parameters
    if (root.isLeaf())
      {
        //STUB
        root.set();
        Pixel val;
        if (root.getNodeVal().equals("x"))
            val = new Pixel(x);
         else
            val = new Pixel(y);
          root.evaluate(val);
          return val;
      }// if leaf

    // Otherwise, recurse on each child
    Pixel[] args = new Pixel[root.numChildren()];
    int i = 0;
    for (TreeNode kid : root.getChildren())
      args[i++] = evaluate(kid, x, y);

    // Apply root's function to the children arguments
    root.set();
    Function f = getFunction(root.getNodeVal()); // using enum + array
    //Function f = functions.get(root.getNodeVal()); // using HashMap
    if (f == null)
      throw new Exception(root.getNodeVal() + " is not a valid MIST function");
    root.evaluate(f.apply(args));
    return root.getEvaluation();
  }// evaluate(TreeNode)

  /**
   * Clear the DAG
   */
  public void clearDAG(TreeNode d) {
    if (d.isSet()) {
      d.clear();
      for (TreeNode kid : d.getChildren())
          clearDAG(kid);
    }
  }// clearDAG

  /**
   * Get environmental variables like x, y, t.s, etc.
   * @param nodeVal, string representation of the
   *    desired environmental variable
   * @return (1,1,1) <- STUB
   */
  private static Pixel getContext(String nodeVal)
  {
    //STUB - assume all context values are 1.0
    return new Pixel(1.0, 1.0, 1.0);
  }

  /**
   * Get the function object based in its string representation
   * @param funName, string representation of the function
   * @return the corresponding function object
   * @throws Exception 
   */
  Function getFunction(String funName)
    throws Exception
  {
    try
      {
        FUN_NAMES name = FUN_NAMES.valueOf("FUN_" + funName.toUpperCase());
        return function1[name.ordinal()];
      } // try
    catch (Exception e)
      {
        throw new Exception("Invalid function name:" + funName);
      } // catch
  } // getFunction

  /**
   * Populate the map with function strings and corresponding implementations
   */
  void populateFunctionMap()
  {
    // Sum
    functions.put("sum", new Function()
                    {
                      public Pixel apply(Pixel[] args)
                      {
                        Pixel sum = new Pixel(0, 0, 0);
                        for (Pixel arg : args)
                          {
                            sum.add(arg);
                          }// for each argument
                        sum.range();
                        return sum;
                      }
                    });
    // Multiplication
    functions.put("mult", new Function()
                    {
                      public Pixel apply(Pixel[] args)
                      {
                        Pixel prod = new Pixel(1, 1, 1);
                        for (Pixel arg : args)
                          {
                            prod.multiplyBy(arg);
                          }// for each argument
                        prod.range();
                        return prod;
                      }
                    });
    // Average
    functions.put("avg", new Function()
                    {
                      public Pixel apply(Pixel[] args)
                      {
                        Pixel sum = new Pixel(0, 0, 0);
                        for (Pixel arg : args)
                          {
                            sum.add(arg);
                          }// for each argument
                        sum.range();
                        sum.multiplyBy(new Pixel(1 / ((double) args.length)));
                        return sum;
                      }
                    });
    // Wrap sum
    functions.put("wsum", new Function()
                    {
                      public Pixel apply(Pixel[] args)
                      {
                        Pixel sum = new Pixel(0, 0, 0);
                        for (Pixel arg : args)
                          {
                            sum.add(arg);
                          }// for each argument
                        sum.wrap();
                        return sum;
                      }
                    });
    // Negate
    functions.put("neg", new Function()
                    {
                      public Pixel apply(Pixel[] args)
                        throws Exception
                      {
                        if (args.length != 1)
                          throw new Exception();
                        Pixel result = new Pixel();
                        for (int i = 0; i < 3; i++)
                          {
                            result.components[i] = -1.0 * args[0].components[i];
                          }// for each component
                        result.range();
                        return result;
                      }
                    });
    // Sine
    functions.put("sin", new Function()
                    {
                      public Pixel apply(Pixel[] args)
                        throws Exception
                      {
                        if (args.length != 1)
                          throw new Exception();
                        Pixel result = new Pixel();
                        for (int i = 0; i < 3; i++)
                          {
                            result.components[i] =
                                Math.sin(args[0].components[i]);
                          }// for each component
                        result.range();
                        return result;
                      }
                    });
    // Cosine
    functions.put("cos", new Function()
                    {
                      public Pixel apply(Pixel[] args)
                        throws Exception
                      {
                        if (args.length != 1)
                          throw new Exception();
                        Pixel result = new Pixel();
                        for (int i = 0; i < 3; i++)
                          {
                            result.components[i] =
                                Math.cos(args[0].components[i]);
                          }// for each component
                        result.range();
                        return result;
                      }
                    });
    // Square root
    functions.put("sqr", new Function()
                    {
                      public Pixel apply(Pixel[] args)
                        throws Exception
                      {
                        if (args.length != 1)
                          throw new Exception();
                        Pixel result = new Pixel();
                        for (int i = 0; i < 3; i++)
                          { // Notice that we allow negative inputs 
                            result.components[i] =
                                Math.sqrt(Math.abs(args[0].components[i]));
                          }// for each component
                        result.range();
                        return result;
                      }
                    });
    // Absolute value
    functions.put("abs", new Function()
                    {
                      public Pixel apply(Pixel[] args)
                        throws Exception
                      {
                        if (args.length != 1)
                          throw new Exception();
                        Pixel result = new Pixel();
                        for (int i = 0; i < 3; i++)
                          { // Notice that we allow negative inputs 
                            result.components[i] =
                                Math.abs(args[0].components[i]);
                          }// for each component
                        result.range();
                        return result;
                      }
                    });

    // Non-component-wise sign
    functions.put("sign", new Function()
                    {
                      public Pixel apply(Pixel[] args)
                        throws Exception
                      {
                        if (args.length != 1)
                          throw new Exception();

                        if (args[0].components[0] > 0
                            && args[0].components[1] > 0
                            && args[0].components[2] > 0)
                          return new Pixel(1.0);
                        else
                          return new Pixel(-1.0);
                      }
                    });
    // Non-component-wise if
    functions.put("if", new Function()
                    {
                      public Pixel apply(Pixel[] args)
                        throws Exception
                      {
                        if (args.length != 3)
                          throw new Exception();
                        // If each component of the test is positive
                        if (args[0].components[0] > 0
                            && args[0].components[1] > 0
                            && args[0].components[2] > 0)
                          // return the first option
                          return args[1];
                        else
                          // otherwise, the second option
                          return args[2];
                      }
                    });
    // RGB
    functions.put("rgb", new Function()
                    {
                      public Pixel apply(Pixel[] args)
                        throws Exception
                      {
                        if (args.length != 3)
                          throw new Exception();
                        // Take the first component of the first child, 
                        // second of the second child, etc
                        Pixel result =
                            new Pixel(args[0].components[0],
                                         args[1].components[1],
                                         args[2].components[2]);
                        result.range();
                        return result;
                      }
                    });

  }// populateFunctionMap

  /** 
   * Helper interface for Function objects
   */
  public interface Function
  {
    public Pixel apply(Pixel[] args)
      throws Exception;
  }// interface Function


}// DAGEvaluator class
