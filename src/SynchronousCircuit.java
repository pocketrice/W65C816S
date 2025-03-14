

public abstract class SynchronousCircuit {
    protected int delay, id;
    protected RefRegister input, output;

    public SynchronousCircuit(int delay, int id) {
        this.delay = delay;
        this.id = id;
    }

    abstract void setInput(Character id, int value);
    abstract int readOutput(Character id);
    abstract void execute();
}
