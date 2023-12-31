package com.deeplake.caiqiu.items;

import com.deeplake.caiqiu.util.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import static com.deeplake.caiqiu.util.AdvancementUtil.giveAdvancement;
import static com.deeplake.caiqiu.util.CommonDef.TICK_PER_SECOND;
import static com.deeplake.caiqiu.util.IDLNBTDef.EGO_HP;
import static com.deeplake.caiqiu.util.IDLNBTDef.MJDS_EGO;

public class ItemAlterEgo extends BaseItemIDF{

    public ItemAlterEgo(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack stack = playerEntity.getItemInHand(hand);
        if (world.isClientSide)
        {
            return ActionResult.success(stack);
        }
        else {
            EgoUtil.trySwapEgo(playerEntity);
            int state = IDLNBT.getPlayerIdeallandIntSafe(playerEntity, MJDS_EGO);
            IDLNBTUtil.SetInt(stack, IDLNBTDef.STATE, IDLNBT.getPlayerIdeallandIntSafe(playerEntity, MJDS_EGO));
            IDLNBTUtil.SetDouble(stack, IDLNBTDef.EGO_HP, IDLNBT.getPlayerIdeallandDoubleSafe(playerEntity, EGO_HP));
            //prevent multi-click
            playerEntity.getCooldowns().addCooldown(this, TICK_PER_SECOND / 2);

            giveAdvancement(playerEntity, "alterego_used");
            if (EgoUtil.getEgo(playerEntity) == MJDSDefine.EnumEgo.POPLON)
            {
                giveAdvancement(playerEntity, "alterego_popolon");
            }else {
                giveAdvancement(playerEntity, "alterego_aphrodite");
            }

            return ActionResult.success(playerEntity.getItemInHand(hand));
        }
    }
}
