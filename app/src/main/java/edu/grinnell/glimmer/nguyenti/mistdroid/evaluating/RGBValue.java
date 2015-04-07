package evaluating;

/**
 * A class for RGB triplets
 * 
 * @author Mist Parser team
 */
public class RGBValue
{

  double[] components;
  /**
   * Default empty constructor
   */
  public RGBValue()
  {
    components = new double[3];
  }
  /**
   * Full constructor
   * @param red
   * @param green
   * @param blue
   */
  public RGBValue(double red, double green, double blue)
  {
    components = new double[3];
    components[0] = red;
    components[1] = green;
    components[2] = blue;
  }
  /**
   * Interpolating single-component constructor
   * @param gray
   */
  public RGBValue(double gray)
  {
    components = new double[3];
    components[0] = components[1] = components[2] = gray;
  }
  
    public boolean equals(Object obj){
  
    if(obj == this)
    return true;
    if(!(obj instanceof RGBValue))
    return false;
    RGBValue rv = (RGBValue) obj;
    double threshold = 0.00000000001;
    
    if ((Math.abs(rv.components[0]-this.components[0])<threshold) &&
        (Math.abs(rv.components[1]-this.components[1])<threshold) &&
        (Math.abs(rv.components[2]-this.components[2])<threshold))
      return true;
    else
      return false;
  
  }
  
  /**
   * Bound each component value to the closed interval [-1,1]
   * by clipping it at the endpoints of the interval
   */
  public void range()
  {
    for (int i = 0; i < 3; i++)
      {
        double c = components[i];
        if (c > 1.0)
          components[i] = 1.0;
        else if (c < -1.0)
          components[i] = -1.0;
      }// for each component
  }// range
  
  /**
   * An equivalent to range used in "wsum" function:
   * Bound each component value to the closed interval [-1,1] 
   * by wrapping around the interval
   */
  public void wrap() 
  {
    for (int i = 0; i < 3; i++)
      {
        double c = components[i];
        if (c > 1.0 || c < -1.0)
          components[i] = c - 2.0*(Math.round(c/2.0));
      }
  }// wrap
  
  /**
   * Add two RGBValues
   * @param toAdd
   */
  public void add(RGBValue toAdd)
  {
    for (int i = 0; i < 3; i++)
      {
        components[i] += toAdd.components[i];
      }// for each component
  }// add
  
  /**
   * Multiply two RGBValues
   * @param toAdd
   */
  public void multiplyBy(RGBValue toMultiply)
  {
    for (int i = 0; i < 3; i++)
      {
        components[i] *= toMultiply.components[i];
      }// for each component
  }// multiplyBy
  
  /**
   * Convert to string
   */
  public String toString()
  {
    return "(" + components[0] + ", " + components[1] + ", " + components[2] + ")";
  } //toString

  /**
   * (Insufficient) experiments with wrapping 
   * 
   */
  public static void main(String[] args)
  {
    RGBValue seventeen = new RGBValue(17.2, -17.2, 1);
    System.out.println("original" + seventeen);
    seventeen.wrap();
    System.out.println(seventeen);
  }// main
  
  
}// class RGBValue
