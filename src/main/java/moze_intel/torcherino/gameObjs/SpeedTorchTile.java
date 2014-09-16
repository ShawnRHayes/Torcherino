package moze_intel.torcherino.gameObjs;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class SpeedTorchTile extends TileEntity
{
	private final int[] AOE = new int[] {1, 3, 5, 7, 9};
	private final String[] DESCR = new String[] {"1x1x1", "3x3x3", "5x5x5", "7x7x7", "9x9x9"};
	private final int UPDATE_BONUS = 10;
	private boolean isActive;
	private byte mode;
	private byte cachedMode;
	private AxisAlignedBB bBox;
	private Random rand;
	
	public SpeedTorchTile()
	{
		isActive = true;
		cachedMode = -1;
		rand = new Random();
	}
	
	@Override
	public void updateEntity() 
	{
		if (this.worldObj.isRemote)
		{
			return;
		}
		
		if (cachedMode != mode)
		{
			bBox = AxisAlignedBB.getBoundingBox(this.xCoord - AOE[mode], this.yCoord - AOE[mode], this.zCoord - AOE[mode], this.xCoord + AOE[mode], this.yCoord + AOE[mode], this.zCoord + AOE[mode]);
			cachedMode = mode;
		}
		
		if (!isActive)
		{
			return;
		}
		
		for (int x = (int) bBox.minX; x <= bBox.maxX; x++)
			for (int y = (int) bBox.minY; y <= bBox.maxY; y++)
				for (int z = (int) bBox.minZ; z <= bBox.maxZ; z++)
				{
					Block block = this.getWorldObj().getBlock(x, y, z);
					
					if (block == Blocks.air)
					{
						continue;
					}
					
					if (block.getTickRandomly())
					{
						for (int i = 0; i < UPDATE_BONUS; i++)
						{
							block.updateTick(this.worldObj, x, y, z, rand);
						}
					}
					
					TileEntity tile = this.worldObj.getTileEntity(x, y, z);
					
					if (tile != null && !(tile instanceof SpeedTorchTile))
					{
						for (int i = 0; i < UPDATE_BONUS; i++)
						{
							tile.updateEntity();
						}
					}
				}
	}
	
	public boolean changeMode(boolean isPlayerSneaking)
	{
		if (mode == 4 && !isPlayerSneaking)
		{
			return false;
		}
		
		if (mode == 0 && isPlayerSneaking)
		{
			return false;
		}
		
		mode += isPlayerSneaking ? -1 : 1;
		return true;
	}
	
	public String getModeDescription()
	{
		return DESCR[mode];
	}
	
	public void setMode(byte mode)
	{
		this.mode = mode;
	}
	
	public byte getMode()
	{
		return mode;
	}
	
	public void setActive(boolean value)
	{
		this.isActive = value;
	}
	
	public boolean getIsActive()
	{
		return this.isActive;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) 
	{
		super.writeToNBT(nbt);
		nbt.setByte("Mode", mode);
		nbt.setBoolean("IsActive", isActive);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.mode = nbt.getByte("Mode");
		this.isActive = nbt.getBoolean("IsActive");
	}
}
