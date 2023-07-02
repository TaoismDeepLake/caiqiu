package com.deeplake.caiqiu.blocks;

import net.minecraft.block.material.Material;

public class BlockIndestructible extends BaseBlockMJDS {
    public BlockIndestructible() {
        super(Properties.of(Material.STONE)
                .strength(-1.0F, 3600000.0F));
    }

    public BlockIndestructible(Properties p_i48440_1_) {
        super(p_i48440_1_.of(Material.STONE)
                .strength(-1.0F, 3600000.0F));
    }
}
