package dezimieren.tutorial.blocks.machines.macerator.slots;

import dezimieren.tutorial.blocks.machines.macerator.TileEntityMacerator;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMaceratorFurnaceFuel extends Slot{
	
	public SlotMaceratorFurnaceFuel(IInventory inventory, int index, int x, int y)
	{
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return TileEntityMacerator.isItemFuel(stack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return super.getItemStackLimit(stack);
	}

}
