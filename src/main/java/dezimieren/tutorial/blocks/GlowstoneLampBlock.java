package dezimieren.tutorial.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class GlowstoneLampBlock extends BlockBase{

	public GlowstoneLampBlock(String name, Material material) {
		super(name, material);
		// TODO Auto-generated constructor stub
		setSoundType(SoundType.GLASS);
		setHardness(0.3F);
		setResistance(1.5F);
		//setHarvestLevel("spade", 0);
		setLightLevel(1.0F);
		setLightOpacity(255);
		//setBlockUnbreakable();
		setCreativeTab(CreativeTabs.REDSTONE);
	}

	
	
}
