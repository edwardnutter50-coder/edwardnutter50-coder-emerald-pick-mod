package com.blaze.easynetheriteopemeraldpick.item;

import com.blaze.easynetheriteopemeraldpick.EasyNetheriteOpEmeraldPick;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

/**
 * Item registration for the ported mod.
 *
 * As of Minecraft 1.21.11, pickaxes are no longer a separate PickaxeItem
 * class - they are plain Items configured via the new
 * Item.Settings#pickaxe(ToolMaterial, attackDamage, attackSpeed) builder.
 */
public final class ModItems {

	private ModItems() {
	}

	public static final Item EMERALD_PICK = registerItem("emerald_pick",
			settings -> new Item(settings.pickaxe(ModToolMaterials.EMERALD_PICK_MATERIAL, 1.0F, -3.0F)));

	private static Item registerItem(String name, Function<Item.Settings, Item> factory) {
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM,
				Identifier.of(EasyNetheriteOpEmeraldPick.MOD_ID, name));
		Item item = factory.apply(new Item.Settings().registryKey(key).fireproof());
		return Registry.register(Registries.ITEM, key, item);
	}

	public static void registerModItems() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(EMERALD_PICK));
	}
}
