package com.deeplake.caiqiu.blocks.te;


import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.registry.TileEntityRegistry;
import com.deeplake.caiqiu.util.CommonFunctions;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class TileEntityScoreMeasure extends TileEntity {
    HashMap<UUID,LocalDateTime> record = new HashMap<>();

    public TileEntityScoreMeasure() {
        super(TileEntityRegistry.SCORE_MEASURE.get());
        LocalDateTime.now();
    }

    public void interact(PlayerEntity playerEntity)
    {
        boolean canAcquireNewCheck = false;
        LocalDateTime lastTimeStamp = LocalDateTime.now();
        LocalDateTime now = LocalDateTime.now();
        UUID uuid = playerEntity.getUUID();
        if (record.containsKey(uuid))
        {
            lastTimeStamp = record.get(uuid);

            LocalDateTime nextTime = lastTimeStamp.plusHours(3);
            canAcquireNewCheck = !now.isBefore(nextTime);
        }
        else {
            canAcquireNewCheck = true;
        }

        if (canAcquireNewCheck)
        {
            record.remove(uuid);
            record.put(uuid, now.plusHours(3));
        }

        if (canAcquireNewCheck)
        {
            checkScore(playerEntity);
        }
        else {
            sendMessage(playerEntity, lastTimeStamp.plusHours(3));
        }
    }

    public void checkScore(PlayerEntity entity)
    {
        int score = 90;
        //todo:get Score
        CommonFunctions.SafeSendMsgToPlayer(TextFormatting.AQUA, entity, IdlFramework.MOD_ID +".msg.cur_score", score);
    }

    public void sendMessage(PlayerEntity entity, LocalDateTime nextCheck)
    {
        CommonFunctions.SafeSendMsgToPlayer(TextFormatting.AQUA, entity, IdlFramework.MOD_ID +".msg.wait_score_check", nextCheck);
    }

    static final String TOTAL_KEY = "cooldownlist";

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT nbt) {
        super.load(p_230337_1_, nbt);
        //load the record hashmap from nbt
        CompoundNBT uuidList = nbt.getCompound(TOTAL_KEY);
        for (String key: uuidList.getAllKeys()
             ) {
            UUID uuid = UUID.fromString(key);
            record.put(uuid, LocalDateTime.ofEpochSecond(uuidList.getLong(key), 0, java.time.ZoneOffset.UTC));
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        //Serialize the record hashmap into nbt
        CompoundNBT totalList = new CompoundNBT();
        for (UUID uuid: record.keySet()
        ) {
            LocalDateTime time = record.get(uuid);
            totalList.putLong(uuid.toString(), time.toEpochSecond(java.time.ZoneOffset.UTC));
        }
        nbt.put(TOTAL_KEY, totalList);
        return super.save(nbt);
    }
}
