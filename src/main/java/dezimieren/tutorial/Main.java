package dezimieren.tutorial;

import dezimieren.tutorial.init.ModCrafting;
import dezimieren.tutorial.proxy.CommonProxy;
import dezimieren.tutorial.util.Reference;
import dezimieren.tutorial.util.handler.RegistryHandler;
import dezimieren.tutorial.util.handler.RenderHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.Name, version = Reference.VERSION)
public class Main {

	
	@Instance 
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		//proxy.init();
		RenderHandler.registerEntityRenders();
		RegistryHandler.initRegisties();
		ModCrafting.register();
	}
	
	@EventHandler
	public static void Postinit(FMLPostInitializationEvent event)
	{
		
	}
}
