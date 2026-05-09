package com.kylinhome.minecraft.profminer;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * 模组护甲材料注册 - 所有平台共享
 */
public class ModArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
        DeferredRegister.create(ProfMinerMod.MOD_ID, Registries.ARMOR_MATERIAL);

    // 钛合金护甲材料：每件比钻石低1点（头盔2, 胸甲7, 裤腿5, 靴子2），耐久1800
    public static final Holder<ArmorMaterial> TITANIUM_ALLOY_ARMOR_MATERIAL = ARMOR_MATERIALS.register("titanium_alloy",
        () -> new ArmorMaterial(
            new EnumMap<>(Map.of(
                ArmorItem.Type.HELMET, 2,
                ArmorItem.Type.CHESTPLATE, 7,
                ArmorItem.Type.LEGGINGS, 5,
                ArmorItem.Type.BOOTS, 2
            )),
            15, // 附魔值
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.of(ModItems.TITANIUM_ALLOY_INGOT.get()),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(ProfMinerMod.MOD_ID, "titanium_alloy"))),
            1.0f, // 韧性
            0.0f  // 击退抗性
        ));

    // 下界钛合金护甲材料：头盔4, 胸甲8, 裤腿6, 靴子4，耐久3000
    public static final Holder<ArmorMaterial> NETHER_TITANIUM_ALLOY_ARMOR_MATERIAL = ARMOR_MATERIALS.register("nether_titanium_alloy",
        () -> new ArmorMaterial(
            new EnumMap<>(Map.of(
                ArmorItem.Type.HELMET, 4,
                ArmorItem.Type.CHESTPLATE, 8,
                ArmorItem.Type.LEGGINGS, 6,
                ArmorItem.Type.BOOTS, 4
            )),
            20, // 附魔值
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            () -> Ingredient.of(ModItems.NETHER_TITANIUM_ALLOY_INGOT.get()),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(ProfMinerMod.MOD_ID, "nether_titanium_alloy"))),
            3.0f, // 韧性
            1.0f  // 击退抗性
        ));

    public static void init() {
        ARMOR_MATERIALS.register();
    }
}
