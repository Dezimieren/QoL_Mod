package dezimieren.tutorial.blocks.machines.macerator;

import dezimieren.tutorial.init.ModBlocks;
import dezimieren.tutorial.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMacerator extends TileEntity implements IInventory, ITickable {

	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);
	private String customName;
	
	private int burnTime;
	private int currentBurnTime;
	private int cookTime;
	private int totalCookTime;
	
	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.macerator";
	}
	
	@Override 
	public boolean hasCustomName() 
	{
		return this.customName != null && !this.customName.isEmpty();
	}
	
	public void setCustomName(String customName)
	{
		this.customName = customName;
	}
	
	@Override 
	public ITextComponent getDisplayName()
	{
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}
	
	@Override
	public int getSizeInventory()
	{
		return this.inventory.size();
	}
	
	@Override
	public boolean isEmpty() 
	{
		for(ItemStack stack : this.inventory)
		{
			if (!stack.isEmpty()) return false;
		}
		
		return true;
	}
	
	@Override 
	public ItemStack getStackInSlot(int index)
	{
		return (ItemStack)this.inventory.get(index);
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.inventory, index, count);
	}
	
	@Override 
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.inventory, index);
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack itemstack = (ItemStack)this.inventory.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.inventory.set(index, stack);
		
		if (stack.getCount() > this.getInventoryStackLimit())
			stack.setCount(this.getInventoryStackLimit());
		if (index == 0 && !flag) 
		{
			this.totalCookTime = this.getCookTime(stack);
			this.cookTime = 0;
			this.markDirty();
		}
	}
	
	@Override 
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.inventory);
		this.burnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this.currentBurnTime = getItemBurnTime((ItemStack)this.inventory.get(1));
		
		if (compound.hasKey("CustomeName", 8)) this.setCustomName(compound.getString("CustomName"));
	}
	
	@Override 
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short)this.burnTime);
		compound.setInteger("CookTime", (short)this.cookTime);
		compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
		ItemStackHelper.saveAllItems(compound, this.inventory);
		
		if (this.hasCustomName()) compound.setString("CustomName", this.customName);
		return compound;
	}
	
	@Override 
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	public boolean isBurning()
	{
		return this.burnTime > 0;
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inventory)
	{
		return inventory.getField(0) > 0;
	}
	
	public void update()
	{
		boolean flag = this.isBurning();
		boolean flag1 = false;
		
		if(this.isBurning()) --this.burnTime;
		
		if(!this.world.isRemote)
		{
			ItemStack stack = (ItemStack)this.inventory.get(1);
			
			if(this.isBurning() || !stack.isEmpty() && !((ItemStack)this.inventory.get(0)).isEmpty())
			{
				if(!this.isBurning() && this.canSmelt())
				{
					this.burnTime = getItemBurnTime(stack);
					this.currentBurnTime = this.burnTime;
					
					if (this.isBurning())
					{
						flag1 = true;
						
						if(!stack.isEmpty())
						{
							Item item = stack.getItem();
							stack.shrink(1);
							
							if (stack.isEmpty())
							{
								ItemStack item1 = item.getContainerItem(stack);
								this.inventory.set(1, item1);
							}
						}
					}
				}
				if (this.isBurning() && this.canSmelt())
				{
					++this.cookTime;
					
					if(this.cookTime == this.totalCookTime)
					{
						this.cookTime = 0;
						this.totalCookTime = this.getCookTime((ItemStack)this.inventory.get(0));
						this.smeltItem();
						flag1 = true;
					}
				}
				else this.cookTime = 0;
			}
			else if(!this.isBurning() && this.cookTime > 0)
			{
				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
			}
			if (flag != this.isBurning())
			{
				flag1 = true;
				BlockMacerator.setState(this.isBurning(), this.world, this.pos);
			}
		} if(flag1)
			this.markDirty();
	}
	
	public int getCookTime(ItemStack fuel)
	{
		if(fuel.isEmpty()) return 0;
		else
		{
			Item item = fuel.getItem();
			
			if (item != ModItems.NETHERNOL)
			{
				if (item != ModItems.ENRICHED_NETHERNOL)
				{
					return 200;
				}
				else 
				{
					return 25;
				}
			}
			else return 25;
		}
		
	}
	
	private boolean canSmelt()
	{
		if (((ItemStack)this.inventory.get(0)).isEmpty()) return false;
		else
		{
			//ItemStack result = MaceratorRecipes.getInstance().getMaceratorResult((ItemStack)this.inventory.get(0), (ItemStack)this.inventory.get(1));
			ItemStack result = MaceratorRecipes.getInstance().getMaceratorResult((ItemStack)this.inventory.get(0));
			if(result.isEmpty()) return false;
			else
			{
				ItemStack output = (ItemStack)this.inventory.get(2);
				if (output.isEmpty()) return true;
				else if (!output.isItemEqual(result)) return false;
				
				else if (output.getCount() + result.getCount() <= this.getInventoryStackLimit() && output.getCount() + result.getCount() <= output.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
                {
                    return true;
                }
                else
                {
                    return output.getCount() + result.getCount() <= result.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
//				int res = output.getCount() + result.getCount();
//				return res <= getInventoryStackLimit() && res <= output.getMaxStackSize();
			}
		}
	}
	
	public void smeltItem()
	{
		if(this.canSmelt())
		{
			ItemStack input1 = (ItemStack)this.inventory.get(0);
			//ItemStack input2 = (ItemStack)this.inventory.get(1);
			//ItemStack result = MaceratorRecipes.getInstance().getMaceratorResult(input1, input2);
			ItemStack result = MaceratorRecipes.getInstance().getMaceratorResult(input1);
			ItemStack output = (ItemStack)this.inventory.get(2);
			
			if(output.isEmpty()) this.inventory.set(2,  result.copy());
			else if(output.getItem() == result.getItem()) output.grow(result.getCount());
			
			 if (input1.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && input1.getMetadata() == 1 && !((ItemStack)this.inventory.get(1)).isEmpty() && ((ItemStack)this.inventory.get(1)).getItem() == Items.BUCKET)
	         {
				 this.inventory.set(1, new ItemStack(Items.WATER_BUCKET));
	         }
			
			input1.shrink(1);
			//input2.shrink(1);
		}
	}
	
	public static int getItemBurnTime(ItemStack fuel)
	{
		if(fuel.isEmpty()) return 0;
		else
		{
			Item item = fuel.getItem();
			
			if (item == Item.getItemFromBlock(Blocks.WOODEN_SLAB))
            {
                return 150;
            }
            else if (item == Item.getItemFromBlock(Blocks.WOOL))
            {
                return 100;
            }
            else if (item == Item.getItemFromBlock(Blocks.CARPET))
            {
                return 67;
            }
            else if (item == Item.getItemFromBlock(Blocks.LADDER))
            {
                return 300;
            }
            else if (item == Item.getItemFromBlock(Blocks.WOODEN_BUTTON))
            {
                return 100;
            }
            else if (Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD)
            {
                return 300;
            }
            else if (item == Item.getItemFromBlock(Blocks.COAL_BLOCK))
            {
                return 16000;
            }
            else if (item == ModItems.NETHERNOL)
            {
                return 69120;
            }
            else if (item == ModItems.ENRICHED_NETHERNOL)
            {
                return 69120000;
            }
            else if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName()))
            {
                return 200;
            }
            else if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName()))
            {
                return 200;
            }
            else if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName()))
            {
                return 200;
            }
            else if (item == Items.STICK)
            {
                return 100;
            }
            else if (item != Items.BOW && item != Items.FISHING_ROD)
            {
                if (item == Items.SIGN)
                {
                    return 200;
                }
                else if (item == Items.COAL)
                {
                    return 1600;
                }
                else if (item == Items.LAVA_BUCKET)
                {
                    return 20000;
                }
                else if (item != Item.getItemFromBlock(Blocks.SAPLING) && item != Items.BOWL)
                {
                    if (item == Items.BLAZE_ROD)
                    {
                        return 2400;
                    }
                    else if (item instanceof ItemDoor && item != Items.IRON_DOOR)
                    {
                        return 200;
                    }
                    else
                    {
                        return item instanceof ItemBoat ? 400 : 0;
                    }
                }
                else
                {
                    return 100;
                }
            }
            else
            {
                return 300;
            }
		}
	}
	
	public static boolean isItemFuel(ItemStack fuel)
	{
		return getItemBurnTime(fuel) > 0;
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	@Override
	public void openInventory(EntityPlayer player) {}
	
	@Override
	public void closeInventory(EntityPlayer player) {}
	
	@Override 
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (index == 2) return false;
		else if (index != 1) return true;
		else 
		{
			return isItemFuel(stack);
		}
	}
	
	public String getGUID()
	{
		return "tutorial:macerator_furnace";
	}
	
	@Override
	public int getField(int id)
	{
		switch(id)
		{
		case 0:
			return this.burnTime;
		case 1:
			return this.currentBurnTime;
		case 2:
			return this.cookTime;
		case 3:
			return this.totalCookTime;
		default:
			return 0;
		}
	}
	
	@Override 
	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0:
			this.burnTime = value;
			break;
		case 1:
			this.currentBurnTime = value;
			break;
		case 2:
			this.cookTime = value;
			break;
		case 3:
			this.totalCookTime = value;
		}
	}
	
	@Override 
	public int getFieldCount()
	{
		return 4;
	}
	
	@Override
	public void clear()
	{
		this.inventory.clear();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
