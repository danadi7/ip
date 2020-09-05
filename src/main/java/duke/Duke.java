package duke;

import duke.exceptions.DukeException;
import duke.logic.CommandParser;
import duke.logic.commands.Command;
import duke.model.TaskManager;
import duke.storage.Storage;
import duke.ui.Ui;

import java.io.IOException;

/**
 * The Duke program is a chatbot for managing tasks and deadlines.
 *
 * @author Daniel Adipranoto
 * @version 0.1
 */
public class Duke {
    private static final String FILE_PATH = "data/DukeDB.txt";
    private Storage storage;
    private TaskManager tm;
    private Ui ui;

    /**
     * Constructor for Duke class.
     * Initiates the User Interface and tries to load save file from disk.
     */
    public Duke() {
        ui = new Ui();
        storage = new Storage(FILE_PATH);
        try {
            tm = new TaskManager((storage.load()));
        } catch (Exception e) {
            ui.showLoadingError();
            tm = new TaskManager();
        }
    }

    /**
     * This is the method that processes user input and looks out for the exit signal.
     */
    public void run() {
        ui.showWelcome();;
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = CommandParser.parse(fullCommand);
                c.execute(tm, ui, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e);
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * This is the main method that runs Duke.
     *
     * @param args Unused.
     * @throws IOException On input error.
     */
    public static void main(String[] args) throws IOException {
        new Duke().run();
    }
}
