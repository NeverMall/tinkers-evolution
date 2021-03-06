package xyz.phanta.tconevo.coremod;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nullable;
import java.util.Map;

public class TconEvoCoreMod implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        String pkg = TconEvoCoreMod.class.getPackage().getName() + ".";
        return new String[] {
                pkg + "ClassTransformerItemStackBar",
                pkg + "ClassTransformerItemSensitiveModifiers",
                pkg + "ClassTransformerAstralAttunement",
                pkg + "ClassTransformerUniquePartTraits"
        };
    }

    @Nullable
    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        // NO-OP
    }

    @Nullable
    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
