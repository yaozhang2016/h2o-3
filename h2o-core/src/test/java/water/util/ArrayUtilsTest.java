package water.util;

import org.junit.BeforeClass;
import org.junit.Test;
import water.TestUtil;

import static org.junit.Assert.*;
import static water.util.ArrayUtils.*;

/**
 * Test FrameUtils interface.
 */
public class ArrayUtilsTest extends TestUtil {
  @BeforeClass
  static public void setup() {  stall_till_cloudsize(1); }

  @Test
  public void testAppendBytes() {
    byte[] sut = {1, 2, 3};
    byte[] sut2 = {3, 4};
    byte[] expected = {1, 2, 3, 3, 4};
    byte[] empty = {};
    assertArrayEquals(null, append((byte[]) null, null));
    assertArrayEquals(sut, append(null, sut));
    assertArrayEquals(sut, append(sut, null));
    assertArrayEquals(empty, append(null, empty));
    assertArrayEquals(empty, append(empty, null));
    assertArrayEquals(sut, append(empty, sut));
    assertArrayEquals(sut, append(sut, empty));
    assertArrayEquals(expected, append(sut, sut2));
  }

  @Test
  public void testAppendInts() {
    int[] sut = {1, 2, 3};
    int[] sut2 = {3, 4};
    int[] expected = {1, 2, 3, 3, 4};
    int[] empty = {};
    assertArrayEquals(null, append((int[]) null, null));
    assertArrayEquals(sut, append(null, sut));
    assertArrayEquals(sut, append(sut, null));
    assertArrayEquals(empty, append(null, empty));
    assertArrayEquals(empty, append(empty, null));
    assertArrayEquals(sut, append(empty, sut));
    assertArrayEquals(sut, append(sut, empty));
    assertArrayEquals(expected, append(sut, sut2));
  }

  @Test
  public void testAppendLongs() {
    long[] sut = {1, 2, 3};
    long[] sut2 = {3, 4};
    long[] expected = {1, 2, 3, 3, 4};
    long[] empty = {};
    assertArrayEquals(null, append((int[]) null, null));
    assertArrayEquals(sut, append(null, sut));
    assertArrayEquals(sut, append(sut, null));
    assertArrayEquals(empty, append(null, empty));
    assertArrayEquals(empty, append(empty, null));
    assertArrayEquals(sut, append(empty, sut));
    assertArrayEquals(sut, append(sut, empty));
    assertArrayEquals(expected, append(sut, sut2));
  }

  @Test
  public void testRemoveOneObject() {
    Integer[] sut = {1, 2, 3};
    Integer[] sutWithout1 = {2, 3};
    Integer[] sutWithout2 = {1, 3};
    Integer[] sutWithout3 = {1, 2};
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, Integer.MIN_VALUE));
    assertArrayEquals("Should not have deleted ",   sut, remove(sut, -1));
    assertArrayEquals("Should have deleted first",  sutWithout1, remove(sut, 0));
    assertArrayEquals("Should have deleted second", sutWithout2, remove(sut, 1));
    assertArrayEquals("Should have deleted third",  sutWithout3, remove(sut, 2));
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, 3));
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, Integer.MAX_VALUE));
  }

  @Test
  public void testRemoveOneObjectFromSingleton() {
    Integer[] sut = {1};
    Integer[] sutWithout1 = {};
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, Integer.MIN_VALUE));
    assertArrayEquals("Should not have deleted ",   sut, remove(sut, -1));
    assertArrayEquals("Should have deleted first",  sutWithout1, remove(sut, 0));
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, 1));
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, Integer.MAX_VALUE));
  }

  @Test
  public void testRemoveOneObjectFromEmpty() {
    Integer[] sut = {};
    assertArrayEquals("Nothing to remove",    sut, remove(sut, -1));
    assertArrayEquals("Nothing to remove",    sut, remove(sut, 0));
    assertArrayEquals("Nothing to remove",    sut, remove(sut, 1));
  }

  @Test
  public void testRemoveOneByte() {
    byte[] sut = {1, 2, 3};
    byte[] sutWithout1 = {2, 3};
    byte[] sutWithout2 = {1, 3};
    byte[] sutWithout3 = {1, 2};
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, Integer.MIN_VALUE));
    assertArrayEquals("Should not have deleted ",   sut, remove(sut, -1));
    assertArrayEquals("Should have deleted first",  sutWithout1, remove(sut, 0));
    assertArrayEquals("Should have deleted second", sutWithout2, remove(sut, 1));
    assertArrayEquals("Should have deleted third",  sutWithout3, remove(sut, 2));
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, 3));
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, Integer.MAX_VALUE));
  }

  @Test
  public void testRemoveOneByteFromSingleton() {
    byte[] sut = {1};
    byte[] sutWithout1 = {};
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, Integer.MIN_VALUE));
    assertArrayEquals("Should not have deleted ",   sut, remove(sut, -1));
    assertArrayEquals("Should have deleted first",  sutWithout1, remove(sut, 0));
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, 1));
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, Integer.MAX_VALUE));
  }

  @Test
  public void testRemoveOneByteFromEmpty() {
    byte[] sut = {};
    assertArrayEquals("Nothing to remove",    sut, remove(sut, -1));
    assertArrayEquals("Nothing to remove",    sut, remove(sut, 0));
    assertArrayEquals("Nothing to remove",    sut, remove(sut, 1));
  }

  @Test
  public void testRemoveOneInt() {
    int[] sut = {1, 2, 3};
    int[] sutWithout1 = {2, 3};
    int[] sutWithout2 = {1, 3};
    int[] sutWithout3 = {1, 2};
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, Integer.MIN_VALUE));
    assertArrayEquals("Should not have deleted ",   sut, remove(sut, -1));
    assertArrayEquals("Should have deleted first",  sutWithout1, remove(sut, 0));
    assertArrayEquals("Should have deleted second", sutWithout2, remove(sut, 1));
    assertArrayEquals("Should have deleted third",  sutWithout3, remove(sut, 2));
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, 3));
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, Integer.MAX_VALUE));
  }

  @Test
  public void testRemoveOneIntFromSingleton() {
    int[] sut = {1};
    int[] sutWithout1 = {};
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, Integer.MIN_VALUE));
    assertArrayEquals("Should not have deleted ",   sut, remove(sut, -1));
    assertArrayEquals("Should have deleted first",  sutWithout1, remove(sut, 0));
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, 1));
    assertArrayEquals("Should have not deleted",    sut,         remove(sut, Integer.MAX_VALUE));
  }

  @Test
  public void testRemoveOneIntFromEmpty() {
    int[] sut = {};
    assertArrayEquals("Nothing to remove",    sut, remove(sut, -1));
    assertArrayEquals("Nothing to remove",    sut, remove(sut, 0));
    assertArrayEquals("Nothing to remove",    sut, remove(sut, 1));
  }

  @Test
  public void testCountNonZeroes() {
    double[] empty = {};
    assertEquals(0, countNonzeros(empty));
    double[] singlenz = {1.0};
    assertEquals(1, countNonzeros(singlenz));
    double[] threeZeroes = {0.0, 0.0, 0.0};
    assertEquals(0, countNonzeros(threeZeroes));
    double[] somenz = {-1.0, Double.MIN_VALUE, 0.0, Double.MAX_VALUE, 0.001, 0.0, 42.0};
    assertEquals(5, countNonzeros(somenz));
  }
  
  private static int findEasy(long[] ys, long y) {
    for (int i = 0; i < ys.length - 1 && y >= ys[i]; i++) {
      if (y < ys[i+1]) return i;
    }
    return y < ys[0] ? -1 : ys.length - 1;
  }

  public int[] binarySearch(long[] ys, long y) {
    if (y < ys[0]) return new int[] {-1, 0};
    if (y >= ys[ys.length - 1]) return new int[] {ys.length - 1, 0};

    int numhops = 0;
    int lo=0, hi = ys.length - 1;
    while( lo < hi-1 ) {
      int mid = (hi+lo)>>>1;
      if( y < ys[mid] ) hi = mid;
      else                lo = mid;
      numhops++;
    }
    
    while( ys[lo+1] == y) lo++;
    return new int[]{lo, numhops};
  }
  
  int[] checkOne(long[] sut, long v, int[] stats) {
    int[] found = gradientRangeSearch(sut, v);
    assertEquals(findEasy(sut, v), found[0]);
    int[] bsFound = binarySearch(sut, v);
    assertEquals(bsFound[0], found[0]);
//    if (bsFound[1] > 0 && bsFound[1]+1 < found[1]) System.out.println("lost at " + v + ": " + found[1] + " vs " + bsFound[1]);
//    if (bsFound[1] > 0) assertTrue("lost at " + v + ": " + found[1] + " vs " + bsFound[1], bsFound[1] >= found[1]);
    stats[0] += found[1];
    stats[1] += bsFound[1];
    stats[2] ++;
    return stats;
  }
  
  void report(String what, int[] stats, int size) {
    double s0 = stats[0]*1.0/stats[2];
    double s1 = stats[1]*1.0/stats[2];
    System.out.println(String.format("%s: %d elements, %.2f vs %.2f", what, size, s0, s1));
  }
  
  @Test public void testGradientSearch_almost_linear() {
    long[] sut = new long[]{1, 999, 2002, 3001, 3999, 4100};
    int[] stats = new int[]{0,0,0};
    for (long i = 1; i <= 4100; i++) {
      int[] res = checkOne(sut, i, stats);
    }
    report("Linear", stats, sut.length);
    assertEquals(-1, gradientRangeSearch(sut, 0)[0]);
    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE)[0]);
    assertEquals(5, gradientRangeSearch(sut, Long.MAX_VALUE)[0]);
    assertEquals(5, gradientRangeSearch(sut, 4101)[0]);
  }

  @Test public void testGradientSearch_concave() {
    long[] sut = new long[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000L, 100000000L};
    long delta = 1;

    int[] stats = new int[]{0,0,0};
    for (long i = 1; i <= 100000000L; i+=delta) {
      checkOne(sut, i, stats);
      if (i > 10000) delta = 1000;
    }
    report("Concave", stats, sut.length);
    assertEquals(-1, gradientRangeSearch(sut, 0)[0]);
    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE)[0]);
    assertEquals(8, gradientRangeSearch(sut, Long.MAX_VALUE)[0]);
    assertEquals(8, gradientRangeSearch(sut, 100000000L)[0]);
  }

  @Test public void testGradientSearch_convex() {
    long[] sut = new long[]{-100000000L, -10000000L, -1000000L, -100000L, -10000L, -1000L, -100L, -10L, -1L};
    long delta = 1000;
    int[] stats = new int[]{0,0,0};

    for (long i = -100000001L; i < 1; i+=delta) {
      checkOne(sut, i, stats);
      if (i > -10000) delta = 1;
    }
    report("Convex", stats, sut.length);
    assertEquals(8, gradientRangeSearch(sut, 0)[0]);
    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE)[0]);
    assertEquals(8, gradientRangeSearch(sut, Long.MAX_VALUE)[0]);
  }

  @Test public void testGradientSearch_concave_then_convex() {
    long[] sut = new long[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000L, 100000000L, 110000000L, 111000000L, 111100000L, 111110000L, 111111000L, 111111100L, 111111110L, 111111111L};
    long delta = 1;
    int[] stats = new int[]{0,0,0};

    for (long i = 1; i <= 111111111L; i+=delta) {
      checkOne(sut, i, stats);
      if (i > 10000) delta = 1000;
    }
    report("Concave/convex", stats, sut.length);
  }

  @Test public void testGradientSearch_convex_then_concave() {
    long[] sut = new long[]{-100000000L, -10000000L, -1000000L, -100000L, -10000L, -1000L, -100L, -10L, -1L, 0, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000L, 100000000L};
    long delta = 1000;
    int[] stats = new int[]{0,0,0};

    for (long i = -100000001L; i < 100000000L; i+=delta) {
      checkOne(sut, i, stats);
      if (i > -10000) delta = 1;
      if (i > 10000) delta = 1000;
    }
    report("Convex/concave", stats, sut.length);
    assertEquals(9, gradientRangeSearch(sut, 0)[0]);
    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE)[0]);
    assertEquals(18, gradientRangeSearch(sut, Long.MAX_VALUE)[0]);
  }

  @Test public void testGradientSearch_sine_plus_x() {
    long[] sut = new long[10000];
    for (int i = 0; i < 10000; i++) {
      double x = Math.sin(i * Math.PI / 10000) * 5000 + i;
      sut[i] = (long)x;
    }

    int[] stats = new int[]{0,0,0};
    for (long i = 0; i < sut.length; i++) {
      checkOne(sut, i, stats);
    }
    report("Sine", stats, sut.length);
    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE)[0]);
    assertEquals(9999, gradientRangeSearch(sut, Long.MAX_VALUE)[0]);
  }
}
