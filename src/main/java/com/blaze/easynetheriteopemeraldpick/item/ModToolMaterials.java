package com.blaze.easynetheriteopemeraldpick.item;

import com.blaze.easynetheriteopemeraldpick.EasyNetheriteOpEmeraldPick;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

/**
 * Recreates the custom anonymous {@code Tier} implementation found in the
 * original decompiled mod (net.mcreator...item.DdhItem$1).
 *
 * Original decompiled values (Minecraft 1.19.4, mojmap SRG names):
 *   m_6609_() -> getUses()              = 100000   (durability)
 *   m_6624_() -> getSpeed()              = 100000.0F (mining speed)
 *   m_6631_() -> getAttackDamageBonus()  = 2.0F
 *   m_6604_() -> getLevel()              = 20        (mines everything - no vanilla
 *                                                      tier requires level > 4)
 *   m_6601_() -> getEnchantmentValue()   = 30
 *   m_6282_() -> getRepairIngredient()   = Ingredient.of(new ItemStack(Items.EMERALD))
 *
 * In modern (1.20.5+/1.21.x) Fabric, the old int "mining level" system was replaced
 * by a block tag of blocks the tool is INCORRECT for. To reproduce "mines every
 * block correctly" we point at an empty custom block tag (nothing is ever incorrect).
 */
public final class ModToolMaterials {

	private ModToolMaterials() {
	}

	/** Empty on purpose: nothing is "incorrect" for this pickaxe, so it mines everything. */
	public static final TagKey<Block> INCORRECT_FOR_EMERALD_PICK = TagKey.of(RegistryKeys.BLOCK,
			Identifier.of(EasyNetheriteOpEmeraldPick.MOD_ID, "incorrect_for_emerald_pick"));

	/** Contains only minecraft:emerald, matching the decompiled repair ingredient. */
	public static final TagKey<Item> REPAIRS_EMERALD_PICK = TagKey.of(RegistryKeys.ITEM,
			Identifier.of(EasyNetheriteOpEmeraldPick.MOD_ID, "repairs_emerald_pick"));

	public static final ToolMaterial EMERALD_PICK_MATERIAL = new ToolMaterial(
			INCORRECT_FOR_EMERALD_PICK,
			100000,
			100000.0F,
			2.0F,
			30,
			REPAIRS_EMERALD_PICK
	);
}
