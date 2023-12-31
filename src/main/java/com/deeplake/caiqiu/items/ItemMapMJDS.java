package com.deeplake.caiqiu.items;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.util.AdvancementUtil;
import com.deeplake.caiqiu.util.CommonFunctions;
import com.deeplake.caiqiu.util.IDLNBTDef;
import com.deeplake.caiqiu.util.IDLNBTUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.deeplake.caiqiu.util.CommonDef.NEWLINE;
import static com.deeplake.caiqiu.util.CommonDef.TICK_PER_SECOND;
import static com.deeplake.caiqiu.util.IDLNBTDef.LAMP_MARK;
import static com.deeplake.caiqiu.util.IDLNBTDef.ORI_POS;

@Mod.EventBusSubscriber(modid = IdlFramework.MOD_ID)
public class ItemMapMJDS extends BaseItemIDF implements INeedLogNBT{

    public static final int BLANK = 0;
    public static final int PASS_ = 1;
    public static final int BOSS_ = 2;

    public static final int[][] CASTLE_MAP =
            {
                    {0,0,0,0,-4,1,1, 0,0,0,0},
                    {0,0,0,1, 1,1,1, 1,0,0,0},
                    {0,0,0,1, 1,0,1, 1,0,0,0},

                   {-2,0,1,0, 1,1,1,0,-3,0,1},
                    {1,1,1,1, 1,1,1, 1,1,1,1},
                    {0,1,0,1, 1,1,1, 1,0,1,0},

                    {1,1,1,1,1,-1,1, 1,1,1,1},
                    {1,1,1,1, 0,0,0,-5,1,1,1},
                    {1,1,0,0, 0,0,0, 0,0,1,1},
            };

    public ItemMapMJDS(Properties p_i48487_1_) {
        super(p_i48487_1_);

        //CommonFunctions.addToEventBus(this);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        if (world.isClientSide)
        {
            return;
        }

        if (entity instanceof ServerPlayerEntity)
        {
            if (IDLNBTUtil.GetInt(stack, LAMP_MARK) == 0)
            {
                if (AdvancementUtil.hasAdvancement((ServerPlayerEntity) entity, AdvancementUtil.ACHV_LAMP)){
                    IDLNBTUtil.SetInt(stack, LAMP_MARK, 1);
                }
            }
        }

        super.inventoryTick(stack, world, entity, p_77663_4_, p_77663_5_);
    }

    //return (ChunkZ, ChunkY, Floor in Z (as 1,2,3,4))
    public static BlockPos getShrinkPosFromRealPos(BlockPos pos)
    {
        return new BlockPos(pos.getX() >> 4, - pos.getY() >> 4, (pos.getY() % 16) >> 2 + 1);
    }

    //the argument wont be converted
    public static void setOriginToStack(ItemStack stack, BlockPos pos)
    {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.put(ORI_POS, NBTUtil.writeBlockPos(pos));
        stack.setTag(nbt);
    }

    public static BlockPos readOriginFromStack(ItemStack stack)
    {
//        if (stack.getTag() == null)
//        {
//            CompoundNBT nbt = new CompoundNBT();
//            nbt.put(ORI_POS, NBTUtil.writeBlockPos(BlockPos.ZERO));
//            stack.setTag(new CompoundNBT());
//        }
        return NBTUtil.readBlockPos(stack.getOrCreateTag().getCompound(ORI_POS));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void addSpecialDesc(ItemTooltipEvent event)
    {
        PlayerEntity playerEntity = event.getPlayer();

        if (playerEntity != null && event.getItemStack().getItem() instanceof ItemMapMJDS)
        {
            StringBuilder stringBuilder = new StringBuilder();

            ItemStack stack = event.getItemStack();
            BlockPos pinPoint = getShrinkPosFromRealPos(playerEntity.blockPosition());
            BlockPos origin = readOriginFromStack(stack);

            int playerAtX = pinPoint.getX()-origin.getX() - 1;
            int playerAtY = pinPoint.getY()-origin.getY() - 1;

            event.getToolTip().add(new StringTextComponent(String.format("Player %d,%d",playerAtX,playerAtY)));

            //Client does not know nbt, let alone ego
//            String playerStr = EgoUtil.getEgo(playerEntity).equals(MJDSDefine.EnumEgo.APHRODITE) ?
//                    CommonFunctions.GetStringLocalTranslated(IDLNBTDef.MAP_MARK_PLAYER_APHRODITE) :
//                    CommonFunctions.GetStringLocalTranslated(IDLNBTDef.MAP_MARK_PLAYER_POPOLON);

            int tickCount = playerEntity.tickCount % TICK_PER_SECOND;

            String playerStr = tickCount >= 10 ?
            CommonFunctions.GetStringLocalTranslated(IDLNBTDef.MAP_MARK_PLAYER_APHRODITE) :
            CommonFunctions.GetStringLocalTranslated(IDLNBTDef.MAP_MARK_PLAYER_POPOLON);

            String pass = CommonFunctions.GetStringLocalTranslated(IDLNBTDef.MAP_MARK_PASS);
            String blank = CommonFunctions.GetStringLocalTranslated(IDLNBTDef.MAP_MARK_BLANK);
            String boss = CommonFunctions.GetStringLocalTranslated(IDLNBTDef.MAP_MARK_BOSS);

//            IdlFramework.Log("Translation:%s->%s, playerText = %s",
//                    IDLNBTDef.MAP_MARK_PLAYER_APHRODITE,
//                    CommonFunctions.GetStringLocalTranslated(IDLNBTDef.MAP_MARK_PLAYER_APHRODITE), playerStr);

            boolean hasLamp = IDLNBTUtil.GetInt(stack, LAMP_MARK) > 0;

            int curY = 0;
            for (int[] row: CASTLE_MAP) {
                int curX = 0;
                for (int grid: row) {
                    if (playerAtY == curY && playerAtX == curX)
                    {
                        stringBuilder.append(playerStr);
                    }
                    else {
                        switch (grid)
                        {
                            case PASS_:
                                stringBuilder.append(pass);
                                break;
                            case BLANK:
                                stringBuilder.append(blank);
                                break;
                            default:
                               if (hasLamp)
                               {
                                   stringBuilder.append(boss);
                               }else {
                                   stringBuilder.append(pass);
                               }
                               break;
                        }
                    }
                    curX++;
                }
                stringBuilder.append(NEWLINE);
                curY++;
            }

            event.getToolTip().add(new StringTextComponent(stringBuilder.toString()));
        }
    }

}
