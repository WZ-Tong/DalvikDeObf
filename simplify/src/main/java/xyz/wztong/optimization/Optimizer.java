package xyz.wztong.optimization;

import org.cf.simplify.ExecutionGraphManipulator;
import org.cf.smalivm.context.ExecutionGraph;
import xyz.wztong.optimization.impl.ConstantPropagation;
import xyz.wztong.optimization.impl.DeadFunctionResult;
import xyz.wztong.optimization.impl.NopInstruction;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public class Optimizer {

    private static final List<Optimization> optimizations = new LinkedList<>();
    private static final int DEFAULT_PASS = 5;

    static {
        optimizations.add(new ConstantPropagation());
        optimizations.add(new NopInstruction());
        optimizations.add(new DeadFunctionResult());
    }

    public static int optimize(ExecutionGraph graph) {
        return optimize(graph, DEFAULT_PASS);
    }

    // Return: Re-execute needed count
    public static int optimize(ExecutionGraph graph, int maxPass) {
        var vm = graph.getVM();
        var classManager = vm.getClassManager();
        var dexBuilder = classManager.getDexBuilder();
        var method = graph.getMethod();
        var manipulator = new ExecutionGraphManipulator(graph, method, vm, dexBuilder);
        int pass = 0;
        int totalChanges = 0;
        int changes;
        do {
            changes = 0;
            for (Optimization optimization : optimizations) {
                changes += optimization.perform(manipulator);
            }
            pass++;
            totalChanges += changes;
        } while (changes != 0 && pass < maxPass);
        System.out.println("Simplifying(" + totalChanges + "): " + method);
        if (totalChanges != 0) {
            vm.updateInstructionGraph(method);
        }
        // TODO: Unreflect
        return 0;
    }

}