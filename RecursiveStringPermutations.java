import java.util.ArrayList;
import java.util.List;

public class RecursiveStringPermutations {
    public boolean allowDuplicates;
    List<String> permutations = new ArrayList<>();
    public void setAllowDuplicates(boolean allowDuplicates){
        this.allowDuplicates = allowDuplicates;
    }
    /**
     * Returns a string with duplicate characters removed while preserving the
     * first-occurrence order. If duplicates are allowed this returns the
     * original string unchanged.
     *
     * @param s input string
     * @return deduplicated string when duplicates are not allowed, otherwise original
     */
    public String dedupeInput(String s){
        if (s == null) return null;
        if (allowDuplicates) return s;

        StringBuilder sb = new StringBuilder();
        java.util.LinkedHashSet<Character> seen = new java.util.LinkedHashSet<>();
        for (char c : s.toCharArray()){
            if (seen.add(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    public void initFunction(String firstString, String str){
        // prepare for a fresh run
        permutations.clear();

        // start generation: firstString is used as the prefix; if caller
        // passes null, treat it as empty prefix
        String prefix = (firstString == null) ? "" : firstString;

        // If duplicates are not allowed, generate permutations on the string
        // with duplicate characters removed (preserving first occurrence order).
        String effective = dedupeInput(str);
        if (effective == null) return;
        if (!effective.equals(str)){
            System.out.println("Duplicates removed; generating permutations of: " + effective);
        }

        // generate permutations using prefix and the effective remaining string
        generatePermutations(prefix, effective);
    }
    public void generatePermutations(String firstString, String str) {
        if( str.isEmpty()){
            permutations.add(firstString);
            return;
        }
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            String newStr = str.substring(0, i) + str.substring(i + 1);
            generatePermutations(firstString + currentChar, newStr);
        }
    }

    public static void main(String[] args) {
        RecursiveStringPermutations rsp = new RecursiveStringPermutations();
        // Use first command-line argument as input if provided; otherwise use a small default
        String input = (args != null && args.length > 0) ? args[0] : "abc";
        rsp.setAllowDuplicates(false);

        // start with empty prefix and the input as the remaining characters
        rsp.initFunction("", input);
        for (String perm : rsp.permutations) {
            System.out.println(perm);
            
        }
        
    }
}