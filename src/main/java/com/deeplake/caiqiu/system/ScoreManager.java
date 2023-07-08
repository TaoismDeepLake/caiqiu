package com.deeplake.caiqiu.system;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.util.CommonFunctions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.FolderName;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber
public class ScoreManager {
    HashMap<UUID, Integer> record = new HashMap<>();
    //Create Singleton instance
    private static ScoreManager _instance = new ScoreManager();

    public static ScoreManager getInstance() {
        return _instance;
    }

    public static int defaultScore = 50;

    //Subscribe world load event
    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        IWorld world = event.getWorld();
        if (world.isClientSide()) return;

        _instance.init((World) world);
    }

    @SubscribeEvent
    public static void onWorldSave(WorldEvent.Save event) {
        IWorld world = event.getWorld();
        if (world.isClientSide()) return;

        _instance.save((World) world);
    }

    public void save(World world)
    {
        try {
            MinecraftServer server =  Objects.requireNonNull(world.getServer());
            Path path = server.getWorldPath(FolderName.ROOT).resolve("score.txt");

            File f = new File(path.toString());
            if(f.exists() && !f.isDirectory()) {
                OutputStream outputStream = new FileOutputStream(path.toString());
                for (UUID uuid: record.keySet())
                {
                    String line = uuid.toString() + "=" + record.get(uuid).toString();
                    CommonFunctions.writeLine(outputStream, line);
                }
                outputStream.close();
            }
            else {
                throw new RuntimeException(new FileNotFoundException("score file is a path, not a file"));
            }
        } catch (FileNotFoundException e) {
            IdlFramework.LogWarning("Score file not found");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //open a file that stores the score
    public void init(World world)
    {
        record.clear();
        try {
            MinecraftServer server =  Objects.requireNonNull(world.getServer());
            Path path = server.getWorldPath(FolderName.ROOT).resolve("score.txt");
            IdlFramework.Log("Score file path: %s", path.toString());

            File f = new File(path.toString());
            if (!f.exists())
            {
                if (f.createNewFile())
                {
                    IdlFramework.Log("Score file not found, create a new one");
                }
                else {
                    throw new RuntimeException(new FileNotFoundException("Cannot create score file"));
                }
            }

            if (f.exists()) {
                if (!f.isDirectory()) {
                    InputStream inputStream = new FileInputStream(path.toString());
                    //read the file as format a = b
                    //a is UUID, b is score
                    while (inputStream.available() > 0) {
                        //read one line
                        String line = CommonFunctions.readLine(inputStream);
                        String[] parts = line.split("=");
                        if (parts.length == 2) {
                            UUID uuid = UUID.fromString(parts[0]);
                            int score = Integer.parseInt(parts[1]);
                            record.put(uuid, score);
                        } else {
                            IdlFramework.LogWarning("Skip invalid line in score file:" + line);
                        }
                    }
                    IdlFramework.Log("Loaded score file for %s players.", record.size());
                    inputStream.close();
                } else {
                    throw new RuntimeException(new FileNotFoundException("score file is a path, not a file"));
                }
            } else {
                throw new RuntimeException(new FileNotFoundException("score file path, does not exist"));
            }
        } catch (FileNotFoundException e) {
            IdlFramework.LogWarning("Score file not found");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void PrintScoreListTo(PlayerEntity entity)
    {
        for (UUID uuid: getInstance().record.keySet()) {
            MinecraftServer server = entity.level.getServer();
            if (server == null) return;
            ServerPlayerEntity player = server.getPlayerList().getPlayer(uuid);
            if (player == null)
            {
                String uuidStr = uuid.toString();
                IdlFramework.Log("Player %s not found", uuidStr);
                int score = getInstance().record.get(uuid);
                CommonFunctions.SafeSendMsgToPlayer(entity,
                        uuidStr.substring(uuidStr.length() - 6) + " : " + score);
            }
            else {
                String name = player.getName().getString();
                
                int score = getInstance().record.get(uuid);
                CommonFunctions.SafeSendMsgToPlayer(entity,name + " : " + score);
            }
        }
    }

    public static int GetScore(UUID uuid)
    {
        return getInstance().record.getOrDefault(uuid, defaultScore);
    }

    public static void SetScore(UUID uuid, int score)
    {
        getInstance().record.put(uuid, score);
    }

    public static int AddScore(UUID uuid, int score)
    {
        int currentScore = GetScore(uuid);
        SetScore(uuid, currentScore + score);
        return currentScore + score;
    }
}
