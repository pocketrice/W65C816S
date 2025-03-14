import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Processor65816 {
    byte regDB, regPB; // 8-bit registers!
    //     │ 	 │
    //     └── DBR, data bank register
    //           └── PBR, program data bank
    //
    short regC, regD, regX, regY, regDP, regS, regPC; // 16-bit registers!
    //     │ 	 │     │     │      │      │       │
    //     └── accumulator   │      │      │       │
    //           └── direct register│      │       │
    //                 └── X index register│       │
    //                       └── Y index register  │
    //                              └── D, direct page register
    //                                     └── SP, stack pointer
    //                                             └── program counter

    //boolean flagN, flagV, flagM, flagX, flagD, flagI, flagZ, flagC, flagE, flagB; // Program status register flags!
    //        │ 	 │      │      │      │      │       │      │     │       │
    //        └── Negative  │      └── indeX register width     │     └── Emulation mode
    //               └── oVerflow         └── Decimal mode      └── Carry     └── Break
    //                      └── accumulator/Memory width └── Zero
    //                                           └── Interrupt disable
    RefRegister regPS;
    ALU alu;

    byte[] addressSpace; // 16MiB address space

    public Processor65816() {
        regPS = new RefRegister(10);
        regPS.register('N', 'V', 'M', 'X', 'D', 'I', 'Z', 'C', 'E', 'B');
        alu = new ALU(5, 1);

        addressSpace = new byte[0xFFFFFF];
    }

    // ==================================================

    public void adc(int dp, boolean X) {

    }


    // ==================================================


    public enum Opcode implements Derivable {
        ADC_I(0x69),
        ADC_A(0x6D),
        ADC_AL(0x6F),
        ADC_DP(0x65),
        ADC_DP_INT(0x72),
        ADC_DPL_INT(0x67),
        ADC_A_IND_X(0x7D),
        ADC_AL_IND_X(0x7F),
        ADC_A_IND_Y(0x79),
        ADC_DP_IND_X(0x75),
        ADC_DP_INT_X(0x61),
        ADC_DP_INTIND_Y(0x71),
        ADC_DPL_INTIND_Y(0x77),
        ADC_SR(0x63),
        ADC_SR_INTIND_Y(0x73);


        private final int hexId;

        Opcode(int hexId) {
            this.hexId = hexId;
        }
    }


    protected class Parser65816 {
        Queue<String> tokens;

        public Parser65816() {

        }
    }


}
