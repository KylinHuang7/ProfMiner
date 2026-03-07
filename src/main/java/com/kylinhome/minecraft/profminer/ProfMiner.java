package com.kylinhome.minecraft.profminer;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.util.valueproviders.UniformInt;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ProfMiner.MODID)
public class ProfMiner {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "profminer";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "profminer" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "profminer" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "profminer" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(
      Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "profminer:example_block", combining the namespace and path
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block",
      BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    // Creates a new BlockItem with the id "profminer:example_block", combining the namespace and path
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block",
      EXAMPLE_BLOCK);

    // ========== 红宝石矿石 ==========
    // 红宝石矿石方块（石头变种）
    public static final DeferredBlock<Block> RUBY_ORE = BLOCKS.register("ruby_ore",
      () -> new DropExperienceBlock(UniformInt.of(3, 7),
        BlockBehaviour.Properties.of()
          .mapColor(MapColor.STONE)
          .strength(3.0f, 3.0f) // 硬度和爆炸抗性，与钻石矿相同
          .requiresCorrectToolForDrops()
          .sound(SoundType.STONE)));
    // 深板岩红宝石矿石方块
    public static final DeferredBlock<Block> DEEPSLATE_RUBY_ORE = BLOCKS.register("deepslate_ruby_ore",
      () -> new DropExperienceBlock(UniformInt.of(3, 7),
        BlockBehaviour.Properties.of()
          .mapColor(MapColor.DEEPSLATE)
          .strength(4.5f, 3.0f) // 深板岩变种更硬
          .requiresCorrectToolForDrops()
          .sound(SoundType.DEEPSLATE)));
    // 红宝石矿石方块物品
    public static final DeferredItem<BlockItem> RUBY_ORE_ITEM = ITEMS.registerSimpleBlockItem("ruby_ore", RUBY_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_RUBY_ORE_ITEM = ITEMS.registerSimpleBlockItem("deepslate_ruby_ore", DEEPSLATE_RUBY_ORE);
    // 红宝石物品
    public static final DeferredItem<Item> RUBY = ITEMS.registerSimpleItem("ruby", new Item.Properties());
    // 红宝石心物品 — 使用后增加生命值上限
    public static final DeferredItem<Item> RUBY_HEART = ITEMS.registerItem("ruby_heart",
      RubyHeartItem::new, new Item.Properties().stacksTo(16));

    // ========== 蛋白石系列 ==========
    // 蛋白石矿石方块（石头中生成，石镐可挖掘，不掉落经验）
    public static final DeferredBlock<Block> OPAL_ORE = BLOCKS.register("opal_ore",
      () -> new Block(
        BlockBehaviour.Properties.of()
          .mapColor(MapColor.STONE)
          .strength(3.0f, 3.0f)
          .requiresCorrectToolForDrops()
          .sound(SoundType.STONE)));
    // 蛋白石矿石方块物品
    public static final DeferredItem<BlockItem> OPAL_ORE_ITEM = ITEMS.registerSimpleBlockItem("opal_ore", OPAL_ORE);
    // 蛋白石物品
    public static final DeferredItem<Item> OPAL = ITEMS.registerSimpleItem("opal", new Item.Properties());
    // 蛋白石块
    public static final DeferredBlock<Block> OPAL_BLOCK = BLOCKS.registerSimpleBlock("opal_block",
      BlockBehaviour.Properties.of()
        .mapColor(MapColor.QUARTZ)
        .strength(1.5f, 6.0f)
        .requiresCorrectToolForDrops()
        .sound(SoundType.STONE));
    public static final DeferredItem<BlockItem> OPAL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("opal_block", OPAL_BLOCK);
    // 平滑蛋白石块（熔炉烧制获得）
    public static final DeferredBlock<Block> SMOOTH_OPAL_BLOCK = BLOCKS.registerSimpleBlock("smooth_opal_block",
      BlockBehaviour.Properties.of()
        .mapColor(MapColor.QUARTZ)
        .strength(2.0f, 6.0f)
        .requiresCorrectToolForDrops()
        .sound(SoundType.STONE));
    public static final DeferredItem<BlockItem> SMOOTH_OPAL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("smooth_opal_block", SMOOTH_OPAL_BLOCK);
    // 蛋白石砖
    public static final DeferredBlock<Block> OPAL_BRICKS = BLOCKS.registerSimpleBlock("opal_bricks",
      BlockBehaviour.Properties.of()
        .mapColor(MapColor.QUARTZ)
        .strength(2.0f, 6.0f)
        .requiresCorrectToolForDrops()
        .sound(SoundType.STONE));
    public static final DeferredItem<BlockItem> OPAL_BRICKS_ITEM = ITEMS.registerSimpleBlockItem("opal_bricks", OPAL_BRICKS);
    // 蛋白石楼梯
    public static final DeferredBlock<StairBlock> OPAL_STAIRS = BLOCKS.register("opal_stairs",
      () -> new StairBlock(OPAL_BLOCK.get().defaultBlockState(),
        BlockBehaviour.Properties.of()
          .mapColor(MapColor.QUARTZ)
          .strength(1.5f, 6.0f)
          .requiresCorrectToolForDrops()
          .sound(SoundType.STONE)));
    public static final DeferredItem<BlockItem> OPAL_STAIRS_ITEM = ITEMS.registerSimpleBlockItem("opal_stairs", OPAL_STAIRS);
    // 蛋白石台阶
    public static final DeferredBlock<SlabBlock> OPAL_SLAB = BLOCKS.register("opal_slab",
      () -> new SlabBlock(
        BlockBehaviour.Properties.of()
          .mapColor(MapColor.QUARTZ)
          .strength(1.5f, 6.0f)
          .requiresCorrectToolForDrops()
          .sound(SoundType.STONE)));
    public static final DeferredItem<BlockItem> OPAL_SLAB_ITEM = ITEMS.registerSimpleBlockItem("opal_slab", OPAL_SLAB);
    // 蛋白石压力板
    public static final DeferredBlock<PressurePlateBlock> OPAL_PRESSURE_PLATE = BLOCKS.register("opal_pressure_plate",
      () -> new PressurePlateBlock(BlockSetType.STONE,
        BlockBehaviour.Properties.of()
          .mapColor(MapColor.QUARTZ)
          .strength(0.5f)
          .requiresCorrectToolForDrops()
          .noCollission()
          .sound(SoundType.STONE)));
    public static final DeferredItem<BlockItem> OPAL_PRESSURE_PLATE_ITEM = ITEMS.registerSimpleBlockItem("opal_pressure_plate", OPAL_PRESSURE_PLATE);
    // 蛋白石围墙
    public static final DeferredBlock<WallBlock> OPAL_WALL = BLOCKS.register("opal_wall",
      () -> new WallBlock(
        BlockBehaviour.Properties.of()
          .mapColor(MapColor.QUARTZ)
          .strength(1.5f, 6.0f)
          .requiresCorrectToolForDrops()
          .sound(SoundType.STONE)));
    public static final DeferredItem<BlockItem> OPAL_WALL_ITEM = ITEMS.registerSimpleBlockItem("opal_wall", OPAL_WALL);

    // Creates a new food item with the id "profminer:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item",
      new Item.Properties().food(
        new FoodProperties.Builder().alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    // Creates a creative tab with the id "profminer:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register(
      "example_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.profminer"))
        .withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
        .displayItems((parameters, output) -> {
            output.accept(
              EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            output.accept(RUBY.get());
            output.accept(RUBY_HEART.get());
            output.accept(RUBY_ORE_ITEM.get());
            output.accept(DEEPSLATE_RUBY_ORE_ITEM.get());
            output.accept(OPAL.get());
            output.accept(OPAL_ORE_ITEM.get());
            output.accept(OPAL_BLOCK_ITEM.get());
            output.accept(SMOOTH_OPAL_BLOCK_ITEM.get());
            output.accept(OPAL_BRICKS_ITEM.get());
            output.accept(OPAL_STAIRS_ITEM.get());
            output.accept(OPAL_SLAB_ITEM.get());
            output.accept(OPAL_PRESSURE_PLATE_ITEM.get());
            output.accept(OPAL_WALL_ITEM.get());
        }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public ProfMiner(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ProfMiner) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(EXAMPLE_BLOCK_ITEM);
            event.accept(RUBY_ORE_ITEM);
            event.accept(DEEPSLATE_RUBY_ORE_ITEM);
            event.accept(OPAL_ORE_ITEM);
            event.accept(OPAL_BLOCK_ITEM);
            event.accept(SMOOTH_OPAL_BLOCK_ITEM);
            event.accept(OPAL_BRICKS_ITEM);
            event.accept(OPAL_STAIRS_ITEM);
            event.accept(OPAL_SLAB_ITEM);
            event.accept(OPAL_PRESSURE_PLATE_ITEM);
            event.accept(OPAL_WALL_ITEM);
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(RUBY);
            event.accept(RUBY_HEART);
            event.accept(OPAL);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
