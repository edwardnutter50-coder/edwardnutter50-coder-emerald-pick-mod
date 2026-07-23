package com.blaze.easynetheriteopemeraldpick;

import com.blaze.easynetheriteopemeraldpick.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fabric port of the original Forge/MCreator mod
 * "Easy Netherite + OP Emerald Pick" (originally built for 1.19.4).
 *
 * Ported to Minecraft 1.21.11 / Fabric.
 */
public class EasyNetheriteOpEmeraldPick implements ModInitializer {

	public static final String MOD_ID = "easy_netherite_op_emerald_pick";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Easy Netherite + OP Emerald Pick");
		ModItems.registerModItems();
	}
}
