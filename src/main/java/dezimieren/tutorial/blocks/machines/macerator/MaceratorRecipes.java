package dezimieren.tutorial.blocks.machines.macerator;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MaceratorRecipes {
	
	private static final MaceratorRecipes INSTANCE = new MaceratorRecipes();
	//private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
    private final Map<ItemStack, ItemStack> smeltingList = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	public static MaceratorRecipes getInstance() 
	{
		return INSTANCE;
	}
	
	private MaceratorRecipes()
	{
		this.addMaceratorRecipe(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.GRAVEL), 0.01F);
		this.addMaceratorRecipe(new ItemStack(Blocks.STONE), new ItemStack(Blocks.COBBLESTONE), 0.05F);
		this.addMaceratorRecipe(new ItemStack(Blocks.STONEBRICK), new ItemStack(Blocks.COBBLESTONE), 0.01F);
		this.addMaceratorRecipe(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.SAND), 0.01F);
//	    this.addSmeltingRecipeForBlock(Blocks.IRON_ORE, new ItemStack(Items.IRON_INGOT), 0.7F);
	}
	
	public void addSmeltingRecipeForBlock(Block input, ItemStack stack, float experience)
	{
		this.addSmelting(Item.getItemFromBlock(input), stack, experience);
	}
	
	public void addSmelting(Item input, ItemStack stack, float experience)
	{
		this.addMaceratorRecipe(new ItemStack(input, 1, 32767), stack, experience);
	}
	
	public void addMaceratorRecipe(ItemStack input1, ItemStack result, float experience)
	{
		//if (getMaceratorResult(input1, input2) != ItemStack.EMPTY) return;
		if (getMaceratorResult(input1) != ItemStack.EMPTY) return;
		//this.smeltingList.put(input1, input2, result);
		this.smeltingList.put(input1, result);
		this.experienceList.put(result, Float.valueOf(experience));
	}
	
	public ItemStack getMaceratorResult(ItemStack input1) //removed input2 from tutorial
	{
//		for (Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.smeltingList.columnMap().entrySet())
//		{
//			if (this.compareItemStacks(input1, (ItemStack)entry.getKey()))
//			{
//				for(Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) {
//					if (this.compareItemStacks(input2, (ItemStack)ent.getKey()))
//					{
//						return (ItemStack)ent.getValue();
//					}
//				}
//			}
//		}
//		
		for (Entry<ItemStack, ItemStack> entry : this.smeltingList.entrySet())
        {
            if (this.compareItemStacks(input1, entry.getKey()))
            {
                return entry.getValue();
            }
        }

        return ItemStack.EMPTY;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
//	public Table<ItemStack, ItemStack, ItemStack> getDualSmeltingList()
//	{
//		return this.smeltingList;
//	}
	public Map<ItemStack, ItemStack> getSmeltingList()
	{
		return this.smeltingList;
	}
	
	public float getMaceratorExperience(ItemStack stack)
	{
		float ret = stack.getItem().getSmeltingExperience(stack);
		if (ret != -1) return ret;
		
		for (Entry<ItemStack, Float> entry : this.experienceList.entrySet())
		{
			if (this.compareItemStacks(stack, entry.getKey()))
			{
				return ((Float)entry.getValue()).floatValue();
			}
		}
		return 0.0F;
	}

}
