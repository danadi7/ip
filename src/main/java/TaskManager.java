import java.io.*;

import java.util.ArrayList;

public class TaskManager {
    private ArrayList<Task> taskList;

    public TaskManager() {
        this.taskList = new ArrayList<Task>();
    }

    public TaskManager(String filePath) throws IOException {
        this.taskList = new ArrayList<Task>();
        try {
            loadFromFile(filePath);
        } catch (FileNotFoundException e) {
            File dukeDb = new File(filePath);
            dukeDb.getParentFile().mkdirs();
            dukeDb.createNewFile();
        }
    }

    public void parseCommand(String command) throws DukeException {
        if (command.startsWith("list")) {
            printList();
        } else if (command.startsWith("done ")) {
            String[] temp = command.split(" ");
            int doneIndex = Integer.parseInt(temp[1]) - 1;
            markTaskDone(doneIndex);;
        } else if (command.startsWith("delete ")) {
            String[] temp = command.split(" ");
            int delIndex = Integer.parseInt(temp[1]) - 1;
            deleteTask(delIndex);;
        } else if (command.startsWith("deadline")) {
            if (command.length() <= 9){
                throw new DukeException("Sorry, the description of a deadline cannot be empty!");
            }
            addNewDeadline(command.substring(9));
        } else if (command.startsWith("event")) {
            if (command.length() <= 6){
                throw new DukeException("Sorry, the description of a event cannot be empty!");
            }
            addNewEvent(command.substring(6));
        } else if (command.startsWith("todo")) {
            if (command.length() <= 5){
                throw new DukeException("The description of a todo cannot be empty!");
            }
            addNewTodo(command.substring(5));
        } else {
            throw new DukeException("Command not recognised!");
        }
    }

    public void addNewDeadline(String task) throws DukeException {
        String[] temp = task.split(" /by ");
        if (temp.length < 2) {
            throw new DukeException("Deadline not properly formatted!");
        }
        Deadline deadline = new Deadline(temp[0], temp[1]);
        taskList.add(deadline);
        System.out.println("\n Task added: " + deadline);
    }

    public void addNewEvent(String task) throws DukeException {
        String[] temp = task.split(" /at ");;
        if (temp.length < 2) {
            throw new DukeException("Event not properly formatted!");
        }
        Event event = new Event(temp[0], temp[1]);
        taskList.add(event);
        System.out.println("\n Task added: " + event);
    }

    public void addNewTodo(String task) {
        ToDo todo = new ToDo(task);
        taskList.add(todo);
        System.out.println("\n Task added: " + todo);
    }

    public void markTaskDone(int index) {
        Task completedTask = taskList.get(index);
        completedTask.markAsDone();
        System.out.println("The following task has been marked as done: \n" + completedTask);
    }

    public void deleteTask(int index) {
        Task toDeleteTask = taskList.get(index);
        taskList.remove(toDeleteTask);
        System.out.println("The following task has been removed: \n" + toDeleteTask);
    }

    public void printList() {
        if (taskList.isEmpty()) {
            System.out.println("List is empty!");
        } else {
            String s = "";
            for (int i = 0; i < taskList.size(); i++) {
                s = s + (i + 1) + ". " + taskList.get(i) + "\n";
            }
            System.out.println(s);
        }
    }

    public void saveToFile(String filePath) throws IOException {
        File dukeDb = new File(filePath);
        if (!dukeDb.exists()) {
            dukeDb.getParentFile().mkdirs();
            dukeDb.createNewFile();
        }
        FileWriter writer = new FileWriter(filePath, false);
        for (Task task : taskList) {
            writer.write(task.toDataString() + "\n");
        }
        writer.close();
    }

    public void loadFromFile(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            String[] taskLine = line.split("\\|");
            switch (taskLine[0]) {
                case("T"):
                    ToDo todo = new ToDo(taskLine[2], Boolean.parseBoolean(taskLine[1]));
                    taskList.add(todo);
                    break;
                case("D"):
                    Deadline deadline = new Deadline(taskLine[2], Boolean.parseBoolean(taskLine[1]), taskLine[3]);
                    taskList.add(deadline);
                    break;
                case("E"):
                    Event event = new Event(taskLine[2], Boolean.parseBoolean(taskLine[1]), taskLine[3]);
                    taskList.add(event);
            }
        }
    }
}
