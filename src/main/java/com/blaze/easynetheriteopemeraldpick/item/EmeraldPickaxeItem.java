package com.blaze.easynetheriteopemeraldpick.item;

import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

/**
 * PickaxeItem has a protected constructor in vanilla, so a small public
 * subclass is required to instantiate it from outside its package. This
 * mirrors how the original decompiled mod's item class was structured too.
 */
public class EmeraldPickaxeItem extends PickaxeItem {
	public EmeraldPickaxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
	}
}
