package com.kylinhome.minecraft.profminer;

/**
 * ProfMiner 模组通用入口
 * 此类包含所有平台共享的初始化逻辑
 */
public class ProfMinerMod {

    public static final String MOD_ID = "profminer";

    /**
     * 通用初始化 - 在所有平台上调用
     */
    public static void init() {
        ModBlocks.init();
        ModItems.init();
        ModArmorMaterials.init();
        ModCreativeTabs.init();
    }
}
