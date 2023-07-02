package com.deeplake.caiqiu.items;

import com.deeplake.caiqiu.util.CommonFunctions;
import com.deeplake.caiqiu.util.IDLNBTUtil;
import com.deeplake.caiqiu.util.MessageDef;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import static com.deeplake.caiqiu.util.CommonDef.TICK_PER_SECOND;
import static com.deeplake.caiqiu.util.IDLNBTDef.ANCHOR_READY;

public class ItemTeleportCommand extends BaseItemIDF implements INeedLogNBT{
    public ItemTeleportCommand() {
    }

    public ItemTeleportCommand(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack stack = playerEntity.getItemInHand(hand);
        if (world.isClientSide)
        {
            return ActionResult.success(stack);
        }
        else {
            if (playerEntity.isCrouching())
            {
                //mark
                BlockPos pos = playerEntity.blockPosition();
                IDLNBTUtil.markPosToStack(stack, playerEntity.blockPosition());
                world.playSound((PlayerEntity)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.PLAYER_LEVELUP, SoundCategory.PLAYERS, 1F, 1f);
                CommonFunctions.SafeSendMsgToPlayer(
                        TextFormatting.BLUE,
                        playerEntity,
                        MessageDef.TELEPORT_ANCHOR_SET);
            }
            else {
                //teleport
                if (IDLNBTUtil.GetBoolean(stack, ANCHOR_READY, false))
                {
                    BlockPos pos = IDLNBTUtil.getMarkedPos(stack);
                    playerEntity.teleportTo(pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f);
                    playerEntity.level.levelEvent(playerEntity, 1013, pos, 0);
                    if (!playerEntity.abilities.instabuild) {
                        stack.shrink(1);
                    }
                    world.playSound((PlayerEntity)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1F, 1f);
                    playerEntity.awardStat(Stats.ITEM_USED.get(this));
                    playerEntity.swing(hand, true);

                    return ActionResult.consume(playerEntity.getItemInHand(hand));
                }
            }
            //prevent multi-click
            playerEntity.getCooldowns().addCooldown(this, TICK_PER_SECOND / 2);
            return ActionResult.success(playerEntity.getItemInHand(hand));
        }
    }
}
