package xyz.wztong;

import org.cf.smalivm.VirtualMachineFactory;
import org.cf.smalivm.exception.VirtualMachineException;
import org.jf.dexlib2.writer.io.FileDataStore;
import xyz.wztong.optimization.Optimizer;

import java.io.File;
import java.io.IOException;

public class Main {
    public static final String SMALI_PATH = "C:\\Users\\WZTong\\Downloads\\IdeaProjectOffline\\DalvikDeObf\\simplify\\src\\main\\resources\\wztong";
    public static final String OUTPUT_PATH = "C:\\Users\\WZTong\\Downloads\\IdeaProjectOffline\\DalvikDeObf\\simplify\\src\\main\\resources\\wztong\\out\\";
    public static final String OUTPUT_DEX_PATH = OUTPUT_PATH + "out.dex";

    public static void main(String[] args) throws IOException, VirtualMachineException {
        var vm = new VirtualMachineFactory().build(SMALI_PATH);
        Optimizer.optimize(vm.execute("LConstantPropgationTest;->invokeIntegerMethods()I"));
        Optimizer.optimize(vm.execute("LConstantPropgationTest;->getIntegerMethod()I"));
        vm.getClassManager().getDexBuilder().writeTo(new FileDataStore(new File(OUTPUT_DEX_PATH)));
        Utils.classManagerFactoryDisassemble(new File(OUTPUT_DEX_PATH), new File(OUTPUT_PATH));
    }

}
