import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecursiveFileSearch {
    public Map<String, Integer> foundFiles = new HashMap<>();
    public boolean caseSensitive;
    public void setCaseSensitive(boolean caseSensitive){
        this.caseSensitive = caseSensitive;
    }
    /**
     * Public entry point. Validates startPath and sets case sensitivity, then
     * performs a recursive search. All checks (start path, directories, nulls)
     * are handled here and in the private recursive helper.
     *
     * @param startPath    starting directory path to search
     * @param caseSensitive whether filename matching should be case sensitive
     * @param fileNames    list of target file names to find
     * @return map of absolute paths to matched file name lengths
     */
    public Map<String, Integer> searchFiles(String startPath, boolean caseSensitive, List<String> fileNames){
        foundFiles.clear();
        setCaseSensitive(caseSensitive);

        if (startPath == null) {
            System.err.println("Error: startPath is null");
            return foundFiles;
        }

        if (fileNames == null || fileNames.isEmpty()){
            System.err.println("Warning: fileNames is null or empty — nothing to search for");
            return foundFiles;
        }

        File startDir = new File(startPath);
        if (!startDir.exists() || !startDir.isDirectory()){
            System.err.println("Error: start path does not exist or is not a directory: " + startPath);
            return foundFiles;
        }

        // delegate to recursive helper
        searchFiles(startDir, fileNames);
        return foundFiles;
    }

    // Private recursive helper that assumes dir is a valid directory and
    // fileNames is non-null/non-empty.
    /**
     * Recursively searches the given directory and its subdirectories
     * for files matching any of the specified names.
     *
     * @param dir the directory to search in (must exist)
     * @param fileNames list of file names to look for
     *
     * Adds each found file’s absolute path and its name length
     * to the foundFiles map, considering case sensitivity.
     */

    private void searchFiles(File dir, List<String> fileNames){
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files){
            if (file.isDirectory()){
                searchFiles(file, fileNames);
            } else {
                for (String fileName : fileNames){
                    if (fileName == null) continue;
                    if ((caseSensitive && file.getName().equals(fileName)) ||
                        (!caseSensitive && file.getName().equalsIgnoreCase(fileName))) {
                        foundFiles.put(file.getAbsolutePath(), fileName.length());
                    }
                }
            }
        }
    }
}
