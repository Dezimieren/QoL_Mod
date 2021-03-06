package dezimieren.tutorial.init;

import java.util.ArrayList;
import java.util.List;

import dezimieren.tutorial.blocks.BlockBase;
import dezimieren.tutorial.blocks.customblocks.GlowstoneLampBlock;
import dezimieren.tutorial.blocks.machines.compactor.BlockCompactor;
import dezimieren.tutorial.blocks.machines.macerator.BlockMacerator;
import dezimieren.tutorial.blocks.tnt.smarttnt.BlockSmartTNT;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {

	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block GlOWSTONE_LAMP_BLOCK = new GlowstoneLampBlock("glowstone_lamp_block", Material.GLASS);
	
	public static final Block MACERATOR = new BlockMacerator("macerator");
	public static final Block COMPACTOR = new BlockCompactor("compactor");
	
	public static final Block SMART_TNT = new BlockSmartTNT("smart_tnt");	
}
