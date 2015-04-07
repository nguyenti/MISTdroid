package evaluating;

import java.math.BigInteger;

/**
 * A class that times division by two
 * Credits: http://stackoverflow.com/questions/1770010/how-do-i-measure-time-elapsed-in-java
 * 
 *
 */
public class DivideByTwo
{

  /**
   * @param args
   */
  public static void main(String[] args)
  {

    int runs = 1000;
    double q = 0;
    long divisionStart, divisionEnd, divisionTime = 0;
    long multStart, multEnd, multTime = 0;
    long queues = 0;

    for (int i = 0; i < runs; i++)
      {
        q = 0;
        divisionStart = System.nanoTime();
        for (double d = -0.1231; d < 2; d += 0.000001)
          q += d / 2.0;
        divisionEnd = System.nanoTime();
        divisionTime += (divisionEnd - divisionStart)/(runs * 1.0);

        
        queues+=q;

        q = 0;
        multStart = System.nanoTime();
        for (double d = -0.1231; d < 2; d += 0.000001)
          q += d * 0.5;
        multEnd = System.nanoTime();
        multTime += (multEnd - multStart)/(runs * 1.0);
        queues+=q;
 
      }
    System.out.println("Division is slower by " + (divisionTime - multTime)
                       / (divisionTime * 1.0) * 100 + "%\n q was " + queues/(200.0));
   
  }

}
