package water.util;

import com.google.common.io.Files;
import com.google.common.math.IntMath;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static water.util.ArraySearch.*;

/**
 * Test ArraySearch functions
 */
public class ArraySearchTest {

  @AfterClass public static void dumpStats() {
    for (PerfRep row : reports) {
      System.out.println(row.toHtml());
    }
  }
  
  synchronized Stats[] checkOne(ArraySearch sut, long[] data, long v) {
    int found = sut.gradientRangeSearchWithStats(data, v);
    int bsFound = sut.binaryRangeSearchWithStats(data, v);
    assertEquals(bsFound, found);
    return new ArraySearch.Stats[]{sut.stats_long_gradient, sut.stats_long_binary};
  }
  
  private static class PerfRep {
    String what; 
    long size;
    long t1;
    long t2;
    
    PerfRep(String what, long size, long t1, long t2) {
      this.what = what;
      this.size = size;
      this.t1 = t1;
      this.t2 = t2;
    }

    private String format(String fmt) {
      return String.format(fmt, what, size, t1*1.0/size, t2*1.0/size);
    }
    
    public String toString() {
      return format("%s: %d, %.2f vs %.2f");
    }
    
    public String toHtml() {
      return format("<tr><td>%s</td><td>%d</td><td>%.2f</td><td>%.2f</td>");
    }
  }
  
  static List<PerfRep> reports = new LinkedList<>();
  
  private static void report(String what, long size, long t1, long t2) {
    PerfRep rep = new PerfRep(what, size, t1, t2);
    reports.add(rep);
    System.out.println(rep);
  }

  @Test public void testGradientSearch_almost_linear() {
    ArraySearch sut = new ArraySearch();
    long[] data = new long[10000];
    for (int i = 0; i < data.length; i++) {
      data[i] = i * 1000 + (i%2*2-1);
    }

    for (long i = 0; i <data.length * 5; i++) {
      checkOne(sut, data, i*200+(i%5-2));
    }

    long t0 = System.nanoTime();
    for (long i = 0; i <data.length * 5; i++) {
      sut.gradientRangeSearchWithStats(data, i*200+(i%5-2));
    }
    long t1 = System.nanoTime();
    for (long i = 0; i <data.length * 5; i++) {
      sut.binaryRangeSearchWithStats(data, i*200+(i%5-2));
    }
    long t2 = System.nanoTime();
    report("Long Linear", sut.stats_long_binary.size, t1-t0, t2-t1);
    assertEquals(0, sut.gradientRangeSearchWithStats(data, 0));
    assertEquals(-1, sut.gradientRangeSearchWithStats(data, Long.MIN_VALUE));
    assertEquals(9999, sut.gradientRangeSearchWithStats(data, Long.MAX_VALUE));
  }

  @Test public void testGradientSearch_with_repetitions() {
    ArraySearch sut = new ArraySearch();
    long[] data = new long[]{1, 10, 100, 1000, 10000, 10000, 10000};
    long delta = 1;

    for (long i = 1; i <= 10002L; i+=delta) {
      checkOne(sut, data, i);
    }
    long t0 = System.nanoTime();
    for (long i = 1; i <= 10002L; i+=delta) {
      sut.gradientRangeSearchWithStats(data, i);
    }
    long t1 = System.nanoTime();
    for (long i = 1; i <= 10002L; i+=delta) {
      sut.binaryRangeSearchWithStats(data, i);
    }
    long t2 = System.nanoTime();
    report("Long Concave", sut.stats_long_binary.size, t1-t0, t2-t1);
    assertEquals(-1, sut.gradientRangeSearchWithStats(data, 0));
    assertEquals(-1, sut.gradientRangeSearchWithStats(data, Long.MIN_VALUE));
    assertEquals(6, sut.gradientRangeSearchWithStats(data, Long.MAX_VALUE));
    assertEquals(6, sut.gradientRangeSearchWithStats(data, 100000000L));
  }

  @Test public void testGradientSearch_concave() {
    ArraySearch sut = new ArraySearch();
    long[] data = new long[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000L, 100000000L};
    long delta = 1;

    for (long i = 1; i <= 100000000L; i+=delta) {
      checkOne(sut, data, i);
      if (i > 10000) delta = 1000;
    }
    delta=1;
    long t0 = System.nanoTime();
    for (long i = 1; i <= 10002L; i+=delta) {
      sut.gradientRangeSearchWithStats(data, i);
      if (i > 10000) delta = 1000;
    }
    long t1 = System.nanoTime();
    delta=1;
    for (long i = 1; i <= 10002L; i+=delta) {
      sut.binaryRangeSearchWithStats(data, i);
      if (i > 10000) delta = 1000;
    }
    long t2 = System.nanoTime();    
    report("Long Concave", sut.stats_long_gradient.size, t1-t0, t2-t1);
    assertEquals(-1, sut.gradientRangeSearchWithStats(data, 0));
    assertEquals(-1, sut.gradientRangeSearchWithStats(data, Long.MIN_VALUE));
    assertEquals(8, sut.gradientRangeSearchWithStats(data, Long.MAX_VALUE));
    assertEquals(8, sut.gradientRangeSearchWithStats(data, 100000000L));
  }

  @Test public void testGradientSearch_convex() {
    ArraySearch sut = new ArraySearch();
    long[] data = new long[]{-100000000L, -10000000L, -1000000L, -100000L, -10000L, -1000L, -100L, -10L, -1L};
    long delta = 1000;

    for (long i = -100000001L; i < 1; i+=delta) {
      checkOne(sut, data, i);
      if (i > -10000) delta = 1;
    }
    delta = 1000;
    long t0 = System.nanoTime();

    for (long i = -100000001L; i < 1; i+=delta) {
      sut.gradientRangeSearchWithStats(data, i);
      if (i > -10000) delta = 1;
    }
    delta = 1000;
    long t1 = System.nanoTime();

    for (long i = -100000001L; i < 1; i+=delta) {
      sut.binaryRangeSearchWithStats(data, i);
      if (i > -10000) delta = 1;
    }
    long t2 = System.nanoTime();
    report("Long Convex", sut.stats_long_gradient.size, t1-t0, t2-t1);
    assertEquals(8, sut.gradientRangeSearchWithStats(data, 0));
    assertEquals(-1, sut.gradientRangeSearchWithStats(data, Long.MIN_VALUE));
    assertEquals(8, sut.gradientRangeSearchWithStats(data, Long.MAX_VALUE));
  }

  @Test public void testGradientSearch_concave_then_convex() {
    ArraySearch sut = new ArraySearch();
    long[] data = new long[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000L, 100000000L, 110000000L, 111000000L, 111100000L, 111110000L, 111111000L, 111111100L, 111111110L, 111111111L};
    long delta = 1;

    for (long i = 1; i <= 111111111L; i+=delta) {
      checkOne(sut, data, i);
      if (i > 10000) delta = 1000;
    }
//    report("Long Concave/convex", sut.stats_long_gradient, sut.stats_long_binary);
  }

  @Test public void testGradientSearch_convex_then_concave() {
    ArraySearch sut = new ArraySearch();
    long[] data = new long[]{-100000000L, -10000000L, -1000000L, -100000L, -10000L, -1000L, -100L, -10L, -1L, 0, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000L, 100000000L};
    long delta = 1000;

    for (long i = -100000001L; i < 100000000L; i+=delta) {
      checkOne(sut, data, i);
      if (i > -10000) delta = 1;
      if (i > 10000) delta = 1000;
    }
//    report("Long Convex/concave", sut.stats_long_gradient, sut.stats_long_binary);
    assertEquals(9, sut.gradientRangeSearchWithStats(data, 0));
    assertEquals(-1, sut.gradientRangeSearchWithStats(data, Long.MIN_VALUE));
    assertEquals(18, sut.gradientRangeSearchWithStats(data, Long.MAX_VALUE));
  }

  @Test public void testGradientSearch_sine_plus_x() {
    ArraySearch sut = new ArraySearch();
    long[] data = new long[10000];
    for (int i = 0; i < 10000; i++) {
      double x = Math.sin(i * Math.PI / 100) * 1999 + i * 2000;
      data[i] = (long)x;
      assertTrue ("At " + i, i == 0 || data[i] >= data[i-1]);
    }

    assertEquals(-1, sut.gradientRangeSearchWithStats(data, Long.MIN_VALUE));

    for (long i = 0; i < data.length; i++) {
      checkOne(sut, data, i);
    }
//    report("Long x+sin(x)", sut.stats_long_gradient, sut.stats_long_binary);
    assertEquals(-1, sut.gradientRangeSearchWithStats(data, Long.MIN_VALUE));
    assertEquals(9999, sut.gradientRangeSearchWithStats(data, Long.MAX_VALUE));
  }

  @Test public void testGradientSearch_cantor_set_integral() {
    int n = IntMath.pow(3,14);
    long[] data = new long[n];
    long sum = 0;
    for (int i = 0; i < n; i++) {
      String ternary = Integer.toString(i, 3);
      if (!ternary.contains("1")) sum += 1;
      data[i] = sum;
    }

    ArraySearch sut = new ArraySearch();

    assertEquals(-1, sut.gradientRangeSearchWithStats(data, Long.MIN_VALUE));

    for (long i = 0; i < sum+1; i++) {
      checkOne(sut, data, i);
    }
//    report("Long Cantor Set", sut.stats_long_gradient, sut.stats_long_binary);
  }

  @Test public void testGradientSearch_two_elements() {
    long[] data = new long[]{1,2};
    ArraySearch sut = new ArraySearch();

    assertEquals(-1, sut.gradientRangeSearchWithStats(data, Long.MIN_VALUE));
    assertEquals(-1, sut.gradientRangeSearchWithStats(data, 0));
    assertEquals( 0, sut.gradientRangeSearchWithStats(data, 1));
    assertEquals( 1, sut.gradientRangeSearchWithStats(data, 2));
    assertEquals( 1, sut.gradientRangeSearchWithStats(data, 3));
  }

  @Test public void testGradientSearch_one_element() {
    long[] data = new long[]{1};
    ArraySearch sut = new ArraySearch();

    assertEquals(-1, sut.gradientRangeSearchWithStats(data, Long.MIN_VALUE));
    assertEquals(-1, sut.gradientRangeSearchWithStats(data, 0));
    assertEquals( 0, sut.gradientRangeSearchWithStats(data, 1));
    assertEquals( 0, sut.gradientRangeSearchWithStats(data, 2));
  }

  @Test public void testGradientSearch_zero_elements() {
    long[] data = new long[0];
    ArraySearch sut = new ArraySearch();

    assertEquals(0, sut.gradientRangeSearchWithStats(data, Long.MIN_VALUE));
    assertEquals(0, sut.gradientRangeSearchWithStats(data, 0));
    assertEquals(0, sut.gradientRangeSearchWithStats(data, 1));
    assertEquals(0, sut.gradientRangeSearchWithStats(data, 2));
  }
  
  @Test public void testBinarySearch_longs() {
    assertEquals(-1, ArraySearch.binarySearch(new long[0], 7));

    assertEquals(-1, ArraySearch.binarySearch(new long[]{10}, 7));
    assertEquals( 0, ArraySearch.binarySearch(new long[]{10}, 10));
    assertEquals(-1, ArraySearch.binarySearch(new long[]{10}, 11));

    assertEquals(-1, ArraySearch.binarySearch(new long[]{10, 12}, 7));
    assertEquals( 0, ArraySearch.binarySearch(new long[]{10, 12}, 10));
    assertEquals( 1, ArraySearch.binarySearch(new long[]{10, 12}, 12));
    assertEquals(-1, ArraySearch.binarySearch(new long[]{10, 12}, 13));
  }

  @Test public void testGradientSearch_longs() {
    assertEquals(-1, ArraySearch.gradientSearch(new long[0], 7));

    assertEquals(-1, ArraySearch.gradientSearch(new long[]{10}, 7));
    assertEquals( 0, ArraySearch.gradientSearch(new long[]{10}, 10));
    assertEquals(-1, ArraySearch.gradientSearch(new long[]{10}, 11));

    assertEquals(-1, ArraySearch.gradientSearch(new long[]{10, 12}, 7));
    assertEquals( 0, ArraySearch.gradientSearch(new long[]{10, 12}, 10));
    assertEquals( 1, ArraySearch.gradientSearch(new long[]{10, 12}, 12));
    assertEquals(-1, ArraySearch.gradientSearch(new long[]{10, 12}, 13));
  }

  @Test public void testBinaryRangeSearch_ints() {
    assertEquals(-1, ArraySearch.binaryRangeSearch(new int[0], 7));

    assertEquals(-1, ArraySearch.binaryRangeSearch(new int[]{10}, Integer.MIN_VALUE));
    assertEquals(-1, ArraySearch.binaryRangeSearch(new int[]{10}, 7));
    assertEquals( 0, ArraySearch.binaryRangeSearch(new int[]{10}, 10));
    assertEquals( 0, ArraySearch.binaryRangeSearch(new int[]{10}, 11));
    assertEquals( 0, ArraySearch.binaryRangeSearch(new int[]{10}, Integer.MAX_VALUE));

    assertEquals(-1, ArraySearch.binaryRangeSearch(new int[]{10, 12}, Integer.MIN_VALUE));
    assertEquals(-1, ArraySearch.binaryRangeSearch(new int[]{10, 12}, 7));
    assertEquals( 0, ArraySearch.binaryRangeSearch(new int[]{10, 12}, 10));
    assertEquals( 1, ArraySearch.binaryRangeSearch(new int[]{10, 12}, 12));
    assertEquals( 1, ArraySearch.binaryRangeSearch(new int[]{10, 12}, 13));
    assertEquals( 1, ArraySearch.binaryRangeSearch(new int[]{10, 12}, Integer.MAX_VALUE));
  }

  @Test public void testBinarySearch_ints() {
    assertEquals(-1, ArraySearch.binarySearch(new int[0], 7));

    assertEquals(-1, ArraySearch.binarySearch(new int[]{10}, 7));
    assertEquals( 0, ArraySearch.binarySearch(new int[]{10}, 10));
    assertEquals(-1, ArraySearch.binarySearch(new int[]{10}, 11));

    assertEquals(-1, ArraySearch.binarySearch(new int[]{10, 12}, 7));
    assertEquals( 0, ArraySearch.binarySearch(new int[]{10, 12}, 10));
    assertEquals( 1, ArraySearch.binarySearch(new int[]{10, 12}, 12));
    assertEquals(-1, ArraySearch.binarySearch(new int[]{10, 12}, 13));
  }

  @Test public void testGradientRangeSearch_ints() {
    assertEquals( 0, ArraySearch.gradientRangeSearch(new int[0], 7));

    assertEquals(-1, ArraySearch.gradientRangeSearch(new int[]{10}, Integer.MIN_VALUE));
    assertEquals(-1, ArraySearch.gradientRangeSearch(new int[]{10}, 7));
    assertEquals( 0, ArraySearch.gradientRangeSearch(new int[]{10}, 10));
    assertEquals( 0, ArraySearch.gradientRangeSearch(new int[]{10}, 11));
    assertEquals( 0, ArraySearch.gradientRangeSearch(new int[]{10}, Integer.MAX_VALUE));

    assertEquals(-1, ArraySearch.gradientRangeSearch(new int[]{10, 12}, Integer.MIN_VALUE));
    assertEquals(-1, ArraySearch.gradientRangeSearch(new int[]{10, 12}, 7));
    assertEquals( 0, ArraySearch.gradientRangeSearch(new int[]{10, 12}, 10));
    assertEquals( 1, ArraySearch.gradientRangeSearch(new int[]{10, 12}, 12));
    assertEquals( 1, ArraySearch.gradientRangeSearch(new int[]{10, 12}, 13));
    assertEquals( 1, ArraySearch.gradientRangeSearch(new int[]{10, 12}, Integer.MAX_VALUE));
  }

  @Test public void testGradientSearch_ints() {
    assertEquals(-1, ArraySearch.gradientSearch(new int[0], 7));

    assertEquals(-1, ArraySearch.gradientSearch(new int[]{10}, 7));
    assertEquals( 0, ArraySearch.gradientSearch(new int[]{10}, 10));
    assertEquals(-1, ArraySearch.gradientSearch(new int[]{10}, 11));

    assertEquals(-1, ArraySearch.gradientSearch(new int[]{10, 12}, 7));
    assertEquals( 0, ArraySearch.gradientSearch(new int[]{10, 12}, 10));
    assertEquals( 1, ArraySearch.gradientSearch(new int[]{10, 12}, 12));
    assertEquals(-1, ArraySearch.gradientSearch(new int[]{10, 12}, 13));
  }
////

  @Test public void testBinaryRangeSearch_doubles() {
    assertEquals( 0, ArraySearch.binaryRangeSearch(new double[0], 7));

    assertEquals(-1, ArraySearch.binaryRangeSearch(new double[]{10}, Double.MIN_VALUE));
    assertEquals(-1, ArraySearch.binaryRangeSearch(new double[]{10}, 7));
    assertEquals( 0, ArraySearch.binaryRangeSearch(new double[]{10}, 10));
    assertEquals( 0, ArraySearch.binaryRangeSearch(new double[]{10}, 11));
    assertEquals( 0, ArraySearch.binaryRangeSearch(new double[]{10}, Double.MAX_VALUE));

    assertEquals(-1, ArraySearch.binaryRangeSearch(new double[]{10, 12}, Double.MIN_VALUE));
    assertEquals(-1, ArraySearch.binaryRangeSearch(new double[]{10, 12}, 7));
    assertEquals( 0, ArraySearch.binaryRangeSearch(new double[]{10, 12}, 10));
    assertEquals( 1, ArraySearch.binaryRangeSearch(new double[]{10, 12}, 12));
    assertEquals( 1, ArraySearch.binaryRangeSearch(new double[]{10, 12}, 13));
    assertEquals( 1, ArraySearch.binaryRangeSearch(new double[]{10, 12}, Double.MAX_VALUE));
  }

  @Test public void testBinarySearch_doubles() {
    assertEquals(-1, ArraySearch.binarySearch(new double[0], 7));

    assertEquals(-1, ArraySearch.binarySearch(new double[]{10}, 7));
    assertEquals( 0, ArraySearch.binarySearch(new double[]{10}, 10));
    assertEquals(-1, ArraySearch.binarySearch(new double[]{10}, 11));

    assertEquals(-1, ArraySearch.binarySearch(new double[]{10, 12}, 7));
    assertEquals( 0, ArraySearch.binarySearch(new double[]{10, 12}, 10));
    assertEquals( 1, ArraySearch.binarySearch(new double[]{10, 12}, 12));
    assertEquals(-2, ArraySearch.binarySearch(new double[]{10, 12}, 13));
  }

  @Test public void testGradientRangeSearch_doubles() {
    assertEquals( 0, ArraySearch.gradientRangeSearch(new double[0], 7));

    assertEquals(-1, ArraySearch.gradientRangeSearch(new double[]{10}, Double.MIN_VALUE));
    assertEquals(-1, ArraySearch.gradientRangeSearch(new double[]{10}, 7));
    assertEquals( 0, ArraySearch.gradientRangeSearch(new double[]{10}, 10));
    assertEquals( 0, ArraySearch.gradientRangeSearch(new double[]{10}, 11));
    assertEquals( 0, ArraySearch.gradientRangeSearch(new double[]{10}, Double.MAX_VALUE));

    assertEquals(-1, ArraySearch.gradientRangeSearch(new double[]{10, 12}, Double.MIN_VALUE));
    assertEquals(-1, ArraySearch.gradientRangeSearch(new double[]{10, 12}, 7));
    assertEquals( 0, ArraySearch.gradientRangeSearch(new double[]{10, 12}, 10));
    assertEquals( 1, ArraySearch.gradientRangeSearch(new double[]{10, 12}, 12));
    assertEquals( 1, ArraySearch.gradientRangeSearch(new double[]{10, 12}, 13));
    assertEquals( 1, ArraySearch.gradientRangeSearch(new double[]{10, 12}, Double.MAX_VALUE));
  }

  @Test public void testGradientSearch_doubles() {
    assertEquals(-1, ArraySearch.gradientSearch(new double[0], 7));

    assertEquals(-1, ArraySearch.gradientSearch(new double[]{10}, 7));
    assertEquals( 0, ArraySearch.gradientSearch(new double[]{10}, 10));
    assertEquals(-1, ArraySearch.gradientSearch(new double[]{10}, 11));

    assertEquals(-1, ArraySearch.gradientSearch(new double[]{10, 12}, 7));
    assertEquals( 0, ArraySearch.gradientSearch(new double[]{10, 12}, 10));
    assertEquals( 1, ArraySearch.gradientSearch(new double[]{10, 12}, 12));
    assertEquals(-2, ArraySearch.gradientSearch(new double[]{10, 12}, 13));
  }
///////

  @Test public void testBinaryRangeSearch_Strings() {
    assertEquals( 0, ArraySearch.binaryRangeSearch(new String[0], "7"));

    assertEquals(-1, ArraySearch.binaryRangeSearch(new String[]{"80"}, ""));
    assertEquals(-1, ArraySearch.binaryRangeSearch(new String[]{"80"}, "7"));
    assertEquals( 0, ArraySearch.binaryRangeSearch(new String[]{"80"}, "80"));
    assertEquals( 0, ArraySearch.binaryRangeSearch(new String[]{"80"}, "81"));
    assertEquals( 0, ArraySearch.binaryRangeSearch(new String[]{"80"}, "ŽŽŽŽŽŽŽŽŽŽŽ"));

    assertEquals(-1, ArraySearch.binaryRangeSearch(new String[]{"80", "90"}, ""));
    assertEquals(-1, ArraySearch.binaryRangeSearch(new String[]{"80", "90"}, "7"));
    assertEquals( 0, ArraySearch.binaryRangeSearch(new String[]{"80", "90"}, "80"));
    assertEquals( 1, ArraySearch.binaryRangeSearch(new String[]{"80", "90"}, "90"));
    assertEquals( 1, ArraySearch.binaryRangeSearch(new String[]{"80", "90"}, "91"));
    assertEquals( 1, ArraySearch.binaryRangeSearch(new String[]{"80", "90"}, "ЫЫЫЫЫЫЫ"));
  }

  @Test public void testBinarySearch_Strings() {
    assertEquals(-1, ArraySearch.binarySearch(new String[0], "7"));

    assertEquals(-1, ArraySearch.binarySearch(new String[]{"80"}, "7"));
    assertEquals( 0, ArraySearch.binarySearch(new String[]{"80"}, "80"));
    assertEquals(-1, ArraySearch.binarySearch(new String[]{"80"}, "82"));

    assertEquals(-1, ArraySearch.binarySearch(new String[]{"80", "90"}, "7"));
    assertEquals( 0, ArraySearch.binarySearch(new String[]{"80", "90"}, "80"));
    assertEquals( 1, ArraySearch.binarySearch(new String[]{"80", "90"}, "90"));
    assertEquals(-2, ArraySearch.binarySearch(new String[]{"80", "90"}, "91"));
  }

  @Test public void testGradientRangeSearch_Strings() {
    assertEquals( 0, ArraySearch.gradientRangeSearch(new String[0], "7"));

    assertEquals(-1, ArraySearch.gradientRangeSearch(new String[]{"80"}, ""));
    assertEquals(-1, ArraySearch.gradientRangeSearch(new String[]{"80"}, "7"));
    assertEquals( 0, ArraySearch.gradientRangeSearch(new String[]{"80"}, "80"));
    assertEquals( 0, ArraySearch.gradientRangeSearch(new String[]{"80"}, "91"));
    assertEquals( 0, ArraySearch.gradientRangeSearch(new String[]{"80"}, "яяяяяяяяя"));

    assertEquals(-1, ArraySearch.gradientRangeSearch(new String[]{"80", "90"}, ""));
    assertEquals(-1, ArraySearch.gradientRangeSearch(new String[]{"80", "90"}, "7"));
    assertEquals( 0, ArraySearch.gradientRangeSearch(new String[]{"80", "90"}, "80"));
    assertEquals( 1, ArraySearch.gradientRangeSearch(new String[]{"80", "90"}, "90"));
    assertEquals( 1, ArraySearch.gradientRangeSearch(new String[]{"80", "90"}, "91"));
    assertEquals( 1, ArraySearch.gradientRangeSearch(new String[]{"80", "90"}, "уууууууу"));
  }

  @Test public void testGradientSearch_Strings() {
    assertEquals(-1, ArraySearch.gradientSearch(new String[0], "7"));

    assertEquals(-1, ArraySearch.gradientSearch(new String[]{"80"}, "7"));
    assertEquals( 0, ArraySearch.gradientSearch(new String[]{"80"}, "80"));
    assertEquals(-1, ArraySearch.gradientSearch(new String[]{"80"}, "99"));

    assertEquals(-1, ArraySearch.gradientSearch(new String[]{"80", "90"}, "7"));
    assertEquals( 0, ArraySearch.gradientSearch(new String[]{"80", "90"}, "80"));
    assertEquals( 1, ArraySearch.gradientSearch(new String[]{"80", "90"}, "90"));
    assertEquals(-2, ArraySearch.gradientSearch(new String[]{"80", "90"}, "91"));
  }

  private static Comparator<? super String> STRING_COMPARATOR = new Comparator<String>() {
    @Override
    public int compare(String o1, String o2) {
      return o1.compareTo(o2);
    }
  };

  @Test public void testFindAllWords() throws IOException {
    List<String> words = Files.readLines(new File("/usr/share/dict/words"), Charset.defaultCharset());
    words.sort(STRING_COMPARATOR);
    String[] ws = words.toArray(new String[words.size()]);
    ArraySearch sut = new ArraySearch();

//    sut.gradientSearchWithStats(ws, "qa");
//    fail("...");
    long now1 = System.nanoTime();
    for(String w : ws) {
      assertTrue("BS Failed on '" + w + "'", sut.binarySearchWithStats(ws, w) >= 0);
    }
    long now2 = System.nanoTime();
    for(String w : ws) {
      assertTrue("GS Failed on '" + w + "'", sut.gradientSearchWithStats(ws, w) >= 0);
    }
    long now3 = System.nanoTime();
    long t1 = now2-now1;
    long t2 = now3-now2;
    report("All words", ws.length, t2, t1);
    assertTrue(sut.binarySearchWithStats(ws, "") < 0);
    assertTrue(sut.gradientSearchWithStats(ws, "") < 0);
    assertTrue(sut.binarySearchWithStats(ws, "not a word") < 0);
    assertTrue(sut.gradientSearchWithStats(ws, "not a word") < 0);
  }
  
}
