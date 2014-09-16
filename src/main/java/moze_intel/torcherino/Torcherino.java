package moze_intel.torcherino;

import moze_intel.torcherino.gameObjs.Registry;
import moze_intel.torcherino.proxies.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(name = Torcherino.NAME, modid = Torcherino.ID, version = Torcherino.VERSION)
public class Torcherino 
{
	public static final String NAME = "Torcherino";
	public static final String ID = "Torcherino";
	public static final String VERSION = "0.1c";
	
	@Instance(ID)
	public static Torcherino instance;
    
    @SidedProxy(clientSide="moze_intel.torcherino.proxies.ClientProxy", serverSide="moze_intel.torcherino.proxies.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Registry.registerObjects();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {}
}
