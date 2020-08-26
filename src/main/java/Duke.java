import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String welcome = "Hello. I am Claude! What may I do for you today?";
        String goodbye = "Goodbye! Hope to see you again soon!";
        Scanner sc = new Scanner(System.in);
        TaskManager tm = new TaskManager();

        System.out.println(welcome);
        while (sc.hasNext()) {
            String command = sc.nextLine();
            if (command.equals("bye")) {
                break;
            } else {
                try {
                    tm.parseCommand(command);
                } catch (DukeException e) {
                    System.out.println(e);
                }
            }
        }
        System.out.println(goodbye);
    }
}
