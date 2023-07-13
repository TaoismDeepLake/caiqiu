package com.deeplake.caiqiu.items;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.util.CommonFunctions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import static com.deeplake.caiqiu.util.EntityUtil.NON_SPEC;

public class ItemRecallLight extends BaseItemIDF{

    @Override
    public boolean useOnRelease(ItemStack p_219970_1_) {
        return true;
    }

    @Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return 40;
    }

    public static final String RECALL_LIGHT_USED = IdlFramework.MOD_ID + ".msg.recall_light_used";

    public void releaseUsing(ItemStack stack, World world, LivingEntity user, int ticksLeft) {
        int i = this.getUseDuration(stack) - ticksLeft;
        if (!world.isClientSide)
        {
            if (i <= 0) {
                SoundCategory soundcategory = user instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, soundcategory, 1.0F, 1f);
                EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(world, user, user.getEyePosition(0f), user.getForward(), user.getBoundingBox(), NON_SPEC);

                Entity target = null;
                if (result != null) {
                    target = result.getEntity();
                }
                if (target != null) {
                    if (target instanceof PlayerEntity) {
                        PlayerEntity targetPlayer = (PlayerEntity) target;
                        //traverse all players
                        for (PlayerEntity playerEntity : world.players()) {
                            if (playerEntity == targetPlayer || playerEntity == user || playerEntity.isCreative() || playerEntity.isSpectator()) {
                                CommonFunctions.SafeSendMsgToPlayer(TextFormatting.AQUA, targetPlayer,RECALL_LIGHT_USED, user.getDisplayName(), targetPlayer.getDisplayName());
                            }
                        }
                        stack.shrink(1);
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
