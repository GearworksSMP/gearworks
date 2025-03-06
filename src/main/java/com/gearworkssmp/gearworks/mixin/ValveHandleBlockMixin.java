package com.gearworkssmp.gearworks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.crank.ValveHandleBlock;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ValveHandleBlock.class)
public abstract class ValveHandleBlockMixin {

    /**
     * @author uberswe
     * @reason need to make this interactable in adventure mode for minigames
     */
    @Overwrite
    public static ActionResult onBlockActivated(PlayerEntity player, World world, Hand hand, BlockHitResult hit) {
        BlockPos pos = hit.getBlockPos();
        BlockState blockState = world.getBlockState(pos);

        if (!(blockState.getBlock() instanceof ValveHandleBlock vhb)) {
			return ActionResult.PASS;
		}

        if (AllItems.WRENCH.isIn(player.getStackInHand(hand)) && player.isSneaking()) {
			return ActionResult.PASS;
		}

        if (vhb.clicked(world, pos, blockState, player, hand)) {
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
