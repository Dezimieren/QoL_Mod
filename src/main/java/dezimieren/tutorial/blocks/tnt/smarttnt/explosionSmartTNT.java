package dezimieren.tutorial.blocks.tnt.smarttnt;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class explosionSmartTNT implements CustomExplosion
{
	public boolean isFlaming;
	public boolean DropsBlocks;
    /** whether or not the explosion sets fire to blocks around it */
    //private final boolean causesFire;
    public boolean isSmoking;
    /** whether or not this explosion spawns smoke particles */
    //private final boolean damagesTerrain;
    private final Random random;
    private final World world;
    private final double x;
    private final double y;
    private final double z;
    private final Entity exploder;
    private float size;
    private World worldObj;
    /** A list of ChunkPositions of blocks affected by this explosion */
    private final List<BlockPos> affectedBlockPositions;
    /** Maps players to the knockback vector applied by the explosion, to send to the client */
    private final Map<EntityPlayer, Vec3d> playerKnockbackMap;
    private final Explosion TNT;
	private Vec3d position;


    public explosionSmartTNT(World worldIn, Entity entityIn, double x, double y, double z, float size)
    {
    	this.isFlaming = false;
    	this.isSmoking = true;
    	this.DropsBlocks = true;
        this.random = new Random();
        this.affectedBlockPositions = Lists.<BlockPos>newArrayList();
        this.playerKnockbackMap = Maps.<EntityPlayer, Vec3d>newHashMap();
        this.world = worldIn;
        this.exploder = entityIn;
        this.size = size;
        this.x = x;
        this.y = y;
        this.z = z;
        //this.causesFire = flaming;
        //this.damagesTerrain = damagesTerrain;
        this.TNT = new Explosion(worldObj, exploder, x, y, z, size, affectedBlockPositions);
        this.position = new Vec3d(this.x, this.y, this.z);
    }

    /**
     * Does the first part of the explosion (destroy blocks)
     */
    public void doExplosionA()
    {
        float f = size;
        HashSet hashset = new HashSet();

        for (int j = 0; j < 15; ++j)
        {
            for (int k = 0; k < 15; ++k)
            {
                for (int l = 0; l < 15; ++l)
                {
                    if (j == 0 || j == 15 - 1|| k == 0 || k == 15 - 1 || l == 0 || l == 15 -1)
                    {
                        double d0 = (double)((float)j / (15.0F -1) * 2.0F - 1.0F);
                        double d1 = (double)((float)k / (15.0F -1) * 2.0F - 1.0F);
                        double d2 = (double)((float)l / (15.0F -1) * 2.0F - 1.0F);
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 = d0 / d3;
                        d1 = d1 / d3;
                        d2 = d2 / d3;
                        float f1 = this.size * (0.3F + this.world.rand.nextFloat() * 0.2F);
                        double d4 = this.x;
                        double d6 = this.y;
                        double d8 = this.z;

                        for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.1F)
                        {
                            BlockPos blockpos = new BlockPos(d4, d6, d8);
                            IBlockState iblockstate = this.world.getBlockState(blockpos);
                            
                 
                            final float f4 = iblockstate.getBlock().getExplosionResistance(worldObj, blockpos, exploder, TNT);
                            float f5;
                            
                            if (f4 < 1000000.0F)
                            {
                            	f5 = 0.0F;
                            }
                            else 
                            {
                            	f5 = 200.0F;
                            }
                            f1 -= (f4 + 0.3F) * f2;
//                            if (iblockstate.getMaterial() != Material.AIR)
//                            {
//                            	f1 -= (f4 + 0.3F) * f2;
//                            }
                          
                            if (f1 > 0.0F)
                            {
                                hashset.add(blockpos);
                            }

                            d4 += d0 * f2;
                            d6 += d1 * f2;
                            d8 += d2 * f2;
                        }
                    }
                }
            }
        }

        this.affectedBlockPositions.addAll(hashset);
        size *= 1.5F;
        int k1 = MathHelper.floor(this.x - size - 1.0D);
        int l1 = MathHelper.floor(this.x + size + 1.0D);
        int i2 = MathHelper.floor(this.y - size - 1.0D);
        int i1 = MathHelper.floor(this.y + size + 1.0D);
        int j2 = MathHelper.floor(this.z - size - 1.0D);
        int j1 = MathHelper.floor(this.z + size + 1.0D);
       
        size = f;
    }

    /**
     * Does the second part of the explosion (sound, particles, drop spawn)
     */
    public void doExplosionB(boolean spawnParticles)
    {
    	//if ((size >= 2.0F) && (isSmoking))
    	
        this.world.playSound((EntityPlayer)null, this.x, this.y, this.z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);

        if (this.size >= 2.0F && this.isSmoking)
        {
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
        }
        else
        {
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
        }

        if (this.isSmoking)
        {
            for (BlockPos blockpos : this.affectedBlockPositions)
            {
                IBlockState iblockstate = this.world.getBlockState(blockpos);
                Block block = iblockstate.getBlock();

                if (spawnParticles)
                {
                    double d0 = (double)((float)blockpos.getX() + this.world.rand.nextFloat());
                    double d1 = (double)((float)blockpos.getY() + this.world.rand.nextFloat());
                    double d2 = (double)((float)blockpos.getZ() + this.world.rand.nextFloat());
                    double d3 = d0 - this.x;
                    double d4 = d1 - this.y;
                    double d5 = d2 - this.z;
                    double d6 = (double)MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                    d3 = d3 / d6;
                    d4 = d4 / d6;
                    d5 = d5 / d6;
                    double d7 = 0.5D / (d6 / (double)this.size + 0.1D);
                    d7 = d7 * (double)(this.world.rand.nextFloat() * this.world.rand.nextFloat() + 0.3F);
                    d3 = d3 * d7;
                    d4 = d4 * d7;
                    d5 = d5 * d7;
                    this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + this.x) / 2.0D, (d1 + this.y) / 2.0D, (d2 + this.z) / 2.0D, d3, d4, d5, new int[0]);
                    this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
                }
                
                
                
                if (block != null)
                {
                	block.onBlockExploded(this.world, blockpos,TNT);
                	
                    if (!this.DropsBlocks || !block.canDropFromExplosion(TNT))
                    {
                    	continue;
                    }
                }
            }
        }

        if (this.isFlaming)
        {
            for (BlockPos blockpos1 : this.affectedBlockPositions)
            {
                if (this.world.getBlockState(blockpos1).getMaterial() == Material.AIR && this.world.getBlockState(blockpos1.down()).isFullBlock() && this.random.nextInt(3) == 0)
                {
                    this.world.setBlockState(blockpos1, Blocks.FIRE.getDefaultState());
                }
            }
        }
    }

    public Map<EntityPlayer, Vec3d> getPlayerKnockbackMap()
    {
        return this.playerKnockbackMap;
    }

    /**
     * Returns either the entity that placed the explosive block, the entity that caused the explosion or null.
     */
    @Nullable
    public EntityLivingBase getExplosivePlacedBy()
    {
        if (this.exploder == null)
        {
            return null;
        }
        else if (this.exploder instanceof entitySmartTNT)
        {
            return ((entitySmartTNT)this.exploder).getTntPlacedBy();
        }
        else
        {
            return this.exploder instanceof EntityLivingBase ? (EntityLivingBase)this.exploder : null;
        }
    }

    public void clearAffectedBlockPositions()
    {
        this.affectedBlockPositions.clear();
    }

    public List<BlockPos> getAffectedBlockPositions()
    {
        return this.affectedBlockPositions;
    }

    public Vec3d getPosition(){ return this.position; }

	@Override
	public boolean simulateExplosion() {
		// TODO Auto-generated method stub
		return true;
	}
}