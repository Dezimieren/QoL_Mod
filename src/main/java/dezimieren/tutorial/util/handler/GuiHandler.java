package dezimieren.tutorial.util.handler;

import dezimieren.tutorial.blocks.machines.macerator.ContainerMacerator;
import dezimieren.tutorial.blocks.machines.macerator.GuiMaceratorFurnace;
import dezimieren.tutorial.blocks.machines.macerator.TileEntityMacerator;
import dezimieren.tutorial.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == Reference.GUI_MACERATOR) return new ContainerMacerator(player.inventory, (TileEntityMacerator)world.getTileEntity(new BlockPos(x,y,z)));
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == Reference.GUI_MACERATOR) return new GuiMaceratorFurnace(player.inventory, (TileEntityMacerator)world.getTileEntity(new BlockPos(x,y,z)));

		return null;
	}

}
