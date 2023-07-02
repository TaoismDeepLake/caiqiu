package com.deeplake.caiqiu.items.weapons;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.entities.IMjdsMonster;
import com.deeplake.caiqiu.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

import java.util.List;

import static com.deeplake.caiqiu.util.EntityUtil.IS_MJDS;

@Mod.EventBusSubscriber
public class CleanserSword extends BaseItemSword {
    public CleanserSword() {
        super();
    }

    public CleanserSword(IItemTier p_i48460_1_, int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
        super(p_i48460_1_, p_i48460_2_, p_i48460_3_, p_i48460_4_);
    }


    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onHit(LivingHurtEvent event) {
        World world = event.getEntityLiving().level;
        if (world.isClientSide) {
            return;
        }

        LivingEntity hurtOne = event.getEntityLiving();

        if (event.getSource().getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();

            if (attacker.getMainHandItem().getItem() instanceof CleanserSword &&
                    hurtOne instanceof IMjdsMonster)
            {
                exileEntity(hurtOne, attacker);
            }
        }
    }

    //Massively copied from BowItem
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {

        ItemStack stack = playerEntity.getItemInHand(hand);
        if (hand == Hand.MAIN_HAND || !playerEntity.isCreative())
        {
            return super.use(world, playerEntity, hand);
        }

        if (world.isClientSide) {
            return ActionResult.success(stack);
        }

        playerEntity.awardStat(Stats.ITEM_USED.get(this));
        CommonFunctions.activateCooldown(stack, playerEntity, CommonDef.TICK_PER_SECOND);

        List<Entity> list = EntityUtil.getEntitiesWithinAABB(world, null, playerEntity.position(), 3f, IS_MJDS);
        for (Entity monster : list)
        {
            exileEntity(monster, playerEntity);
        }

        return ActionResult.success(stack);
    }

    public static void exileEntity(Entity hurtOne, @Nullable Entity source)
    {
        IDLNBTUtil.SetInt(hurtOne, IDLNBTDef.NO_REVIVE, 1);
        if (source != null)
        {
            IdlFramework.Log("%s(%s) removed %s @%s permanently.", source.getName().getContents(), source.getStringUUID(), hurtOne.getName().getContents(), ((IMjdsMonster) hurtOne).getRespawn());
        }
        hurtOne.remove();
    }
}
