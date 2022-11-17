package net.kettlemc.titan;

import net.kettlemc.titan.content.block.TitanBlocks;
import net.kettlemc.titan.content.tileentity.IronFurnaceRecipes;
import net.kettlemc.titan.content.tileentity.TitanTileEntityFurnace;
import net.kettlemc.titan.proxy.Proxy;
import net.kettlemc.titan.content.item.TitanItems;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.kettlemc.titan.config.TitanConstants.*;

@Mod(modid = MOD_ID, name = NAME, version = VERSION)
public class TitanMod {

    @Mod.Instance
    private static TitanMod instance;

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    private static Proxy proxy;

    public static TitanMod getInstance() {
        return instance;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
        MinecraftForge.EVENT_BUS.register(TitanItems.class);
        MinecraftForge.EVENT_BUS.register(TitanBlocks.class);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
        IronFurnaceRecipes.init();
        GameRegistry.registerTileEntity(TitanTileEntityFurnace.class, new ResourceLocation(MOD_ID, "titan_furnace"));
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

}
