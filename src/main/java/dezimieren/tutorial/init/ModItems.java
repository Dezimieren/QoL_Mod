package dezimieren.tutorial.init;

import java.util.ArrayList;
import java.util.List;

import dezimieren.tutorial.armor.ArmorBase;
import dezimieren.tutorial.items.ItemBase;
import dezimieren.tutorial.tools.ToolAxe;
import dezimieren.tutorial.tools.ToolBase;
import dezimieren.tutorial.tools.ToolEnder;
import dezimieren.tutorial.tools.ToolHoe;
import dezimieren.tutorial.tools.ToolPickaxe;
import dezimieren.tutorial.tools.ToolShovel;
import dezimieren.tutorial.util.Reference;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {

	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//Material
	//public static final ArmorMaterial ENDER_ARMOR = EnumHelper.addArmorMaterial("ender_armor", Reference.MOD_ID + ":ender", 13, new int[] {2,5,5,2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F);
	public static final ToolMaterial TOOL_EMERALD = EnumHelper.addToolMaterial("tool_emerald", 3, 4000, 8.0F, 3.0F, 22);
	public static final ToolMaterial TOOL_OBSIDIAN = EnumHelper.addToolMaterial("tool_obsidian", 1, 4000, 6.0F, 2.0F, 5);
	public static final ToolMaterial TOOL_ENDER =EnumHelper.addToolMaterial("tool_ender", 3, 1000000, 8.0F , 12.0F, 50);
	
	//items and ores/shards
	public static final Item OBSIDIAN_SHARD = new ItemBase("obsidian_shard");
	
	
	
	//Pickaxes
	public static final Item EMERALD_PICKAXE = new ToolPickaxe("emerald_pickaxe", TOOL_EMERALD);
	public static final Item OBSIDIAN_PICKAXE = new ToolPickaxe("obsidian_pickaxe", TOOL_OBSIDIAN);
	
	//Boats and Hoes
	public static final Item EMERALD_HOE = new ToolHoe("emerald_hoe", TOOL_EMERALD);
	public static final Item OBSIDIAN_HOE = new ToolHoe("obsidian_hoe", TOOL_OBSIDIAN);
	
	
	//Axes
	public static final Item EMERALD_AXE = new ToolAxe("emerald_axe", TOOL_EMERALD);
	public static final Item OBSIDIAN_AXE = new ToolAxe("obsidian_axe", TOOL_OBSIDIAN);
	
	//Shovels
	public static final Item EMERALD_SHOVEL = new ToolShovel("emerald_shovel", TOOL_EMERALD);
	public static final Item OBSIDIAN_SHOVEL = new ToolShovel("obsidian_shovel", TOOL_OBSIDIAN);
	
	//Armor
	//public static final Item ENDER_HELMET = new ArmorBase("ender_helmet", ENDER_ARMOR, 1, EntityEquipmentSlot.HEAD);
	
	//Ender
	public static final Item ENDER_GLAIVE = new ToolEnder("ender_glaive", TOOL_ENDER);
	
}
