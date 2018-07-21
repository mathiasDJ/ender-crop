package io.github.drmanganese.endercrop.handler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import io.github.drmanganese.endercrop.HoeHelper;
import io.github.drmanganese.endercrop.configuration.EnderCropConfiguration;
import io.github.drmanganese.endercrop.init.ModBlocks;

public class UseHoeEventHandler {

    @SubscribeEvent
    public void useHoe(UseHoeEvent event) {
        if (event.getResult() != Event.Result.DEFAULT || event.isCanceled()) {
            return;
        }

        final EntityPlayer player = event.getEntityPlayer();
        final World world = event.getWorld();
        final BlockPos pos = event.getPos();

        if (world.getBlockState(pos).getBlock() == Blocks.END_STONE) {
            if (HoeHelper.canHoeEndstone(event.getCurrent()) || player.isCreative()) {
                world.setBlockState(pos, ModBlocks.TILLED_END_STONE.getDefaultState());
                event.setResult(Event.Result.ALLOW);
            } else if (!world.isRemote && !Loader.isModLoaded("waila") && !Loader.isModLoaded("theoneprobe")){
                player.sendStatusMessage(new TextComponentTranslation("endercrop.alert.hoe").setStyle(new Style().setItalic(true).setColor(TextFormatting.GRAY)), true);
            }
        }
    }
}