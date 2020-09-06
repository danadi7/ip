package duke.logic.commands;

import java.io.IOException;
import java.util.ArrayList;

import duke.exceptions.DukeException;
import duke.model.TaskManager;
import duke.model.task.Task;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Handles 'bye' command input by user.
 */
public class ExitCommand extends Command{

    /**
     * Constructor for ExitCommand class.
     *
     * @param command String input by user.
     */
    public ExitCommand(String command) {
        super(command);
    }

    /**
     * Saves the current Tasks to disk and shows exit message.
     *
     * @param tm TaskManager that handles tasks in memory.
     * @param ui User interface that interacts with the user.
     * @param storage Storage class that handles saving and loading from file.
     * @throws DukeException If command is not properly formatted.
     */
    @Override
    public void execute(TaskManager tm, Ui ui, Storage storage) throws DukeException {
        String[] commandDetails = command.split(" ", 2);
        if (commandDetails.length != 1) {
            throw new DukeException("Exit command should not include extra parameters!");
        }
        ArrayList<Task> taskList = tm.getTaskList();
        try {
            storage.save(taskList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ui.showGoodbye();
    }

    /**
     * Overrides the abstract class to send exit signal to Duke.
     *
     * @return Exit signal so Duke terminates after command is processed.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
