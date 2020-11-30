package net.fabricmc.fabric.mixin.enchantment;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.event.enchantment.EnchantmentPowerCalculationCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(EnchantmentScreenHandler.class)
public abstract class EnchantmentScreenHandlerMixin {

	@ModifyVariable(method = "method_17411", at = @At(value = "STORE", ordinal = 0), ordinal = 0, remap = false)
	private int prependPylonChecks(int i, ItemStack stack, World world, BlockPos pos) {
		ImmutableList.Builder<BlockPos> validPositions = ImmutableList.builder();
		for (int z = -1; z <= 1; ++z) {
			for (int x = -1; x <= 1; ++x) {
				if ((z != 0 || x != 0) && world.isAir(pos.add(x, 0, z)) && world.isAir(pos.add(x, 1, z))) {
					for (int y = 0; y < 2; y++) {
						validPositions.add(pos.add(x * 2, y, z * 2));
						if (x != 0 && z != 0) {
							validPositions.add(pos.add(x * 2, y, z));
							validPositions.add(pos.add(x, y, z*2));
						}
					}
				}
			}
		}
		float f = EnchantmentPowerCalculationCallback.EVENT.invoker().getEnchantmentPower(stack, world, pos, validPositions.build());
		return i + (int) f;
	}
}
