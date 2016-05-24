import java.util.Stack;

public class CommandManager {

    private Stack<MovePieceCommand> undos = new Stack<MovePieceCommand>();
    private Stack<MovePieceCommand> redos = new Stack<MovePieceCommand>();

    public void executeCommand(MovePieceCommand c) {
        c.execute();
        undos.push(c);
        redos.clear();
    }

    public boolean isUndoAvailable() {
        return !undos.empty();
    }

    public void undo() {
        assert(!undos.empty());
        MovePieceCommand command = undos.pop();
        command.undo();
        redos.push(command);
    }

    public boolean isRedoAvailable() {
        return !redos.empty();
    }

    public void redo() {
        assert(!redos.empty());
        MovePieceCommand command = redos.pop();
        command.execute();
        undos.push(command);
    }
}
