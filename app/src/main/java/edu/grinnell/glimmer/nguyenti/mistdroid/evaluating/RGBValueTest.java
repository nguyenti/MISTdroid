package evaluating;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class RGBValueTest
{
  RGBValue testRGB = null;
  double red, green, blue, greyVal = 0.0;

  @Test
  public void testRGBvalueConstruction()
  {
    red = 255.0;
    green = 0.0;
    blue = 0.0;
    greyVal = 20;
    double expected[] = { red, green, blue };
    // 3 component test
    testRGB = new RGBValue(red, green, blue);

    assertEquals("check 3 parameter construction", Arrays.toString(expected),
                 Arrays.toString(testRGB.components));

    //Grey test
    testRGB = new RGBValue(greyVal);
    expected = new double[] { greyVal, greyVal, greyVal };
    assertEquals("grey test", Arrays.toString(expected),
                 Arrays.toString(testRGB.components));
  }//testRGBvalueConstruction()

  @Test
  public void testBasicOperations()
  {
    //Range()
    red = 255; blue = 255;  green = 255;
    double expected[] = { red, green, blue };
    //Range test
    testRGB = new RGBValue(red, green, blue);
    testRGB.range();
    expected = new double[] { 1.0, 1.0, 1.0 };
    assertEquals("should be upper bounded", Arrays.toString(expected),
                 Arrays.toString(testRGB.components));
    red = -255;blue = -255;green = -255;
    expected = new double[] { -1.0, -1.0, -1.0 };
    testRGB = new RGBValue(red, green, blue);
    testRGB.range();
    assertEquals("should be lower bounded", Arrays.toString(expected),
                 Arrays.toString(testRGB.components));
    //multiply()
    red = 0.2 ;blue = 0.2;green = 0.2;
    testRGB = new RGBValue(red, green, blue);
    RGBValue second = new RGBValue(2, 2, 2);
    testRGB.multiplyBy(second);
    expected = new double[] { 0.4, 0.4, 0.4 };
    assertEquals("multiplies each component together",
                 Arrays.toString(expected), Arrays.toString(testRGB.components));

  //add()
    testRGB.add(second);
    expected = new double[] { 2.4, 2.4, 2.4 };
    assertEquals("add each component together",
                 Arrays.toString(expected), Arrays.toString(testRGB.components));
    //TODO: to String
  }//testBasicOperations()
}
