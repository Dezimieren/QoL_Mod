package dezimieren.tutorial.util.handler;

import dezimieren.tutorial.blocks.machines.compactor.TileEntityCompactor;
import dezimieren.tutorial.blocks.machines.macerator.TileEntityMacerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {

	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityMacerator.class, "macerator");
		GameRegistry.registerTileEntity(TileEntityCompactor.class, "compactor");
	}
}
