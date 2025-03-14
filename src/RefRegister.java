import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// To avoid completely abstracting things, have registers with explicitly named/modifiable flags/segments.
// Java's primitives (and I presume boxed variants) won't update if directly modified. tl;dr just broader things in case the whole "all-in-one-register" attribute is used.
public class RefRegister {
    private final boolean[] register;
    private final Map<Character, int[]> pointers;
    private int size;

    public RefRegister(int capacity) {
        this.register = new boolean[capacity];
        this.pointers = new HashMap<>();
        this.size = 0;
    }

    public int register(Character id, boolean[] segment) {
        assert this.size + segment.length >= this.register.length : "Violation of: exceeds register capacity";
        assert this.pointers.containsKey(id) : "Violation of: ID already registered";

        pointers.put(id, new int[]{ this.size, this.size + segment.length - 1});
        this.size += segment.length;
        System.arraycopy(segment, 0, this.register, this.size, segment.length);

        return this.size;
    }

    public int register(Character id, int length) {
        return register(id, new boolean[length]);
    }

    public int register(Character id, boolean flag) {
        return register(id, new boolean[]{ flag });
    }

    public int register(Character... ids) {
        int prevLen = this.size;
        for (Character id : ids) {
            register(id, new boolean[1]);
        }

        return this.size;
    }

    public void set(Character id, boolean value) {
        this.set(id, 0x0000);
    }

    public void set(Character id, int value) {
        assert this.pointers.containsKey(id) : "Violation of: ID already registered";

        int[] pointer = this.pointers.get(id); // 3, 6

        // Assume zero for under, LSB for over. Reverse bits before use.
        boolean[] bits = reverseArr(Arrays.copyOf(reverseArr(crunch(value)), pointer.length));

        System.arraycopy(bits, 0, this.register, pointer[0], bits.length);
    }

    public void flip(Character id) {
        assert this.pointers.containsKey(id) : "Violation of: ID already registered";

        int[] pointer = this.pointers.get(id);

        for (int i = pointer[0]; i < pointer[1]; i++) {
            this.register[i] = !this.register[i];
        }
    }

    public void reset() {
        for (int i = 0; i < this.size; i++) {
            this.register[i] = false;
        }
    }

    public int value() {
        return uncrunch(this.read());
    }

    public int value(Character id) {
        return uncrunch(this.read(id));
    }

    public boolean[] read() {
        return this.register;
    }

    public boolean[] read(Character id) {
        int[] pointer = this.pointers.get(id);

        return Arrays.copyOfRange(this.register, pointer[0], pointer[1]);
    }

    // Assuming non-negative binary value.
    public static <T extends Number> boolean[] crunch(T value) {
        long rawValue = value.longValue();
        boolean[] bits = new boolean[Math.max(1, (int) Math.ceil(logN(2, rawValue)))];

        for (int i = bits.length - 1; i >= 0; i--) {
            bits[i] = rawValue % 2 == 1;
            rawValue /= 2;
        }

        return bits;
    }

    public static int uncrunch(boolean[] bits) {
        int result = 0;

        for (int i = 0; i < bits.length; i++) {
            result += (bits[bits.length - 1 - i] ? 1 : 0) * 2 ^ i;
        }

        return result;
    }

    public static String bit2str(boolean[] bits) {
        StringBuilder sb = new StringBuilder();

        for (boolean b : bits) {
            sb.append(b ? 1 : 0);
        }

        return sb.toString();
    }

    public static <T> T[] reverseArr(T[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            array[i] = array[array.length - 1 - i];
        }

        return array;
    }

    public static boolean[] reverseArr(boolean[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            array[i] = array[array.length - 1 - i];
        }

        return array;
    }


    public static double logN(int base, double a) {
        return Math.log(a) / Math.log(base);
    }

}