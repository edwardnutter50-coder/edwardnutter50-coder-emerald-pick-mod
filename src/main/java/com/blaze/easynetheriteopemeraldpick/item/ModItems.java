package com.blaze.easynetheriteopemeraldpick.item;

import com.blaze.easynetheriteopemeraldpick.EasyNetheriteOpEmeraldPick;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.PickaxeItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

/**
 * Item registration for the ported mod.
 *
 * Original registered id (from EasyNetheriteOpEmeraldPickModItems.class, MCreator):
 *   easy_netherite_op_emerald_pick:emerald_pick  -> displayed as "God Pick"
 *
 * Original PickaxeItem constructor call (decompiled):
 *   new DdhItem$1() /* custom Tier *&#47;, attackDamageModifier = 1, attackSpeedModifier = -3.0F,
 *   new Item.Properties().<zero-arg builder call, most consistent with fireResistant()>
 */
public final class ModItems {

	private ModItems() {
	}

	public static final Item EMERALD_PICK = registerItem("emerald_pick",
			settings -> new PickaxeItem(ModToolMaterials.EMERALD_PICK_MATERIAL, 1.0F, -3.0F, settings));

	private static Item registerItem(String name, Function<Item.Settings, Item> factory) {
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM,
				Identifier.of(EasyNetheriteOpEmeraldPick.MOD_ID, name));
		Item item = factory.apply(new Item.Settings().registryKey(key).fireproof());
		return Registry.register(Registries.ITEM, key, item);
	}

	public static void registerModItems() {
		// Original MCreator mod added this item to a vanilla creative tab via a
		// CreativeModeTabEvent.BuildContents subscriber. The exact vanilla tab could
		// not be reconstructed with certainty from bytecode alone (only its obfuscated
		// field reference remained), so it has been placed in the Tools & Utilities
		// tab here, which is the closest natural fit for a pickaxe.
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(EMERALD_PICK));
	}
}
