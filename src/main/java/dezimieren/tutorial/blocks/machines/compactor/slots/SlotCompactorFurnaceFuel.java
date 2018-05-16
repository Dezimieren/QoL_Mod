package dezimieren.tutorial.blocks.machines.compactor.slots;

import dezimieren.tutorial.blocks.machines.compactor.TileEntityCompactor;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCompactorFurnaceFuel extends Slot{
	
	public SlotCompactorFurnaceFuel(IInventory inventory, int index, int x, int y)
	{
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return TileEntityCompactor.isItemFuel(stack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return super.getItemStackLimit(stack);
	}

}
