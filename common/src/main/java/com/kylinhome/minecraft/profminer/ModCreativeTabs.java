package com.kylinhome.minecraft.profminer;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;

/**
 * 模组创造模式标签页注册 - 所有平台共享
 */
public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
        DeferredRegister.create(ProfMinerMod.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> PROFMINER_TAB = CREATIVE_MODE_TABS.register("profminer_tab",
        () -> CreativeTabRegistry.create(
            Component.translatable("itemGroup.profminer"),
            () -> Items.DIAMOND_PICKAXE.getDefaultInstance()
        ));

    public static void init() {
        CREATIVE_MODE_TABS.register();
    }
}
