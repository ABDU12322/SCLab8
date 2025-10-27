public class main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java main <startDir> <caseSensitive:true|false> <file1> [file2 ...]");
            System.out.println("Example: java main C:\\Temp false notes.txt README.md");
            return;
        }

        String startPath = args[0];
        boolean caseSensitive = Boolean.parseBoolean(args[1]);

        java.util.List<String> targets = new java.util.ArrayList<>();
        for (int i = 2; i < args.length; i++) {
            targets.add(args[i]);
        }

        RecursiveFileSearch rfs = new RecursiveFileSearch();

        // All validation about existence and directory checks happens inside
        // RecursiveFileSearch.searchFiles now, so main only forwards arguments.
        java.util.Map<String, Integer> results = rfs.searchFiles(startPath, caseSensitive, targets);

        if (results.isEmpty()) {
            System.out.println("No matching files found.");
        } else {
            System.out.println("Found files:");
            for (java.util.Map.Entry<String, Integer> e : results.entrySet()) {
                System.out.printf("%s (name length = %d)%n", e.getKey(), e.getValue());
            }
            System.out.println("Total matches: " + results.size());
        }
    }
}
