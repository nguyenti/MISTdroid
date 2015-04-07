package evaluating;

import java.io.PrintWriter;
import java.util.HashMap;
import dagmaking.DAG;
import parsing.*;

/**
 * A class for evaluating DAGs.
 * 
 * @author Mist Parser team
 */
public class DAGEvaluator
{
  // Hashtable of MIST function strings and their implementations
  HashMap<String, Function> functions;

  Function function1[] = {
                          // Sum
                          new Function()
                            {
                              public RGBValue apply(RGBValue[] args)
                              {
                                RGBValue sum = new RGBValue(0, 0, 0);
                                for (RGBValue arg : args)
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
                              public RGBValue apply(RGBValue[] args)
                              {
                                RGBValue sum = new RGBValue(0, 0, 0);
                                for (RGBValue arg : args)
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
                              public RGBValue apply(RGBValue[] args)
                              {
                                RGBValue prod = new RGBValue(1, 1, 1);
                                for (RGBValue arg : args)
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
                              public RGBValue apply(RGBValue[] args)
                              {
                                RGBValue sum = new RGBValue(0, 0, 0);
                                for (RGBValue arg : args)
                                  {
                                    sum.add(arg);
                                  }
                                sum.range();
                                sum.multiplyBy(new RGBValue(
                                                            1 / ((double) args.length)));
                                return sum;
                              }
                            }, // Average
                          // Square
                          new Function()
                            {
                              public RGBValue apply(RGBValue[] args)
                                throws Exception
                              {
                                if (args.length != 1)
                                  throw new Exception();
                                RGBValue result = new RGBValue(1,1,1);
                                result.multiplyBy(args[0]);
                                result.multiplyBy(args[0]);
                                result.range();
                                return result;
                              }
                            }, // Square
                          // Negate
                          new Function()
                            {
                              public RGBValue apply(RGBValue[] args)
                                throws Exception
                              {
                                if (args.length != 1)
                                  throw new Exception();
                                RGBValue result = new RGBValue();
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
                              public RGBValue apply(RGBValue[] args)
                                throws Exception
                              {
                                if (args.length != 1)
                                  throw new Exception();
                                RGBValue result = new RGBValue();
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
                              public RGBValue apply(RGBValue[] args)
                                throws Exception
                              {
                                if (args.length != 1)
                                  throw new Exception();
                                RGBValue result = new RGBValue();
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
                              public RGBValue apply(RGBValue[] args)
                                throws Exception
                              {
                                if (args.length != 1)
                                  throw new Exception();
                                RGBValue result = new RGBValue();
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
                              public RGBValue apply(RGBValue[] args)
                                throws Exception
                              {
                                if (args.length != 1)
                                  throw new Exception();
                                if (args[0].components[0] > 0
                                    && args[0].components[1] > 0
                                    && args[0].components[2] > 0)
                                  return new RGBValue(1.0);
                                else
                                  return new RGBValue(-1.0);
                              }
                            }, // Sign
                          // If
                          new Function()
                            {
                              public RGBValue apply(RGBValue[] args)
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
                              public RGBValue apply(RGBValue[] args)
                                throws Exception
                              {
                                if (args.length != 3)
                                  throw new Exception();
                                // Take the first component of the first child, 
                                // second of the second child, etc
                                RGBValue result =
                                    new RGBValue(args[0].components[0],
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
  public DAGEvaluator()
  {
    functions = new HashMap<>();
    populateFunctionMap();
  }// DAGEvaluator()

  /**
   * Evaluate a DAG.
   * @param root of the DAG
   * @return the RGB value of the DAG
   * @throws Exception 
   *    if something has wrong arguments or
   *    if a function is not in the hash
   */
  public RGBValue evaluate(TreeNode root)
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
        RGBValue val = getContext(root.getNodeVal());
        root.evaluate(val);
        return val; //all leaves have value 1 for now
      }// if leaf

    // Otherwise, recurse on each child
    RGBValue[] args = new RGBValue[root.numChildren()];
    int i = 0;
    for (TreeNode kid : root.getChildren())
      args[i++] = evaluate(kid);

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
   * Get environmental variables like x, y, t.s, etc.
   * @param nodeVal, string representation of the
   *    desired environmental variable
   * @return (1,1,1) <- STUB
   */
  private static RGBValue getContext(String nodeVal)
  {
    //STUB - assume all context values are 1.0
    return new RGBValue(1.0, 1.0, 1.0);
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
                      public RGBValue apply(RGBValue[] args)
                      {
                        RGBValue sum = new RGBValue(0, 0, 0);
                        for (RGBValue arg : args)
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
                      public RGBValue apply(RGBValue[] args)
                      {
                        RGBValue prod = new RGBValue(1, 1, 1);
                        for (RGBValue arg : args)
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
                      public RGBValue apply(RGBValue[] args)
                      {
                        RGBValue sum = new RGBValue(0, 0, 0);
                        for (RGBValue arg : args)
                          {
                            sum.add(arg);
                          }// for each argument
                        sum.range();
                        sum.multiplyBy(new RGBValue(1 / ((double) args.length)));
                        return sum;
                      }
                    });
    // Wrap sum
    functions.put("wsum", new Function()
                    {
                      public RGBValue apply(RGBValue[] args)
                      {
                        RGBValue sum = new RGBValue(0, 0, 0);
                        for (RGBValue arg : args)
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
                      public RGBValue apply(RGBValue[] args)
                        throws Exception
                      {
                        if (args.length != 1)
                          throw new Exception();
                        RGBValue result = new RGBValue();
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
                      public RGBValue apply(RGBValue[] args)
                        throws Exception
                      {
                        if (args.length != 1)
                          throw new Exception();
                        RGBValue result = new RGBValue();
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
                      public RGBValue apply(RGBValue[] args)
                        throws Exception
                      {
                        if (args.length != 1)
                          throw new Exception();
                        RGBValue result = new RGBValue();
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
                      public RGBValue apply(RGBValue[] args)
                        throws Exception
                      {
                        if (args.length != 1)
                          throw new Exception();
                        RGBValue result = new RGBValue();
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
                      public RGBValue apply(RGBValue[] args)
                        throws Exception
                      {
                        if (args.length != 1)
                          throw new Exception();
                        RGBValue result = new RGBValue();
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
                      public RGBValue apply(RGBValue[] args)
                        throws Exception
                      {
                        if (args.length != 1)
                          throw new Exception();

                        if (args[0].components[0] > 0
                            && args[0].components[1] > 0
                            && args[0].components[2] > 0)
                          return new RGBValue(1.0);
                        else
                          return new RGBValue(-1.0);
                      }
                    });
    // Non-component-wise if
    functions.put("if", new Function()
                    {
                      public RGBValue apply(RGBValue[] args)
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
                      public RGBValue apply(RGBValue[] args)
                        throws Exception
                      {
                        if (args.length != 3)
                          throw new Exception();
                        // Take the first component of the first child, 
                        // second of the second child, etc
                        RGBValue result =
                            new RGBValue(args[0].components[0],
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
    public RGBValue apply(RGBValue[] args)
      throws Exception;
  }// interface Function

  public static void main(String[] args)
    throws Exception
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    String code = "wsum(x,x)";
    TreeNode root = Parser.parse(code);
    pen.println("After parsing, the tree is:\n " + root);
    TreeNode dagRoot = DAG.makeDAG(root);
    pen.println("After making a DAG, the tree is:\n " + dagRoot);
    DAGEvaluator e = new DAGEvaluator();
    pen.println("Result is " + e.evaluate(dagRoot));

    // Experimenting with enum valueOf and ordinal
    int arr[] = { 7, 2, 3 };
    String funName = "sum";
    FUN_NAMES name = FUN_NAMES.valueOf("FUN_" + funName.toUpperCase());
    pen.println(arr[name.ordinal()]);
  }// main
}// DAGEvaluator class
