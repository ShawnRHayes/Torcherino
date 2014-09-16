package moze_intel.torcherino.gameObjs;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SpeedTorchBlock extends BlockTorch implements ITileEntityProvider
{
	public SpeedTorchBlock() 
	{
		this.setBlockName("speed_torch");
		this.setLightLevel(0.75f);
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) 
	{
		if (!world.isRemote)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			
			if (tile != null && tile instanceof SpeedTorchTile)
			{
				((SpeedTorchTile) tile).setActive(!world.isBlockIndirectlyGettingPowered(x, y, z));
			}
		}
		
		super.onBlockAdded(world, x, y, z);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) 
	{
		if (!world.isRemote)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			
			if (tile != null && tile instanceof SpeedTorchTile)
			{
				((SpeedTorchTile) tile).setActive(!world.isBlockIndirectlyGettingPowered(x, y, z));
			}
		}
		
		super.onNeighborBlockChange(world, x, y, z, block);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par1, float par2, float par3, float par4)
    {
		if (!world.isRemote)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			
			if (tile == null || !(tile instanceof SpeedTorchTile))
			{
				return false;
			}
			
			SpeedTorchTile torch = (SpeedTorchTile) tile;
			
			if (torch.changeMode(player.isSneaking()))
			{
				player.addChatMessage(new ChatComponentText("Changed mode to: " + torch.getModeDescription()));
			}
		}
		
        return false;
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new SpeedTorchTile();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) 
	{
		super.randomDisplayTick(world, x, y, z, rand);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) 
	{
		this.blockIcon = register.registerIcon("torcherino:speed_torch");
	}
}
