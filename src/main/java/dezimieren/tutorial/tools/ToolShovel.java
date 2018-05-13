package dezimieren.tutorial.tools;

import dezimieren.tutorial.Main;
import dezimieren.tutorial.init.ModItems;
import dezimieren.tutorial.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;

public class ToolShovel extends ItemSpade implements IHasModel{

	public ToolShovel(String name, ToolMaterial material) {
		super(material);
		// TODO Auto-generated constructor stub
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.TOOLS);
		
		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		// TODO Auto-generated method stub
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
