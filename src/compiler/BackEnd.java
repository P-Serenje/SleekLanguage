package compiler;
import java.io.File;
import java.io.IOException;

public class BackEnd {
	 /**
     * Compiles an LLVM IR (.ll) file with runtime.c using clang.
     * The output executable is named after the .ll input file (without the extension).
     *
     * @param llFilePath Path to the .ll file.
     */
    public static void compileLLVMIR(String llFilePath) {
        // Create a File object from the input path
        File llFile = new File(llFilePath);

        // Check if file exists and is a .ll file
        if (!llFile.exists() || !llFilePath.endsWith(".ll")) {
            System.err.println("Invalid file: " + llFilePath);
            return;
        }

        // Derive output name by removing the ".ll" extension
        String outputName = llFilePath.substring(0, llFilePath.lastIndexOf(".ll"));

        // Command to run
        String command = String.format("clang %s runtime.c -O2 -o %s", llFilePath, outputName);

        try {
            // Use ProcessBuilder to run the command
            ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", command);
            builder.inheritIO(); // So output/error streams are shown in the terminal
            Process process = builder.start();

            // Wait for the process to finish
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Compilation successful. Executable created: " + outputName);
            } else {
                System.err.println("Compilation failed with exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Example usage
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Please provide a .ll file path.");
            return;
        }
        compileLLVMIR(args[0]);
    }
}
