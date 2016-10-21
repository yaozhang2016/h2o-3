package water.util;

/**
 * Search methods for arrays
 */
public class ArraySearch {
  // behaves like Arrays.binarySearch, but is slower -> Just good for tiny arrays (length<20)
  public static int linearSearch(double[] vals, double v) {
    final int N=vals.length;
    for (int i=0; i<N; ++i) {
      if (vals[i]==v) return i;
      if (vals[i]>v) return -i-1;
    }
    return -1;
  }

  static int _for_testing_number_of_comparisons_in_binary_search;

  public static int binaryRangeSearch(long[] ys, long y) {
    _for_testing_number_of_comparisons_in_binary_search = 1;
    if (y < ys[0]) return -1;
    _for_testing_number_of_comparisons_in_binary_search++;
    if (y >= ys[ys.length - 1]) return ys.length - 1;

    int lo=0, hi = ys.length - 1;
    while( lo < hi-1 ) {
      int mid = (hi+lo)>>>1;
      _for_testing_number_of_comparisons_in_binary_search++;
      if( y < ys[mid] ) hi = mid;
      else              lo = mid;
    }

    while( ys[lo+1] == y) lo++;
    return lo;
  }

  static int _for_testing_number_of_comparisons_in_grad_search;

  public static int gradientRangeSearch(long[] ys, long y) {
    _for_testing_number_of_comparisons_in_grad_search = 1;
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
      _for_testing_number_of_comparisons_in_grad_search++;
      if (y < midVal) hi = mid - 1;
      else            lo = mid + 1;
    }
    if (lo <= last && y < ys[lo]) lo--;
    while( lo < last-1 && ys[lo+1] == y) lo++;
    return lo;
  }
}
