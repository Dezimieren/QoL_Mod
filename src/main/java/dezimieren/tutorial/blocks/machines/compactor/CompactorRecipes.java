package dezimieren.tutorial.blocks.machines.compactor;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import dezimieren.tutorial.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CompactorRecipes {
	
	private static final CompactorRecipes INSTANCE = new CompactorRecipes();
	//private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
    private final Map<ItemStack, ItemStack> smeltingList = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	public static CompactorRecipes getInstance() 
	{
		return INSTANCE;
	}
	
	private CompactorRecipes()
	{
		this.addCompactorRecipe(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.COBBLESTONE), 0.1F);
		this.addCompactorRecipe(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.STONE), 0.1F);
		this.addCompactorRecipe(new ItemStack(Blocks.DIRT), new ItemStack(Blocks.GRAVEL), 0.1F);
		this.addCompactorRecipe(new ItemStack(Blocks.CLAY), new ItemStack(Blocks.DIRT), 0.1F);
		this.addCompactorRecipe(new ItemStack(Blocks.SAND), new ItemStack(Blocks.CLAY), 0.1F);
		this.addCompactorRecipe(new ItemStack(ModItems.OBSIDIAN_SHARD), new ItemStack(Blocks.OBSIDIAN), 1.0F);
//	    this.addSmeltingRecipeForBlock(Blocks.IRON_ORE, new ItemStack(Items.IRON_INGOT), 0.7F);
	}
	
	public void addSmeltingRecipeForBlock(Block input, ItemStack stack, float experience)
	{
		this.addSmelting(Item.getItemFromBlock(input), stack, experience);
	}
	
	public void addSmelting(Item input, ItemStack stack, float experience)
	{
		this.addCompactorRecipe(new ItemStack(input, 1, 32767), stack, experience);
	}
	
	public void addCompactorRecipe(ItemStack input1, ItemStack result, float experience)
	{
		//if (getMaceratorResult(input1, input2) != ItemStack.EMPTY) return;
		if (getCompactorResult(input1) != ItemStack.EMPTY) return;
		//this.smeltingList.put(input1, input2, result);
		this.smeltingList.put(input1, result);
		this.experienceList.put(result, Float.valueOf(experience));
	}
	
	public ItemStack getCompactorResult(ItemStack input1) //removed input2 from tutorial
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
	
	public float getCompactorExperience(ItemStack stack)
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
