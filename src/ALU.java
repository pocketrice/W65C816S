public class ALU extends SynchronousCircuit {
    public ALU(int delay, int id) {
        super(delay, id);

        this.input = new RefRegister(36); // 16, 16, 1, 3
        this.input.register('A', 16);
        this.input.register('B', 16);
        this.input.register('C', 1);
        this.input.register('f', 3);

        this.output = new RefRegister(20); // 16, 1, 1, 1, 1
        this.output.register('Y', 16);
        this.output.register('C', 's', 'z', 'o');
    }

    @Override
    void setInput(Character id, int value) {
        this.input.set(id, value);
    }

    @Override
    int readOutput(Character id) {
        return this.output.value(id);
    }

    @Override
    void execute() {
        int fsel = this.input.value('f');

        switch (fsel) {
            case 0 -> { // NOT(A)

            }

            case 1 -> { // AND(A,B)

            }

            case 2 -> { // OR(A,B)

            }

            case 3 -> { // XOR(A,B)

            }

            case 4 -> { // A + B + Cin

            }

            case 5 -> { // A - B - Cin

            }

            case 6 -> { // B - A - Cin

            }

            default -> {}
        }
    }
}
