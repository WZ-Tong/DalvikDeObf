package xyz.wztong.emulate;

import org.cf.smalivm.SideEffect;
import org.cf.smalivm.emulate.MethodStateMethod;
import org.cf.smalivm.emulate.UnknownValuesMethod;

public abstract class EmulateMethodStateMethod extends MethodStateMethod implements UnknownValuesMethod {
    @Override
    public SideEffect.Level getSideEffectLevel() {
        return SideEffect.Level.STRONG;
    }
}
