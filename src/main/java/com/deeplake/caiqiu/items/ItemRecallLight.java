package com.deeplake.caiqiu.items;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.util.CommonDef;
import com.deeplake.caiqiu.util.CommonFunctions;
import com.deeplake.caiqiu.util.ProjectileUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemRecallLight extends BaseItemIDF{

    @Override
    public boolean useOnRelease(ItemStack p_219970_1_) {
        return true;
    }

    @Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return 9999;
    }

    public static final String RECALL_LIGHT_USED = IdlFramework.MOD_ID + ".msg.recall_light_used";

    public void releaseUsing(ItemStack stack, World world, LivingEntity user, int ticksLeft) {
        int i = this.getUseDuration(stack) - ticksLeft;
        if (!world.isClientSide)
        {
            //if holding over 1 sec
            if (i >= CommonDef.TICK_PER_SECOND) {
                SoundCategory soundcategory = user instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, soundcategory, 1.0F, 1f);
                float range = 16f;
                Vector3d userPos = user.getEyePosition(0);
                Vector3d vector3d1 = user.getViewVector(1.0F);
                Vector3d vector3d2 = userPos.add(vector3d1.x * range, vector3d1.y * range, vector3d1.z * range);
                AxisAlignedBB axisalignedbb = user.getBoundingBox().expandTowards(vector3d1.scale(range)).inflate(1.0D, 1.0D, 1.0D);
                EntityRayTraceResult entityraytraceresult = ProjectileUtil.getEntityHitResult(user, userPos, vector3d2, axisalignedbb, (p_215312_0_) -> {
                    return !p_215312_0_.isSpectator() && p_215312_0_.isPickable();
                }, range*range);

                Entity target = null;
                if (entityraytraceresult != null) {
                    if (entityraytraceresult.getType() == RayTraceResult.Type.ENTITY) {
                        target = entityraytraceresult.getEntity();
                    }
                }

                if (target != null) {
                    if (target instanceof PlayerEntity) {
                        PlayerEntity targetPlayer = (PlayerEntity) target;
                        if (targetPlayer.isCreative() || targetPlayer.isSpectator())
                        {
                            return;
                        }
                        //traverse all players
                        for (PlayerEntity playerEntity : world.players()) {
                            if (playerEntity == targetPlayer || playerEntity == user || playerEntity.isCreative() || playerEntity.isSpectator()) {
                                CommonFunctions.SafeSendMsgToPlayer(TextFormatting.AQUA, playerEntity,RECALL_LIGHT_USED, user.getDisplayName(), targetPlayer.getDisplayName());
                            }
                        }

                        if (user instanceof PlayerEntity)
                        {
                            boolean flag = ((PlayerEntity) user).abilities.instabuild;
                            if (!flag)
                            {
                                stack.shrink(1);
                            }
                        }
                    }
                }

            }
        }
    }

    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);

        p_77659_2_.startUsingItem(p_77659_3_);
        return ActionResult.consume(itemstack);
    }

    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.BOW;
    }

}
