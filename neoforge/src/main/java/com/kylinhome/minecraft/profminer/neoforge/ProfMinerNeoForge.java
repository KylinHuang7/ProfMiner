package com.kylinhome.minecraft.profminer.neoforge;

import com.kylinhome.minecraft.profminer.ProfMinerMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

/**
 * NeoForge 平台入口
 */
@Mod(ProfMinerMod.MOD_ID)
public class ProfMinerNeoForge {

    public ProfMinerNeoForge(IEventBus modEventBus) {
        // 调用通用初始化
        ProfMinerMod.init();
    }
}
