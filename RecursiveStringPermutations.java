import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Consumer;

public class RecursiveStringPermutations {
    public boolean allowDuplicates;
    List<String> permutations = new ArrayList<>();

    public void setAllowDuplicates(boolean allowDuplicates) {
        this.allowDuplicates = allowDuplicates;
    }

    /**
     * Removes duplicate characters while preserving order if duplicates are not allowed.
     *
     * @param input input string
     * @return deduplicated string or original if duplicates allowed
     */
    public String dedupeInput(String input) {
        if (input == null) return null;
        if (allowDuplicates) return input;

        StringBuilder result = new StringBuilder();
        LinkedHashSet<Character> seenChars = new LinkedHashSet<>();
        for (char ch : input.toCharArray()) {
            if (seenChars.add(ch)) {
                result.append(ch);
            }
        }
        return result.toString();
    }

    /** Iterative Heap’s algorithm (stores permutations). */
    public void generatePermutationsIterative(String input) {
        permutations.clear();
        if (input == null) return;
        int length = input.length();
        if (length == 0) return;

        char[] chars = input.toCharArray();
        int[] control = new int[length];
        permutations.add(new String(chars));
        int position = 0;

        while (position < length) {
            if (control[position] < position) {
                int swapIndex = (position % 2 == 0) ? 0 : control[position];
                swap(chars, swapIndex, position);
                permutations.add(new String(chars));
                control[position]++;
                position = 0;
            } else {
                control[position] = 0;
                position++;
            }
        }
    }

    /** Iterative permutation generator using consumer for streaming (no storing). */
    public void generatePermutationsIterativeStream(String input, Consumer<String> consumer) {
        if (input == null || consumer == null) return;
        int length = input.length();
        if (length == 0) return;

        char[] chars = input.toCharArray();
        int[] control = new int[length];
        consumer.accept(new String(chars));
        int position = 0;

        while (position < length) {
            if (control[position] < position) {
                int swapIndex = (position % 2 == 0) ? 0 : control[position];
                swap(chars, swapIndex, position);
                consumer.accept(new String(chars));
                control[position]++;
                position = 0;
            } else {
                control[position] = 0;
                position++;
            }
        }
    }

    /** Recursive permutation generator using consumer (no storing). */
    public void generatePermutationsRecursiveStream(String input, Consumer<String> consumer) {
        if (input == null || consumer == null) return;
        char[] chars = input.toCharArray();
        permuteRecStream(chars, 0, consumer);
    }

    private void permuteRecStream(char[] chars, int startIndex, Consumer<String> consumer) {
        if (startIndex == chars.length) {
            consumer.accept(new String(chars));
            return;
        }
        for (int i = startIndex; i < chars.length; i++) {
            swap(chars, startIndex, i);
            permuteRecStream(chars, startIndex + 1, consumer);
            swap(chars, startIndex, i);
        }
    }

    /** Swaps two characters in an array. */
    private void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    /** Performance comparison between recursive and iterative methods. */
    public static void main(String[] args) {
        RecursiveStringPermutations rsp = new RecursiveStringPermutations();
        String input = (args != null && args.length > 0) ? args[0] : "abcd";
        rsp.setAllowDuplicates(false);

        String effective = rsp.dedupeInput(input);

        System.out.println("Input string: " + input);
        System.out.println("Duplicates allowed: " + rsp.allowDuplicates);
        System.out.println("Effective string used: " + effective);
        System.out.println("\n--- Performance Comparison ---");

        // Measure recursive performance
        long recursiveStart = System.nanoTime();
        final int[] recCount = {0};
        rsp.generatePermutationsRecursiveStream(effective, s -> recCount[0]++);
        long recursiveEnd = System.nanoTime();
        long recursiveTime = recursiveEnd - recursiveStart;

        // Measure iterative performance
        long iterativeStart = System.nanoTime();
        final int[] iterCount = {0};
        rsp.generatePermutationsIterativeStream(effective, s -> iterCount[0]++);
        long iterativeEnd = System.nanoTime();
        long iterativeTime = iterativeEnd - iterativeStart;

        // Display performance results
        System.out.println("Recursive method:");
        System.out.println("  Permutations generated: " + recCount[0]);
        System.out.println("  Time taken: " + (recursiveTime / 1_000_000.0) + " ms");

        System.out.println("Iterative method:");
        System.out.println("  Permutations generated: " + iterCount[0]);
        System.out.println("  Time taken: " + (iterativeTime / 1_000_000.0) + " ms");

        System.out.println("\nFaster method: " +
                (recursiveTime < iterativeTime ? "Recursive" : "Iterative"));
    }
}
