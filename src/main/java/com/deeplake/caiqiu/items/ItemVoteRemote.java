package com.deeplake.caiqiu.items;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.events.EventsClassTrial;
import com.deeplake.caiqiu.util.CommonFunctions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import static com.deeplake.caiqiu.util.EntityUtil.NON_SPEC;

public class ItemVoteRemote extends BaseItemIDF {

    public static final String ACCUSED_MSG = IdlFramework.MOD_ID + ".msg.accused";
    public static final String ACCUSED_COUNT_MSG = IdlFramework.MOD_ID + ".msg.accused.count";

    public void releaseUsing(ItemStack stack, World world, LivingEntity user, int ticksLeft) {
        int i = this.getUseDuration(stack) - ticksLeft;
        if (!world.isClientSide && user instanceof PlayerEntity)
        {
            if (i <= 0) {
                SoundCategory soundcategory = SoundCategory.PLAYERS;
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, soundcategory, 1.0F, 1f);
                EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(world, user, user.getEyePosition(0f), user.getForward(), user.getBoundingBox(), NON_SPEC);

                Entity target = null;
                if (result != null) {
                    target = result.getEntity();
                }
                if (target != null) {
                    if (target instanceof PlayerEntity) {
                        if (EventsClassTrial.GetTrialStatus() == EventsClassTrial.EnumClassTrialStatus.ONGOING) {
                            PlayerEntity targetPlayer = (PlayerEntity) target;
                            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.AQUA, user,ACCUSED_MSG, targetPlayer.getDisplayName());
                            EventsClassTrial.AddAccuseCount(targetPlayer.getUUID());
                            int curCount = EventsClassTrial.GetAccuseCount(targetPlayer.getUUID());
                            //broadcast to all players
                            for (PlayerEntity playerEntity : world.players()) {
                                if (playerEntity == targetPlayer || playerEntity == user || playerEntity.isCreative() || playerEntity.isSpectator()) {
                                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.AQUA, targetPlayer,ACCUSED_COUNT_MSG, targetPlayer.getDisplayName(), curCount);
                                    IdlFramework.Log(String.format("%s->%s",user.getDisplayName(), targetPlayer.getDisplayName()) );
                                }
                            }

                            PlayerEntity playerEntity = (PlayerEntity) user;
                            //1min cooldown
                            playerEntity.getCooldowns().addCooldown(this, 20 * 60);
                        }
                    }
                }
            }
        }
    }
}
