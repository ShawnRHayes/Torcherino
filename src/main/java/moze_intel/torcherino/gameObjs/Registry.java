package moze_intel.torcherino.gameObjs;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Registry 
{
	public static Block speedTorch = new SpeedTorchBlock();
	
	public static void registerObjects()
	{
		GameRegistry.registerBlock(speedTorch, speedTorch.getUnlocalizedName());
		GameRegistry.registerTileEntity(SpeedTorchTile.class, "speed_torch_tile");
		
		GameRegistry.addRecipe(new ItemStack(speedTorch), new Object[] {"XCX", "CTC", "XCX", 'C', Items.clock, 'T', Blocks.torch});
	}
}
