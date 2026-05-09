package com.kylinhome.minecraft.profminer;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

/**
 * 模组物品注册 - 所有平台共享 (1.20.1)
 */
public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ProfMinerMod.MOD_ID, Registries.ITEM);

    // ========== 方块物品 ==========
    public static final RegistrySupplier<Item> RUBY_ORE_ITEM = ITEMS.register("ruby_ore",
        () -> new BlockItem(ModBlocks.RUBY_ORE.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> DEEPSLATE_RUBY_ORE_ITEM = ITEMS.register("deepslate_ruby_ore",
        () -> new BlockItem(ModBlocks.DEEPSLATE_RUBY_ORE.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> OPAL_ORE_ITEM = ITEMS.register("opal_ore",
        () -> new BlockItem(ModBlocks.OPAL_ORE.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> OPAL_BLOCK_ITEM = ITEMS.register("opal_block",
        () -> new BlockItem(ModBlocks.OPAL_BLOCK.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> SMOOTH_OPAL_BLOCK_ITEM = ITEMS.register("smooth_opal_block",
        () -> new BlockItem(ModBlocks.SMOOTH_OPAL_BLOCK.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> OPAL_BRICKS_ITEM = ITEMS.register("opal_bricks",
        () -> new BlockItem(ModBlocks.OPAL_BRICKS.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> OPAL_STAIRS_ITEM = ITEMS.register("opal_stairs",
        () -> new BlockItem(ModBlocks.OPAL_STAIRS.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> OPAL_SLAB_ITEM = ITEMS.register("opal_slab",
        () -> new BlockItem(ModBlocks.OPAL_SLAB.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> OPAL_PRESSURE_PLATE_ITEM = ITEMS.register("opal_pressure_plate",
        () -> new BlockItem(ModBlocks.OPAL_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> OPAL_WALL_ITEM = ITEMS.register("opal_wall",
        () -> new BlockItem(ModBlocks.OPAL_WALL.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> TITANIUM_ORE_ITEM = ITEMS.register("titanium_ore",
        () -> new BlockItem(ModBlocks.TITANIUM_ORE.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> DEEPSLATE_TITANIUM_ORE_ITEM = ITEMS.register("deepslate_titanium_ore",
        () -> new BlockItem(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> TITANIUM_ALLOY_BLOCK_ITEM = ITEMS.register("titanium_alloy_block",
        () -> new BlockItem(ModBlocks.TITANIUM_ALLOY_BLOCK.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> NETHER_TITANIUM_ALLOY_BLOCK_ITEM = ITEMS.register("nether_titanium_alloy_block",
        () -> new BlockItem(ModBlocks.NETHER_TITANIUM_ALLOY_BLOCK.get(), new Item.Properties()));

    // ========== 普通物品 ==========
    public static final RegistrySupplier<Item> RUBY = ITEMS.register("ruby",
        () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> RUBY_HEART = ITEMS.register("ruby_heart",
        () -> new RubyHeartItem(new Item.Properties().stacksTo(16)));
    public static final RegistrySupplier<Item> OPAL = ITEMS.register("opal",
        () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> RAW_TITANIUM = ITEMS.register("raw_titanium",
        () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> TITANIUM = ITEMS.register("titanium",
        () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> ALLOY_SMITHING_TEMPLATE = ITEMS.register("alloy_smithing_template",
        () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> TITANIUM_ALLOY_INGOT = ITEMS.register("titanium_alloy_ingot",
        () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> NETHER_TITANIUM_ALLOY_INGOT = ITEMS.register("nether_titanium_alloy_ingot",
        () -> new Item(new Item.Properties()));

    // ========== 钛合金装备（耐久1800） ==========
    public static final RegistrySupplier<Item> TITANIUM_ALLOY_HELMET = ITEMS.register("titanium_alloy_helmet",
        () -> new ArmorItem(ModArmorMaterials.TITANIUM_ALLOY_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
            new Item.Properties().durability(1800)));
    public static final RegistrySupplier<Item> TITANIUM_ALLOY_CHESTPLATE = ITEMS.register("titanium_alloy_chestplate",
        () -> new ArmorItem(ModArmorMaterials.TITANIUM_ALLOY_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
            new Item.Properties().durability(1800)));
    public static final RegistrySupplier<Item> TITANIUM_ALLOY_LEGGINGS = ITEMS.register("titanium_alloy_leggings",
        () -> new ArmorItem(ModArmorMaterials.TITANIUM_ALLOY_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
            new Item.Properties().durability(1800)));
    public static final RegistrySupplier<Item> TITANIUM_ALLOY_BOOTS = ITEMS.register("titanium_alloy_boots",
        () -> new ArmorItem(ModArmorMaterials.TITANIUM_ALLOY_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
            new Item.Properties().durability(1800)));

    // ========== 下界钛合金装备（耐久3000，防火） ==========
    public static final RegistrySupplier<Item> NETHER_TITANIUM_ALLOY_HELMET = ITEMS.register("nether_titanium_alloy_helmet",
        () -> new ArmorItem(ModArmorMaterials.NETHER_TITANIUM_ALLOY_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
            new Item.Properties().durability(3000).fireResistant()));
    public static final RegistrySupplier<Item> NETHER_TITANIUM_ALLOY_CHESTPLATE = ITEMS.register("nether_titanium_alloy_chestplate",
        () -> new ArmorItem(ModArmorMaterials.NETHER_TITANIUM_ALLOY_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
            new Item.Properties().durability(3000).fireResistant()));
    public static final RegistrySupplier<Item> NETHER_TITANIUM_ALLOY_LEGGINGS = ITEMS.register("nether_titanium_alloy_leggings",
        () -> new ArmorItem(ModArmorMaterials.NETHER_TITANIUM_ALLOY_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
            new Item.Properties().durability(3000).fireResistant()));
    public static final RegistrySupplier<Item> NETHER_TITANIUM_ALLOY_BOOTS = ITEMS.register("nether_titanium_alloy_boots",
        () -> new ArmorItem(ModArmorMaterials.NETHER_TITANIUM_ALLOY_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
            new Item.Properties().durability(3000).fireResistant()));

    public static void init() {
        ITEMS.register();
    }
}
