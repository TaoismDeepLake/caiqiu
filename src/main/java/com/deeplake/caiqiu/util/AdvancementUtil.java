package com.deeplake.caiqiu.util;

import com.deeplake.caiqiu.IdlFramework;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class AdvancementUtil {
    public static final String ACHV_BREAKABLE_WALL = "breakable_wall";
    public static final String ACHV_WATCH_YOUR_STEP = "watch_your_step";
    public static final String ACHV_MANTLE = "mantle";
    public static final String ACHV_PURE_WATER = "pure_water";

    public static final String ACHV_ALTEREGO_APHRODITE = "alterego_aphrodite";
    public static final String ACHV_ALTEREGO_POPOLON = "alterego_popolon";
    public static final String ACHV_ALTEREGO_USED = "alterego_used";
    public static final String ACHV_CERAMIC_BOW = "ceramic_bow";
    public static final String ACHV_RED_BOW = "red_bow";
    public static final String ACHV_MAP = "map";

    public static final String ACHV_MONGO_SWORD = "mongo_sword";
    public static final String ACHV_GREAT_KEY_1 = "great_key_1";

    public static final String ACHV_ANGEL_RING = "angel_ring";
    public static final String ACHV_DEVIL_WING = "devil_wing";
    public static final String ACHV_LAMP = "lamp";
    public static final String ACHV_SALT = "salt";
    public static final String ACHV_BOOTS = "boots";
    public static final String ACHV_SABRE = "sabre";
    public static final String ACHV_JAR = "jar";
    public static final String ACHV_MAGICAL_ROD = "magical_rod";

    public static final String ACHV_TREASURE_A = "treasure_a_1";
    public static final String ACHV_TREASURE_B = "treasure_b_1";
    public static final String ACHV_TREASURE_C = "treasure_c_1";

    public static final String DUNGEON_1 = "dungeon_1";
    public static final String DUNGEON_2 = "dungeon_2";
    public static final String DUNGEON_3 = "dungeon_3";
    public static final String DUNGEON_4 = "dungeon_4";
    public static final String DUNGEON_5 = "dungeon_5";

    public static final String ACHV_ROOT = "root";


    public static boolean giveAdvancement(PlayerEntity player, String id)
    {
        if (player.level.isClientSide)
        {
            return false;
        }

        ServerPlayerEntity playerMP = (ServerPlayerEntity) player;
        Advancement advancement = findAdvancement(player.getServer(), id);
        if (advancement == null)
        {
            IdlFramework.LogWarning("failed to find an advancement:%s", id);
            return false;
        }

        return giveAdvancement(player, advancement);
    }

    public static boolean giveAdvancement(PlayerEntity player, Advancement advancement)
    {
        if (player.level.isClientSide)
        {
            return false;
        }

        ServerPlayerEntity playerMP = (ServerPlayerEntity) player;
        if (advancement == null)
        {
            return false;
        }
        AdvancementProgress advancementprogress = playerMP.getAdvancements().getOrStartProgress(advancement);
        if (advancementprogress.isDone()) {
            return false;
        } else {
            for(String s : advancementprogress.getRemainingCriteria()) {
                playerMP.getAdvancements().award(advancement, s);
            }

            return true;
        }
    }

    public static boolean hasAdvancement(ServerPlayerEntity p_192552_2_, String id)
    {
        Advancement adv = findAdvancement(p_192552_2_.server, id);
        if (adv == null)
        {
            return false;
        }
        else {
            return hasAdvancement(p_192552_2_, adv);
        }
    }
    public static boolean hasAdvancement(ServerPlayerEntity p_192552_2_, Advancement p_192552_3_)
    {
        AdvancementProgress advancementprogress = p_192552_2_.getAdvancements().getOrStartProgress(p_192552_3_);
        return advancementprogress.isDone();
    }

    public static Advancement findAdvancement(MinecraftServer server, String id)
    {
        Advancement advancement = server.getAdvancements().getAdvancement(new ResourceLocation(IdlFramework.MOD_ID, id));
        if (advancement == null)
        {
            advancement = server.getAdvancements().getAdvancement(new ResourceLocation(id));//Also try vanilla
        }

        if (advancement == null)
        {
            IdlFramework.Log("Cannot find advancement:%s", id);
            return null;
        }
        else
        {
            return advancement;
        }
    }
}
