package advancedstreams;

public class IsPalindrome {

  public static boolean isPalindrome(String string) {
    int length = string.length();
    for (int i = 0; i < length / 2; i++) {
      if (string.charAt(i) != string.charAt(length - 1 - i)) {
        return false;
      }
    }
    return true;
  }
}
