import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecursiveStringPermutationsTest {

    private RecursiveStringPermutations rsp;

    @BeforeEach
    void setUp() {
        rsp = new RecursiveStringPermutations();
    }

    // ✅ Test Case 1: Basic permutation generation for "abc"
    @Test
    void testGeneratePermutationsBasic() {
        rsp.permutations.clear();
        rsp.generatePermutations("", "abc");

        List<String> expected = List.of("abc", "acb", "bac", "bca", "cab", "cba");
        assertEquals(expected.size(), rsp.permutations.size());
        assertTrue(rsp.permutations.containsAll(expected));
    }

    // ✅ Test Case 2: Single character string
    @Test
    void testGeneratePermutationsSingleCharacter() {
        rsp.permutations.clear();
        rsp.generatePermutations("", "a");

        List<String> expected = List.of("a");
        assertEquals(expected, rsp.permutations);
    }

    // ✅ Test Case 3: Empty string input
    @Test
    void testGeneratePermutationsEmptyString() {
        rsp.permutations.clear();
        rsp.generatePermutations("", "");

        // Expect only one permutation — the empty prefix itself
        assertEquals(1, rsp.permutations.size());
        assertEquals("", rsp.permutations.get(0));
    }

    // ✅ Test Case 4: String with duplicate characters (no filtering here)
    @Test
    void testGeneratePermutationsWithDuplicates() {
        rsp.permutations.clear();
        rsp.generatePermutations("", "aab");

        // Expected 6 permutations (since duplicates are not filtered here)
        assertEquals(6, rsp.permutations.size());
        assertTrue(rsp.permutations.contains("aab"));
        assertTrue(rsp.permutations.contains("aba"));
        assertTrue(rsp.permutations.contains("baa"));
    }

    // ✅ Test Case 5: Non-empty prefix provided
    @Test
    void testGeneratePermutationsWithPrefix() {
        rsp.permutations.clear();
        rsp.generatePermutations("x", "yz");

        List<String> expected = List.of("xyz", "xzy");
        assertEquals(expected.size(), rsp.permutations.size());
        assertTrue(rsp.permutations.containsAll(expected));
    }
}
