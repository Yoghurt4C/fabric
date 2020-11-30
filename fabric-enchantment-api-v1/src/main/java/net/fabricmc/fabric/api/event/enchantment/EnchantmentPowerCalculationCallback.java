package net.fabricmc.fabric.api.event.enchantment;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public interface EnchantmentPowerCalculationCallback {
	Event<EnchantmentPowerCalculationCallback> EVENT = EventFactory.createArrayBacked(EnchantmentPowerCalculationCallback.class,
			(listeners) -> (stack, world, tablePos, modifierPositions) -> {
				float f = 0;
				for (EnchantmentPowerCalculationCallback event : listeners) {
					float result = event.getEnchantmentPower(stack, world, tablePos, modifierPositions);

					if (result != 0)	{
						f += result;
					}
				}
				return f;
			}
	);

	float getEnchantmentPower(ItemStack stack, World world, BlockPos tablePos, List<BlockPos> modifierPositions);
}
