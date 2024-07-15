package net.firtreeman.meatmaster.block.custom;

import net.firtreeman.meatmaster.block.entity.IndustrialOvenStationBlockEntity;
import net.firtreeman.meatmaster.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class IndustrialOvenStationBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    public static final DirectionProperty HORIZONTAL_FACING;
    public static final BooleanProperty LIT;

    public IndustrialOvenStationBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof IndustrialOvenStationBlockEntity) {
                ((IndustrialOvenStationBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) return InteractionResult.SUCCESS;

        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof IndustrialOvenStationBlockEntity) {
            NetworkHooks.openScreen((ServerPlayer) pPlayer, (IndustrialOvenStationBlockEntity) blockEntity, pPos);
            return InteractionResult.CONSUME;
        } else {
            throw new IllegalStateException("Industrial Oven Station ContainerProvider missing");
        }
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pState.getValue(LIT)) return;

        double xPos = pPos.getX() + 0.5;
        double yPos = pPos.getY();
        double zPos = pPos.getZ() + 0.5;
        if (pRandom.nextDouble() < 0.1) {
            pLevel.playLocalSound(xPos, yPos, zPos, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
        }

        Direction facing = pState.getValue(HORIZONTAL_FACING);
        Direction.Axis axis = facing.getAxis();
        double horizParticleOffset = pRandom.nextDouble() * 0.6 - 0.3;
        double xParticleOffset = axis == Direction.Axis.X ? (double)facing.getStepX() * 0.52 : horizParticleOffset;
        double yParticleOffset = pRandom.nextDouble() * 6.0 / 16.0;
        double zParticleOffset = axis == Direction.Axis.Z ? (double)facing.getStepZ() * 0.52 : horizParticleOffset;
        pLevel.addParticle(ParticleTypes.SMOKE, xPos + xParticleOffset, yPos + yParticleOffset, zPos + zParticleOffset, 0.0, 0.0, 0.0);
        pLevel.addParticle(ParticleTypes.FLAME, xPos + xParticleOffset, yPos + yParticleOffset, zPos + zParticleOffset, 0.0, 0.0, 0.0);

    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HORIZONTAL_FACING, LIT);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new IndustrialOvenStationBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) return null;


        return createTickerHelper(pBlockEntityType, ModBlockEntities.INDUSTRIAL_OVEN_SBE.get(), ((pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1)));
    }

    static {
        HORIZONTAL_FACING = HorizontalDirectionalBlock.FACING;
        LIT = BlockStateProperties.LIT;
    }
}
