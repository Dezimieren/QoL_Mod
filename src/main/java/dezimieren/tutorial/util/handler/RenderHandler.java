package dezimieren.tutorial.util.handler;

import dezimieren.tutorial.blocks.tnt.smarttnt.RenderSmartTNTPrimed;
import dezimieren.tutorial.blocks.tnt.smarttnt.entitySmartTNT;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {

	public static void registerEntityRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(entitySmartTNT.class, new IRenderFactory<entitySmartTNT>()
		{

			@Override
			public Render<? super entitySmartTNT> createRenderFor(RenderManager manager) {
				// TODO Auto-generated method stub
				return new RenderSmartTNTPrimed(manager);
			}
			
		});
	}
	
}
