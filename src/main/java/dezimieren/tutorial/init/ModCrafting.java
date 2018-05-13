package dezimieren.tutorial.init;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting {

	public static void register() {
		GameRegistry.addShapedRecipe(
				new ResourceLocation("String"), 
				new ResourceLocation("recipes"), 
				new ItemStack(ModBlocks.GlOWSTONE_LAMP_BLOCK, 4), 
				new Object[] {" 1 ", "345", " 7 ",
						Character.valueOf('1'), Blocks.GLOWSTONE, 
						Character.valueOf('3'), Blocks.GLOWSTONE, Character.valueOf('4'), Blocks.TORCH, Character.valueOf('5'), Blocks.GLOWSTONE,
						Character.valueOf('7'), Blocks.GLOWSTONE});
		
		GameRegistry.addSmelting(Blocks.OBSIDIAN, new ItemStack(ModItems.OBSIDIAN_SHARD), 0.5F);
		
	}
	
	
}
