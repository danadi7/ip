package duke.logic;

import duke.exceptions.DukeException;
import duke.logic.commands.AddCommand;
import duke.logic.commands.Command;
import duke.logic.commands.DeleteCommand;
import duke.logic.commands.DoneCommand;
import duke.logic.commands.ExitCommand;
import duke.logic.commands.FindCommand;
import duke.logic.commands.ListCommand;

public class CommandParser {

    public static Command parse(String command) throws DukeException {
        String[] extractedCommand = command.split(" ", 2);
        switch(extractedCommand[0]) {
        case("bye"):
            return new ExitCommand(command);
        case("list"):
            return new ListCommand(command);
        case("done"):
            return new DoneCommand(command);
        case("delete"):
            return new DeleteCommand(command);
        case("find"):
            return new FindCommand(command);
        case("deadline"):
        case("event"):
        case("todo"):
            return new AddCommand(command);
        default:
            throw new DukeException("Command not recognised!");
        }
    }
}
