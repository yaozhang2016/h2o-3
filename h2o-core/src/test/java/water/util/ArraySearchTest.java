package water.util;

import com.google.common.math.IntMath;
import org.junit.AfterClass;
import org.junit.Test;
import water.TestUtil;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static water.util.ArraySearch.*;

/**
 * Test FrameUtils interface.
 */
public class ArraySearchTest {

  @AfterClass public static void dumpStats() {
    for (PerfRep row : reports) {
      System.out.println(row.toHtml());
    }
  }
  
  synchronized int[] checkOne(long[] sut, long v, int[] stats) {
    int found = gradientRangeSearch(sut, v);
    int bsFound = binaryRangeSearch(sut, v);
    assertEquals(bsFound, found);
    int nbcomps = _for_testing_number_of_comparisons_in_binary_search;
    int ngcomps = _for_testing_number_of_comparisons_in_grad_search;
//    if (bsFound[1] > 0) assertTrue("lost at " + v + ": " + ncomps + " vs " + bsFound[1], ncomps <= bsFound[1] + 3);
    stats[0] += ngcomps;
    stats[1] += nbcomps;
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
      return String.format("%s: %d tries, %.2f vs %.2f", what, size, first, second);
    }
    public String toHtml() {
      return String.format("<tr><td>%s</td><td>%d</td><td>%.2f</td><td>%.2f</td>", what, size, first, second);
    }
  }
  
  static List<PerfRep> reports = new LinkedList<>();
  
  private static void report(String what, int[] stats) {
    PerfRep rep = new PerfRep(what, stats[2], stats[0], stats[1]);
    reports.add(rep);
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
    report("Linear", stats);
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
    report("Concave", stats);
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
    report("Concave", stats);
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
    report("Convex", stats);
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
    report("Concave/convex", stats);
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
    report("Convex/concave", stats);
    assertEquals(9, gradientRangeSearch(sut, 0));
    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE));
    assertEquals(18, gradientRangeSearch(sut, Long.MAX_VALUE));
  }

  @Test public void testGradientSearch_sine_plus_x() {
    long[] sut = new long[10000];
    for (int i = 0; i < 10000; i++) {
      double x = Math.sin(i * Math.PI / 100) * 1999 + i * 2000;
      sut[i] = (long)x;
      assertTrue ("At " + i, i == 0 || sut[i] >= sut[i-1]);
    }

    int[] stats = new int[]{0,0,0};
    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE));

    for (long i = 0; i < sut.length; i++) {
      checkOne(sut, i, stats);
    }
    report("x+sin(x)", stats);
    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE));
    assertEquals(9999, gradientRangeSearch(sut, Long.MAX_VALUE));
  }

  @Test public void testGradientSearch_cantor_set_integral() {
    int n = IntMath.pow(3,14);
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
    report("Cantor Set", stats);
  }

  @Test public void testGradientSearch_two_elements() {
    long[] sut = new long[]{1,2};

    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE));
    assertEquals(-1, gradientRangeSearch(sut, 0));
    assertEquals( 0, gradientRangeSearch(sut, 1));
    assertEquals( 1, gradientRangeSearch(sut, 2));
    assertEquals( 1, gradientRangeSearch(sut, 3));
  }

  @Test public void testGradientSearch_one_element() {
    long[] sut = new long[]{1};

    assertEquals(-1, gradientRangeSearch(sut, Long.MIN_VALUE));
    assertEquals(-1, gradientRangeSearch(sut, 0));
    assertEquals( 0, gradientRangeSearch(sut, 1));
    assertEquals( 0, gradientRangeSearch(sut, 2));
  }

  @Test public void testGradientSearch_zero_elements() {
    long[] sut = new long[0];

    assertEquals(0, gradientRangeSearch(sut, Long.MIN_VALUE));
    assertEquals(0, gradientRangeSearch(sut, 0));
    assertEquals(0, gradientRangeSearch(sut, 1));
    assertEquals(0, gradientRangeSearch(sut, 2));
  }
  
}
