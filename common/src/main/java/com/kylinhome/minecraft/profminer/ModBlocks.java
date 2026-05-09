package com.kylinhome.minecraft.profminer;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

/**
 * 模组方块注册 - 所有平台共享
 */
public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ProfMinerMod.MOD_ID, Registries.BLOCK);

    // ========== 红宝石矿石 ==========
    public static final RegistrySupplier<Block> RUBY_ORE = BLOCKS.register("ruby_ore",
        () -> new DropExperienceBlock(UniformInt.of(3, 7),
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .strength(3.0f, 3.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE)));

    public static final RegistrySupplier<Block> DEEPSLATE_RUBY_ORE = BLOCKS.register("deepslate_ruby_ore",
        () -> new DropExperienceBlock(UniformInt.of(3, 7),
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.DEEPSLATE)
                .strength(4.5f, 3.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.DEEPSLATE)));

    // ========== 蛋白石系列 ==========
    public static final RegistrySupplier<Block> OPAL_ORE = BLOCKS.register("opal_ore",
        () -> new Block(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .strength(3.0f, 3.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE)));

    public static final RegistrySupplier<Block> OPAL_BLOCK = BLOCKS.register("opal_block",
        () -> new Block(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.QUARTZ)
                .strength(1.5f, 6.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE)));

    public static final RegistrySupplier<Block> SMOOTH_OPAL_BLOCK = BLOCKS.register("smooth_opal_block",
        () -> new Block(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.QUARTZ)
                .strength(2.0f, 6.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE)));

    public static final RegistrySupplier<Block> OPAL_BRICKS = BLOCKS.register("opal_bricks",
        () -> new Block(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.QUARTZ)
                .strength(2.0f, 6.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE)));

    public static final RegistrySupplier<Block> OPAL_STAIRS = BLOCKS.register("opal_stairs",
        () -> new StairBlock(OPAL_BLOCK.get().defaultBlockState(),
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.QUARTZ)
                .strength(1.5f, 6.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE)));

    public static final RegistrySupplier<Block> OPAL_SLAB = BLOCKS.register("opal_slab",
        () -> new SlabBlock(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.QUARTZ)
                .strength(1.5f, 6.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE)));

    public static final RegistrySupplier<Block> OPAL_PRESSURE_PLATE = BLOCKS.register("opal_pressure_plate",
        () -> new PressurePlateBlock(BlockSetType.STONE,
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.QUARTZ)
                .strength(0.5f)
                .requiresCorrectToolForDrops()
                .noCollission()
                .sound(SoundType.STONE)));

    public static final RegistrySupplier<Block> OPAL_WALL = BLOCKS.register("opal_wall",
        () -> new WallBlock(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.QUARTZ)
                .strength(1.5f, 6.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE)));

    // ========== 钛矿系列 ==========
    public static final RegistrySupplier<Block> TITANIUM_ORE = BLOCKS.register("titanium_ore",
        () -> new Block(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .strength(3.0f, 3.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE)));

    public static final RegistrySupplier<Block> DEEPSLATE_TITANIUM_ORE = BLOCKS.register("deepslate_titanium_ore",
        () -> new Block(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.DEEPSLATE)
                .strength(4.5f, 3.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.DEEPSLATE)));

    public static final RegistrySupplier<Block> TITANIUM_ALLOY_BLOCK = BLOCKS.register("titanium_alloy_block",
        () -> new Block(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_GRAY)
                .strength(50.0f, 1200.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.NETHERITE_BLOCK)));

    public static final RegistrySupplier<Block> NETHER_TITANIUM_ALLOY_BLOCK = BLOCKS.register("nether_titanium_alloy_block",
        () -> new Block(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .strength(50.0f, 1200.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.NETHERITE_BLOCK)));

    public static void init() {
        BLOCKS.register();
    }
}
