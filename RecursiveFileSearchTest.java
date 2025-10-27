import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RecursiveFileSearchTest {

    private Path tempDir;
    private RecursiveFileSearch searcher;

    @BeforeEach
    void setUp() throws IOException {
        searcher = new RecursiveFileSearch();
        tempDir = Files.createTempDirectory("testdir_");

        // Create test files and folders
        Files.createFile(tempDir.resolve("file1.txt"));
        Files.createFile(tempDir.resolve("file2.txt"));

        Path subDir = Files.createDirectory(tempDir.resolve("subdir"));
        Files.createFile(subDir.resolve("target.txt"));

        Files.createFile(tempDir.resolve("Report.TXT"));
    }

    @AfterEach
    void tearDown() throws IOException {
        // Delete temporary directory recursively
        Files.walk(tempDir)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    // ✅ Test Case 1: Basic valid search
    @Test
    void testBasicSearch() {
        List<String> fileNames = List.of("file1.txt", "file2.txt");
        Map<String, Integer> result = searcher.searchFiles(tempDir.toString(), true, fileNames);

        assertFalse(result.isEmpty());
        assertTrue(result.keySet().stream().anyMatch(path -> path.endsWith("file1.txt")));
        assertTrue(result.keySet().stream().anyMatch(path -> path.endsWith("file2.txt")));
        assertEquals(9, result.get(result.keySet().stream()
                .filter(path -> path.endsWith("file1.txt"))
                .findFirst()
                .orElseThrow()));
    }

    // ✅ Test Case 2: Case-insensitive search
    @Test
    void testCaseInsensitiveSearch() {
        List<String> fileNames = List.of("report.txt");
        Map<String, Integer> result = searcher.searchFiles(tempDir.toString(), false, fileNames);

        assertEquals(1, result.size());
        assertTrue(result.keySet().stream().anyMatch(path -> path.endsWith("Report.TXT")));
    }

    // ✅ Test Case 3: Invalid start path
    @Test
    void testInvalidStartPath() {
        List<String> fileNames = List.of("file1.txt");
        Map<String, Integer> result = searcher.searchFiles("non_existing_directory", true, fileNames);

        assertTrue(result.isEmpty());
    }

    // ✅ Test Case 4: Empty file names list
    @Test
    void testEmptyFileNamesList() {
        Map<String, Integer> result = searcher.searchFiles(tempDir.toString(), true, List.of());

        assertTrue(result.isEmpty());
    }

    // ✅ Test Case 5: Recursive search in subdirectories
    @Test
    void testRecursiveSearch() {
        List<String> fileNames = List.of("target.txt");
        Map<String, Integer> result = searcher.searchFiles(tempDir.toString(), true, fileNames);

        assertEquals(1, result.size());
        assertTrue(result.keySet().stream().anyMatch(path -> path.contains("subdir") && path.endsWith("target.txt")));
    }
}
