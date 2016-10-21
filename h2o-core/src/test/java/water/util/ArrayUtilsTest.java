package water.util;

import com.google.common.math.IntMath;
import org.junit.BeforeClass;
import org.junit.Test;
import water.TestUtil;

import java.util.LinkedList;
import java.util.List;

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

  public int[] binarySearch(long[] ys, long y) {
    int numComps = 1;
    if (y < ys[0]) return new int[] {-1, 1};
    numComps++;
    if (y >= ys[ys.length - 1]) return new int[] {ys.length - 1, 2};

    int lo=0, hi = ys.length - 1;
    while( lo < hi-1 ) {
      int mid = (hi+lo)>>>1;
      numComps++;
      if( y < ys[mid] ) hi = mid;
      else              lo = mid;
    }
    
    while( ys[lo+1] == y) lo++;
    return new int[]{lo, numComps};
  }
  
  synchronized int[] checkOne(long[] sut, long v, int[] stats) {
    int found = gradientRangeSearch(sut, v);
    int[] bsFound = binarySearch(sut, v);
    assertEquals(bsFound[0], found);
    int ncomps = ArrayUtils._for_testing_number_of_comparisons;
//    if (bsFound[1] > 0) assertTrue("lost at " + v + ": " + ncomps + " vs " + bsFound[1], ncomps <= bsFound[1] + 3);
    stats[0] += ncomps;
    stats[1] += bsFound[1];
    stats[2] ++;
    return stats;
  }
  
  private static class PerfRep {
    String what; int size; double first; double second;
    PerfRep(String what, int size, double first, double second) {
      this.what = what; this.size = size; this.first = first; this.second = second;
    }
    PerfRep(String what, int size, int first, int second) {
      this(what, size, first*1.0/size, second*1.0/size);
    }
    public String toString() {
      return String.format("%s: %d elements, %.2f vs %.2f", what, size, first, second);
    }
    public String toHtml() {
      return String.format("<tr><td>%s</td><td>%d</td><td>%.2f</td><td>%.2f</td>", what, size, first, second);
    }
  }
  
  //static List<PerfRep> reports = new LinkedList<>();
  
  private static void report(String what, int[] stats, int size) {
    PerfRep rep = new PerfRep(what, size, stats[0], stats[1]);
//    reports.add(rep);
    System.out.println(rep);
  }

  @Test public void testGradientSearch_almost_linear() {
    long[] sut = new long[10000];
    for (int i = 0; i < sut.length; i++) {
      sut[i] = i * 1000 + (i%2*2-1);
    }
    int[] stats = new int[]{0,0,0};
    for (long i = 0; i <sut.length * 5; i++) {
      checkOne(sut, i*200+(i%5-2), stats);
    }
    report("Linear", stats, sut.length);
    assertEquals(0, gradientRangeSearch(sut, 0));
    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE));
    assertEquals(9999, gradientRangeSearch(sut, Long.MAX_VALUE));
  }

  @Test public void testGradientSearch_with_repetitions() {
    long[] sut = new long[]{1, 10, 100, 1000, 10000, 10000, 10000};
    long delta = 1;

    int[] stats = new int[]{0,0,0};
    for (long i = 1; i <= 10002L; i+=delta) {
      checkOne(sut, i, stats);
    }
    report("Concave", stats, sut.length);
    assertEquals(-1, gradientRangeSearch(sut, 0));
    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE));
    assertEquals(6, gradientRangeSearch(sut, Long.MAX_VALUE));
    assertEquals(6, gradientRangeSearch(sut, 100000000L));
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
    assertEquals(-1, gradientRangeSearch(sut, 0));
    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE));
    assertEquals(8, gradientRangeSearch(sut, Long.MAX_VALUE));
    assertEquals(8, gradientRangeSearch(sut, 100000000L));
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
    assertEquals(8, gradientRangeSearch(sut, 0));
    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE));
    assertEquals(8, gradientRangeSearch(sut, Long.MAX_VALUE));
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
    assertEquals(9, gradientRangeSearch(sut, 0));
    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE));
    assertEquals(18, gradientRangeSearch(sut, Long.MAX_VALUE));
  }

  @Test public void testGradientSearch_sine_plus_x() {
    long[] sut = new long[10000];
    for (int i = 0; i < 10000; i++) {
      double x = Math.sin(i * Math.PI / 10000) * 3000 + i;
      sut[i] = (long)x;
      assertTrue (i == 0 || sut[i] >= sut[i-1]);
    }

    int[] stats = new int[]{0,0,0};

    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE));

    for (long i = 0; i < sut.length; i++) {
      checkOne(sut, i, stats);
    }
    report("Sine", stats, sut.length);
    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE));
    assertEquals(9999, gradientRangeSearch(sut, Long.MAX_VALUE));
  }

  @Test public void testGradientSearch_cantor_set_integral() {
    int n = IntMath.pow(3,10);
    long[] sut = new long[n];
    long sum = 0;
    for (int i = 0; i < n; i++) {
      String ternary = Integer.toString(i, 3);
      boolean include = ternary.indexOf("1") < 0;
      if (include) sum += 1;
      sut[i] = sum;
    }

    int[] stats = new int[]{0,0,0};

    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE));

    for (long i = 0; i < sum+1; i++) {
      checkOne(sut, i, stats);
    }
    report("Cantor Set", stats, sut.length);
    fail();
  }
}
