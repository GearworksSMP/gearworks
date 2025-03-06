package com.gearworkssmp.gearworks.blocks;

import com.gearworkssmp.gearworks.blocks.entities.SwoltersLargeDumbbellBlockEntity;
import com.gearworkssmp.gearworks.blocks.partials.DumbbellPart;

import com.gearworkssmp.gearworks.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Collections;
import java.util.List;

public class SwoltersLargeDumbbellBlock extends BlockWithEntity {
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	public static final EnumProperty<DumbbellPart> PART = EnumProperty.of("part", DumbbellPart.class);

	public SwoltersLargeDumbbellBlock(Settings settings) {
		super(settings);
		// Set default state: facing north, LEFT part
		this.setDefaultState(this.stateManager.getDefaultState()
				.with(FACING, Direction.NORTH)
				.with(PART, DumbbellPart.LEFT));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, PART);
	}

	// Called when the player is placing the block.
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		Direction facing = ctx.getHorizontalPlayerFacing();
		return this.getDefaultState().with(FACING, facing).with(PART, DumbbellPart.LEFT);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		Direction facing = state.get(FACING);
		BlockPos otherPos = pos.offset(facing.rotateYClockwise());
		if (!world.getBlockState(otherPos).isAir()) {
			return false;
		}
		return super.canPlaceAt(state, world, otherPos);
	}

	// Once the LEFT half is placed, automatically place the RIGHT half.
	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		Direction facing = state.get(FACING);
		// Place RIGHT half to the right of the player’s facing
		BlockPos otherPos = pos.offset(facing.rotateYClockwise());
		if (world.getBlockState(otherPos).isAir()) {
			world.setBlockState(otherPos, state.with(PART, DumbbellPart.RIGHT), 3);
		}
		super.onPlaced(world, pos, state, placer, itemStack);
	}

	// When one half is broken, break the other half.
	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		Direction facing = state.get(FACING);
		BlockPos otherPos = state.get(PART) == DumbbellPart.LEFT
				? pos.offset(facing.rotateYClockwise())
				: pos.offset(facing.rotateYCounterclockwise());
		if (world.getBlockState(otherPos).getBlock() == this) {
			world.breakBlock(otherPos, false);
		}
		super.onBreak(world, pos, state, player);
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
		if (state.get(PART) == DumbbellPart.LEFT) {
			return Collections.singletonList(new ItemStack(ModItems.SWOLTERS_LARGE_DUMBBELL));
		}
		return Collections.emptyList();
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new SwoltersLargeDumbbellBlockEntity(pos, state);
	}


}
