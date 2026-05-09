package com.kylinhome.minecraft.profminer;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * 模组护甲材料定义 - 1.20.1 版本
 * 在 1.20.1 中 ArmorMaterial 是接口，需要实现
 */
public class ModArmorMaterials {

    // 钛合金护甲材料
    public static final ArmorMaterial TITANIUM_ALLOY_ARMOR_MATERIAL = new ArmorMaterial() {
        private final int[] durabilityForSlot = {1800, 1800, 1800, 1800};
        private final int[] defenseForSlot = {2, 7, 5, 2}; // 靴子, 裤腿, 胸甲, 头盔

        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            return durabilityForSlot[type.ordinal()];
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return defenseForSlot[type.ordinal()];
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_IRON;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(ModItems.TITANIUM_ALLOY_INGOT.get());
        }

        @Override
        public String getName() {
            return ProfMinerMod.MOD_ID + ":titanium_alloy";
        }

        @Override
        public float getToughness() {
            return 1.0f;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.0f;
        }
    };

    // 下界钛合金护甲材料
    public static final ArmorMaterial NETHER_TITANIUM_ALLOY_ARMOR_MATERIAL = new ArmorMaterial() {
        private final int[] durabilityForSlot = {3000, 3000, 3000, 3000};
        private final int[] defenseForSlot = {4, 8, 6, 4}; // 靴子, 裤腿, 胸甲, 头盔

        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            return durabilityForSlot[type.ordinal()];
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return defenseForSlot[type.ordinal()];
        }

        @Override
        public int getEnchantmentValue() {
            return 20;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_NETHERITE;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(ModItems.NETHER_TITANIUM_ALLOY_INGOT.get());
        }

        @Override
        public String getName() {
            return ProfMinerMod.MOD_ID + ":nether_titanium_alloy";
        }

        @Override
        public float getToughness() {
            return 3.0f;
        }

        @Override
        public float getKnockbackResistance() {
            return 1.0f;
        }
    };

    public static void init() {
        // 1.20.1 中护甲材料不需要注册到注册表，直接作为常量使用
    }
}
