package water.util;

import java.util.Arrays;

/**
 * Search methods for arrays
 */
public class ArraySearch {
  ArraySearch() {} // good for testing
  
  private static ArraySearch instance = new ArraySearch();

  static class Stats {
    int numberOfComparisons = 0;
    int size = 0;
    double avg() { 
      return numberOfComparisons*1.0/(size == 0 ? 1 : size);
    }
  }

  //====================================================
  // long
  //====================================================

  Stats stats_long_binary = new Stats();

  public static int binaryRangeSearch(long[] ys, long y) {
    return instance.binaryRangeSearchWithStats(ys, y);
  }

  private static int selectFound(long[] ys, long y, int index) {
    return index >= 0 && index < ys.length && ys[index] == y ? index : -1;
  }
  
  public static int binarySearch(long[] ys, long y) {
    return selectFound(ys, y, binaryRangeSearch(ys, y));
  }

  int binaryRangeSearchWithStats(long[] ys, long y) {
    stats_long_binary.size++;
    if (ys.length == 0 || y < ys[0]) return -1;
    stats_long_binary.numberOfComparisons++;
    if (y >= ys[ys.length - 1]) return ys.length - 1;

    int lo=0, hi = ys.length - 1;
    while( lo < hi-1 ) {
      int mid = (hi+lo)>>>1;
      if( y < ys[mid] ) hi = mid;
      else              lo = mid;
    }

    while( ys[lo+1] == y) lo++;
    return lo;
  }

  Stats stats_long_gradient = new Stats();

  public static int gradientRangeSearch(long[] ys, long y) {
    return instance.gradientRangeSearchWithStats(ys, y);
  }

  public static int gradientSearch(long[] ys, long y) {
    return selectFound(ys, y, gradientRangeSearch(ys, y));
  }

  int gradientRangeSearchWithStats(long[] ys, long y) {
    stats_long_gradient.size++;
    int lo = 0;
    int last = ys.length - 1;
    int hi = last;
    while (lo < hi) {
      long loVal = ys[lo];
      int dx = hi - lo;
      long dy = ys[hi] - loVal;
      int mid = dy < dx ? (lo+hi)/2 : // small derivative => use binary
          Math.max(lo, Math.min(hi-1, lo+(int)((y - loVal)*dx/dy)));
      long midVal = ys[mid];
      if (y < midVal) hi = mid - 1;
      else            lo = mid + 1;
    }
    if (lo <= last && y < ys[lo]) lo--;
    while( lo < last-1 && ys[lo+1] == y) lo++;
    return lo;
  }

  //====================================================
  // int
  //====================================================

  Stats stats_int_binary = new Stats();

  public static int binaryRangeSearch(int[] ys, int y) {
    return instance.binaryRangeSearchWithStats(ys, y);
  }

  private static int selectFound(int[] ys, int y, int index) {
    return index >= 0 && index < ys.length && ys[index] == y ? index : -1;
  }

  public static int binarySearch(int[] ys, int y) {
    return selectFound(ys, y, binaryRangeSearch(ys, y));
  }

  int binaryRangeSearchWithStats(int[] ys, int y) {
    stats_int_binary.size++;
    stats_int_binary.numberOfComparisons++;
    if (ys.length == 0 || y < ys[0]) return -1;
    stats_int_binary.numberOfComparisons++;
    if (y >= ys[ys.length - 1]) return ys.length - 1;

    int lo=0, hi = ys.length - 1;
    while( lo < hi-1 ) {
      int mid = (hi+lo)/2;
      stats_int_binary.numberOfComparisons++;
      if (y < ys[mid]) hi = mid;
      else             lo = mid;
    }

    while (lo+1 < ys.length && ys[lo+1] == y) lo++;
    return lo;
  }

  Stats stats_int_gradient = new Stats();

  public static int gradientRangeSearch(int[] ys, int y) {
    return instance.gradientRangeSearchWithStats(ys, y);
  }

  public static int gradientSearch(int[] ys, int y) {
    return selectFound(ys, y, gradientRangeSearch(ys, y));
  }

  int gradientRangeSearchWithStats(int[] ys, int y) {
    stats_int_gradient.size++;
    stats_int_gradient.numberOfComparisons++;
    int lo = 0;
    int last = ys.length - 1;
    int hi = last;
    while (lo < hi) {
      int loVal = ys[lo];
      int dx = hi - lo;
      int dy = ys[hi] - loVal;
      int mid = dy < dx ? (lo+hi)/2 : // small derivative => use binary
          Math.max(lo, Math.min(hi-1, lo + ((y - loVal)*dx/dy)));
      int midVal = ys[mid];
      stats_int_gradient.numberOfComparisons++;
      if (y < midVal) hi = mid - 1;
      else            lo = mid + 1;
    }
    if (lo <= last && y < ys[lo]) lo--;
    while( lo < last-1 && ys[lo+1] == y) lo++;
    return lo;
  }

  //====================================================
  // double
  //====================================================

  Stats stats_double_binary = new Stats();

  // behaves like Arrays.binarySearch, but is slower -> Just good for tiny arrays (length<20)
  public static int linearSearch(double[] vals, double v) {
    final int N=vals.length;
    for (int i=0; i<N; ++i) {
      if (vals[i]==v) return i;
      if (vals[i]>v) return -i-1;
    }
    return -1;
  }

  private static int selectFound(double[] ys, double y, int index) {
    return index < 0 ? index : (index < ys.length && ys[index] == y) ? index : -index-1;
  }

  static int binarySearch(double[] ys, double y) {
    return selectFound(ys, y, binaryRangeSearch(ys, y));
  }

  static int binaryRangeSearch(double[] ys, double y) {
    return instance.binaryRangeSearchWithStats(ys, y);
  }

  int binaryRangeSearchWithStats(double[] ys, double y) {
    stats_double_binary.size++;
    int lo = 0;
    int last = ys.length - 1;
    int hi = last;

    while (lo < hi) {
      int mid = (lo + hi) >>> 1;
      double midVal = ys[mid];

      stats_double_binary.numberOfComparisons++;
      if (midVal < y)
        lo = mid + 1;  // Neither val is NaN, thisVal is smaller
      else if (midVal > y) {
        stats_double_binary.numberOfComparisons++;
        hi = mid - 1; // Neither val is NaN, thisVal is larger
      } else {
        long midBits = Double.doubleToLongBits(midVal);
        long keyBits = Double.doubleToLongBits(y);
        stats_double_binary.numberOfComparisons+=2;
        if (midBits == keyBits)     // Values are equal
          return mid;             // Key found
        else if (midBits < keyBits) // (-0.0, 0.0) or (!NaN, NaN)
          lo = mid + 1;
        else                        // (0.0, -0.0) or (NaN, !NaN)
          hi = mid - 1;
        stats_double_binary.numberOfComparisons++;
      }
    }
    if (lo <= last && y < ys[lo]) lo--;
    while( lo < last-1 && ys[lo+1] == y) lo++;
    return lo;
  }

  Stats stats_double_gradient = new Stats();

  public static int gradientRangeSearch(double[] ys, double y) {
    return instance.gradientRangeSearchWithStats(ys, y);
  }

  public static int gradientSearch(double[] ys, double y) {
    return selectFound(ys, y, gradientRangeSearch(ys, y));
  }

  int gradientRangeSearchWithStats(double[] ys, double y) {
    stats_double_gradient.size++;
    stats_double_gradient.numberOfComparisons++;
    int lo = 0;
    int last = ys.length - 1;
    int hi = last;
    while (lo < hi) {
      double loVal = ys[lo];
      int dx = hi - lo;
      double dy = ys[hi] - loVal;
      int mid = dy < dx ? (lo+hi)/2 : // small derivative => use binary
          Math.max(lo, Math.min(hi-1, lo+(int)((y - loVal)*dx/dy)));
      double midVal = ys[mid];
      stats_double_gradient.numberOfComparisons++;
      if (y < midVal) hi = mid - 1;
      else            lo = mid + 1;
    }
    if (lo <= last && y < ys[lo]) lo--;
    while( lo < last-1 && ys[lo+1] == y) lo++;
    return lo;
  }

  //====================================================
  // String
  //====================================================

  Stats stats_string_binary = new Stats();

  // behaves like Arrays.binarySearch, but is slower -> Just good for tiny arrays (length<20)
  public static int linearSearch(String[] vals, String v) {
    if (v == null) return -1;
    final int N=vals.length;
    for (int i=0; i<N; ++i) {
      String vi = vals[i];
      int comp = vi == null ? -1 : vi.compareTo(v);
      if (comp == 0) return i;
      if (comp <  0) return -i-1;
    }
    return -1;
  }

  private static int selectFound(String[] ys, String y, int index) {
    return index < 0 ? index : (index < ys.length && y.equals(ys[index])) ? index : -index-1;
  }

  static int binarySearch(String[] ys, String y) {
    return selectFound(ys, y, binaryRangeSearch(ys, y));
  }

  static int binaryRangeSearch(String[] ys, String y) {
    return instance.binaryRangeSearchWithStats(ys, y);
  }

  int binarySearchWithStats(String[] ys, String y) {
    return selectFound(ys, y, binaryRangeSearchWithStats(ys, y));
  }


  int binaryRangeSearchWithStats(String[] ys, String y) {
    stats_string_binary.size++;
    if (y == null) return -1;
    int lo = 0;
    int last = ys.length - 1;
    int hi = last;

    while (lo < hi) {
      int mid = (lo + hi) >>> 1;
      String midVal = ys[mid];

      stats_string_binary.numberOfComparisons++;
      int compared = midVal.compareTo(y);
      if (compared < 0)
        lo = mid + 1;  // Neither val is NaN, thisVal is smaller
      else if (compared > 0)
        hi = mid - 1; // Neither val is NaN, thisVal is larger
      else return mid;             // Key found
    }
    stats_string_binary.numberOfComparisons++;
    if (lo <= last && y.compareTo(ys[lo]) < 0) lo--;
    while( lo < last-1 && y.equals(ys[lo+1])) lo++;
    return lo;
  }

  Stats stats_string_gradient = new Stats();

  public static int gradientRangeSearch(String[] ys, String y) {
    return instance.gradientRangeSearchWithStats(ys, y);
  }

  public static int gradientSearch(String[] ys, String y) {
    return selectFound(ys, y, gradientRangeSearch(ys, y));
  }

  public int gradientSearchWithStats(String[] ys, String y) {
    return selectFound(ys, y, gradientRangeSearchWithStats(ys, y));
  }
  
  // the algorithm is wrong. Difference in the 3rd letter beats the diff in the second letter.
  // have to skip to the position where loVal and hiVal differ
  int gradientRangeSearchWithStats(String[] ys, String y) {
    stats_string_gradient.size++;
    if (y == null) return -1;
    if (ys.length == 0) return 0;
    stats_string_gradient.numberOfComparisons++;
    int lo = 0;
    int last = ys.length - 1;
    int hi = last;
    int pos = 0;
//    System.out.println("Looking for '" + y + "'");
    boolean dobin = false;
    while (lo < hi) {
      String loVal0 = ys[lo];
      String hiVal0 = ys[hi];
//      System.out.println(loVal0 + ".." + hiVal0);
      int mid = (lo+hi)/2;
      if (!dobin) {
        while (pos < loVal0.length() && pos < hiVal0.length() && pos < y.length() && loVal0.charAt(pos) == hiVal0.charAt(pos)) pos++;
        String loVal = loVal0.substring(pos);
        String hiVal = hiVal0.substring(pos);
        String cy = y.substring(pos);
        int dx = hi - lo;
        long dy = cy.compareTo(loVal);
        long dy0 = hiVal.compareTo(loVal);
        mid = Math.max(lo, Math.min(hi-1, lo+(int)(dy*dx/dy0)));
      }
      dobin = !dobin;
      String midVal = ys[mid];
      stats_string_gradient.numberOfComparisons++;
      int dy1 = y.compareTo(midVal);
      if (dy1 < 0) hi = mid - 1;
      else {
        lo = mid+1;
        if (dy1 == 0) hi = lo;
      }
    }
    if (lo <= last && y.compareTo(ys[lo]) < 0) lo--;
    while( lo < last-1 && y.equals(ys[lo+1])) lo++;
    return lo;
  }

}
