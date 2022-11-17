package net.kettlemc.titan.content.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kettlemc.titan.config.TextUtils;
import net.kettlemc.titan.config.TitanConstants;
import net.kettlemc.titan.content.TitanCreativeTabs;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class TitanSwordItem extends ItemSword {

    private ToolMaterial material;
    private boolean isGiant;
    private float attackDamage;

    public TitanSwordItem(String registry, ToolMaterial material) {
        super(material);
        this.material = material;
        this.isGiant = (material == TitanToolMaterial.TITAN_GIANT || material == TitanToolMaterial.CITRINE_GIANT || material == TitanToolMaterial.DIAMOND_GIANT);
        this.attackDamage = material.getAttackDamage();
        this.setRegistryName(new ResourceLocation(TitanConstants.MOD_ID, registry));
        this.setTranslationKey(registry);
        this.setCreativeTab(TitanCreativeTabs.TITAN_MOD_TAB);
        this.setHasSubtypes(false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, @Nullable World world, List<String> stringList, ITooltipFlag tooltipFlag) {
        if (this.material == TitanToolMaterial.CITRINE || this.material == TitanToolMaterial.CITRINE_GIANT) {
            stringList.add(TextUtils.color("&225% " + I18n.format("effect.poison")));
        } else if (this.material == TitanToolMaterial.NETHER || this.material == TitanToolMaterial.NETHER_GIANT) {
            stringList.add(TextUtils.color("&c25% " + I18n.format("tile.fire.name")));
        }

    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot p_getItemAttributeModifiers_1_) {
        Multimap<String, AttributeModifier> multimap = isGiant ? HashMultimap.create() : super.getItemAttributeModifiers(p_getItemAttributeModifiers_1_);
        if (p_getItemAttributeModifiers_1_ == EntityEquipmentSlot.MAINHAND && isGiant) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage + 3.0F, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.9000000953674316D, 0));
        }

        return multimap;
    }

}
