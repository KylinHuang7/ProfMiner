package com.kylinhome.minecraft.profminer;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * 红宝石心 - 使用后增加2点生命值上限（1颗红心），最多增加到20颗红心（40点生命值）
 */
public class RubyHeartItem extends Item {

    // 每颗红宝石心增加的生命值上限
    private static final double HEALTH_INCREASE = 2.0;
    // 最大生命值上限（20颗红心 = 40点）
    private static final double MAX_HEALTH_LIMIT = 40.0;
    // 属性修改器的基础ID
    private static final ResourceLocation RUBY_HEART_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(ProfMiner.MODID, "ruby_heart_extra_health");

    public RubyHeartItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            // 服务端逻辑
            AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
            if (healthAttribute != null) {
                // 计算当前的生命值上限
                double currentMaxHealth = healthAttribute.getValue();

                if (currentMaxHealth >= MAX_HEALTH_LIMIT) {
                    // 已经达到上限，提示玩家
                    player.displayClientMessage(
                            Component.translatable("item.profminer.ruby_heart.max_reached"), true);
                    return InteractionResultHolder.fail(itemStack);
                }

                // 获取当前已有的红宝石心修改器
                AttributeModifier existingModifier = healthAttribute.getModifier(RUBY_HEART_MODIFIER_ID);
                double currentBonus = 0;
                if (existingModifier != null) {
                    currentBonus = existingModifier.amount();
                    // 移除旧的修改器，准备添加新的
                    healthAttribute.removeModifier(RUBY_HEART_MODIFIER_ID);
                }

                // 计算新的加成值
                double newBonus = currentBonus + HEALTH_INCREASE;
                // 确保不超过上限（基础20 + 红宝石心加成 不超过40）
                double baseHealth = 20.0;
                if (baseHealth + newBonus > MAX_HEALTH_LIMIT) {
                    newBonus = MAX_HEALTH_LIMIT - baseHealth;
                }

                if (newBonus <= 0) {
                    player.displayClientMessage(
                            Component.translatable("item.profminer.ruby_heart.max_reached"), true);
                    return InteractionResultHolder.fail(itemStack);
                }

                // 添加新的修改器
                AttributeModifier newModifier = new AttributeModifier(
                        RUBY_HEART_MODIFIER_ID,
                        newBonus,
                        AttributeModifier.Operation.ADD_VALUE
                );
                healthAttribute.addPermanentModifier(newModifier);

                // 消耗物品（非创造模式）
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }

                // 恢复新增的生命值
                player.heal((float) HEALTH_INCREASE);

                // 发送成功提示
                int currentHearts = (int) (healthAttribute.getValue() / 2);
                player.displayClientMessage(
                        Component.translatable("item.profminer.ruby_heart.success", currentHearts), true);
            }
        }

        // 播放使用音效
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS,
                1.0F, 1.0F);

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
