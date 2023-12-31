package com.deeplake.caiqiu.util;

import static com.deeplake.caiqiu.IdlFramework.MOD_ID;

public class MessageDef {
    public static String DEAL_SUCCESS = MOD_ID + ".msg.deal.success";
    public static String DEAL_FAIL = MOD_ID + ".msg.deal.fail";
    public static String BOX_FAIL = MOD_ID + ".msg.box.already_get";

    public static String NO_SPAWN = MOD_ID + ".msg.teleport.no_spawn";
    public static String TELEPORT_ANCHOR_SET = MOD_ID + ".msg.teleport.set_anchor";

    public static String REQ_POPOLON = MOD_ID + ".msg.ego.req.popolon";
    public static String REQ_APHRODITE = MOD_ID + ".msg.ego.req.aphrodite";

    public static String EGO_DOOR_CLOSER = MOD_ID + ".msg.ego.req.ego_door_closer";

    public static String SWAP_EGO = MOD_ID + ".msg.ego.swap.";
    public static String getSwapEgoMsgKey(MJDSDefine.EnumEgo resultEgo)
    {
        return SWAP_EGO.concat(String.valueOf(resultEgo.value));
    }
}
