package net.melon.slabs.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.melon.slabs.MelonSlabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;


@Mixin(CactusBlock.class)
public abstract class CactusMixin extends Block{

    public CactusMixin(Settings settings) {
        super(settings);
        // TODO Auto-generated constructor stub
    }

    @Shadow
    @Final
    public static IntProperty AGE;

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      BlockPos blockPos = pos.up();
      if (world.isAir(blockPos)) {
         int i;
         for(i = 1; world.getBlockState(pos.down(i)).isOf(this); ++i) {
         }

         if (i < 4) {
            int j = (Integer)state.get(AGE);
            if (j == 7) {
               world.setBlockState(blockPos, MelonSlabs.CACTUS_SLAB_BLOCK.getDefaultState());
               BlockState blockState = (BlockState)state.with(AGE, 0);
               world.setBlockState(pos, blockState, 4);
               blockState.neighborUpdate(world, blockPos, this, pos, false);
            } else {
               world.setBlockState(pos, (BlockState)state.with(AGE, j + 1), 4);
            }

         }
      }
    }

    static{
        AGE = Properties.AGE_15;
    }
}