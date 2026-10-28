import java.io.*;         // BufferedWriter, IOException
import java.nio.file.*;   // Files, Path, StandardOpenOption
import java.time.*;       // LocalDateTime, format.LocalDateTimeFormatter
import java.time.format.DateTimeFormatter;
import java.util.Scanner; // For reading keyboard inputs

public class EventLogger
{
    private static final int TARGET_ENTRIES = 100;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm:ss");


    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);

        try
        {
            Path outputPath = getOutputPath(keyboard); // function call

            // file already exists, choose another filename > no
            if (outputPath == null)
            {
                System.out.println("Program cancelled.");
                return;
            }

            int entryCount = 0;

            //opening the file for writing log entries
            try (BufferedWriter writer = Files.newBufferedWriter(                    outputPath,
                    StandardOpenOption.CREATE_NEW,
                    StandardOpenOption.WRITE))
            {

                writer.write("Entry Number,Date,Time\n"); // title in file
                writer.newLine();
                writer.flush();

                System.out.println("\nReady to record events.");
                System.out.println("Press \"ENTER\" to record an event.");
                System.out.println("Type \"CLOSE\" and press \"ENTER\" to finish.");
                System.out.println("Maximum entries: " + TARGET_ENTRIES);
                System.out.println("File: " + outputPath.toAbsolutePath());

                while (entryCount < TARGET_ENTRIES)
                {
                    System.out.print("\nEntry " + (entryCount + 1)
                            + " - ENTER to record, CLOSE to stop: ");

                    String input = keyboard.nextLine().trim();

                    // if "CLOSE" is typed
                    if (input.equalsIgnoreCase("CLOSE"))
                    {
                        break;
                    }

                    // if anything other than "ENTER" or "CLOSE"
                    if (!input.isEmpty())
                    {
                        System.out.println(
                                "Press \"ENTER\" on an empty line, or type \"CLOSE\".");
                        continue;
                    }

                    LocalDateTime now = LocalDateTime.now();
                    entryCount++;

                    String date = now.format(DATE_FORMAT);
                    String time = now.format(TIME_FORMAT);

                    writer.write(entryCount + "," + date + "," + time);
                    writer.newLine();
                    writer.flush();

                    System.out.println("Recorded: " + entryCount
                            + "," + date + "," + time);
                }

                LocalDateTime closedAt = LocalDateTime.now(); // close file

                writer.newLine();
                writer.write("Session Closed,"
                        + closedAt.format(DATE_FORMAT) + ","
                        + closedAt.format(TIME_FORMAT));
                writer.newLine();
                writer.flush();

                System.out.println("\nRecording finished.");
                System.out.println("Total events: " + entryCount);
                System.out.println("Closing time: "
                        + closedAt.format(DATE_FORMAT) + " "
                        + closedAt.format(TIME_FORMAT));
            }

            System.out.println("File saved to: "
                    + outputPath.toAbsolutePath());

        }
        catch (IOException e)
        {
            System.out.println("File error: " + e.getMessage());
        }
    }

    private static Path getOutputPath(Scanner scanner)
    {
        while (true)
        {
            System.out.print(
                "Enter filename (e.g., event_times.txt) to create a new file: ");
            String fileName = scanner.nextLine().trim();

            if (fileName.isEmpty())
            {
                System.out.println("Filename cannot be empty.");
                continue;
            }

            if (!fileName.toLowerCase().endsWith(".txt"))
            {
                fileName += ".txt";
            }

            Path path = Path.of(fileName);

            if (Files.exists(path))
            {
                System.out.println("File already exists.");
                System.out.print("Choose another filename? (yes/no): ");

                String answer = scanner.nextLine().trim();

                if (answer.equalsIgnoreCase("yes")
                        || answer.equalsIgnoreCase("y"))
                {
                    continue;
                }

                if (answer.equalsIgnoreCase("no")
                        || answer.equalsIgnoreCase("n"))
                {
                    return null;
                }

                System.out.println("Please answer yes or no.");
                continue;
            }

            return path;
        }
    }
}

