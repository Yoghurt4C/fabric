package net.fabricmc.fabric.test.event.enchantment;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.enchantment.EnchantmentPowerCalculationCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.LiteralText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HopefullyThisDoesntWork implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("EnchantmentPowerEventTest");

	@Override
	public void onInitialize() {
		EnchantmentPowerCalculationCallback.EVENT.register(((stack, world, tablePos, modifierPositions) -> {
			float[] f = new float[]{0};
			if (stack.getItem() == Items.WOODEN_SWORD) f[0] += 30;
			if (tablePos.getY() > 100) f[0] += 15;
			modifierPositions.forEach(pos -> {
				float temp = 0;
				BlockState state = world.getBlockState(pos);
				if (state.getBlock() == Blocks.GLOWSTONE) temp = 0.2f;
				else if (state.getBlock().isIn(BlockTags.BEE_GROWABLES)) temp = 3;
				else if (state == Blocks.BIRCH_SLAB.getDefaultState().with(Properties.WATERLOGGED, true)) temp = 3.25f;
				else if (state.getBlock() == Blocks.CRYING_OBSIDIAN) temp -= 2;
				if (temp != 0) {
					LOGGER.info("Enchantment Power at \"" + pos.toString() + "\", from \"" + state.toString() + "\": " + temp);
					f[0] += temp;
				}
			});
			LOGGER.info("Total Enchantment Power (before bookshelves): " + (int) f[0]);
			return f[0];
		}));
	}
}
