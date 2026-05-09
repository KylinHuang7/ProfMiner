package com.kylinhome.minecraft.profminer.forge;

import com.kylinhome.minecraft.profminer.ProfMinerMod;
import net.minecraftforge.fml.common.Mod;

/**
 * Forge 平台入口
 */
@Mod(ProfMinerMod.MOD_ID)
public class ProfMinerForge {

    public ProfMinerForge() {
        // 调用通用初始化
        ProfMinerMod.init();
    }
}
