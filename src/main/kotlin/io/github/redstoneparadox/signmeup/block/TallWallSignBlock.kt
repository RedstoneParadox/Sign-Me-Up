package io.github.redstoneparadox.signmeup.block

import io.github.redstoneparadox.signmeup.block.enums.TallSignShape
import net.minecraft.block.*
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.SignType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.WorldView

class TallWallSignBlock(settings: Settings, type: SignType) : AbstractSignBlock(settings, type) {
    override fun getTranslationKey(): String {
        return asItem().translationKey
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
        val shape = state.get(SHAPE)
        return if (shape == TallSignShape.TOP) TOP_SHAPE else BOTTOM_SHAPE
    }

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        val bool = world.getBlockState(pos.offset((state.get(WallSignBlock.FACING) as Direction).opposite)).material.isSolid
        val shape = state.get(SHAPE)

        return bool && world
            .getBlockState(
                pos
                    .offset((state.get(WallSignBlock.FACING) as Direction).opposite)
                    .offset(if (shape == TallSignShape.TOP) Direction.UP else Direction.DOWN)
            )
            .material.isSolid
    }

    override fun getPlacementState(ctx: ItemPlacementContext?): BlockState? {
        return super.getPlacementState(ctx)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(SHAPE, FACING)
    }

    companion object {
        val TOP_SHAPE: VoxelShape = createCuboidShape(4.0, 0.0, 4.0, 12.0, 16.0, 12.0)
        val BOTTOM_SHAPE: VoxelShape = createCuboidShape(4.0, 0.0, 4.0, 12.0, 16.0, 12.0)
        val SHAPE: EnumProperty<TallSignShape> = EnumProperty.of("shape", TallSignShape::class.java)
        val FACING: DirectionProperty = HorizontalFacingBlock.FACING
    }
}