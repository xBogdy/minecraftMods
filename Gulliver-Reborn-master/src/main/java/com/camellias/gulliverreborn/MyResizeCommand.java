package com.camellias.gulliverreborn;

import java.util.List;
import java.util.UUID;

import com.artemis.artemislib.util.attributes.ArtemisLibAttributes;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class MyResizeCommand extends CommandBase
{
	private final List<String> aliases = Lists.newArrayList(GulliverReborn.MODID, "mysize", "ms");
	private static UUID uuidHeight = UUID.fromString("5440b01a-974f-4495-bb9a-c7c87424bca4");
	private static UUID uuidWidth = UUID.fromString("3949d2ed-b6cc-4330-9c13-98777f48ea51");
	private static UUID uuidReach1 = UUID.fromString("854e0004-c218-406c-a9e2-590f1846d80b");
	private static UUID uuidReach2 = UUID.fromString("216080dc-22d3-4eff-a730-190ec0210d5c");
	private static UUID uuidHealth = UUID.fromString("3b901d47-2d30-495c-be45-f0091c0f6fb2");
	private static UUID uuidStrength = UUID.fromString("558f55be-b277-4091-ae9b-056c7bc96e84");
	private static UUID uuidSpeed = UUID.fromString("f2fb5cda-3fbe-4509-a0af-4fc994e6aeca");
	
	@Override
	public String getName() 
	{
		return "mysize";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "gulliverreborn.commands.mysize.usage";
	}
	
	@Override
	public List<String> getAliases()
	{
		return aliases;
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return true;
	}
	
	@Override
	public int getRequiredPermissionLevel()
	{
		return 0;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if(args.length < 1) return;
		
		String s = args[0];
		float size;

		try
		{
			size = Float.parseFloat(s);
		}
		catch(NumberFormatException e)
		{
			sender.sendMessage(new TextComponentString(TextFormatting.RED + "Size Invalid myres"));
			sender.sendMessage(new TextComponentString(TextFormatting.RED + "Size Invalid"));
			return;
		}

		if(sender instanceof EntityPlayer)
		{
//			if(((EntityPlayer) sender).getHealth()>=0){

				//TODO: viata se salveaza ultima si dupa inca o comanda o arata pe cea corecta
				//TODO: fullhp == 20.0 (important float)
				//TODO:PROBABIL ACI IF GETHEALTH==18 size == 0.9 etc etc
//				sender.sendMessage(new TextComponentString(TextFormatting.RED + "Size Invalid myres"));
//				sender.sendMessage(new TextComponentString(TextFormatting.RED + "Size Invalid"));
//				float jb = ((EntityPlayer) sender).getHealth();
//				String loll= " "+jb+" ";
//				sender.sendMessage(new TextComponentString(TextFormatting.RED + loll));
//			}

			size = MathHelper.clamp(size, 0.125F, Config.MAX_SIZE);
			Multimap<String, AttributeModifier> attributes = HashMultimap.create();
			Multimap<String, AttributeModifier> removeableAttributes = HashMultimap.create();
			Multimap<String, AttributeModifier> removeableAttributes2 = HashMultimap.create();

			attributes.put(ArtemisLibAttributes.ENTITY_HEIGHT.getName(), new AttributeModifier(uuidHeight, "Player Height", size - 1, 2));
			attributes.put(ArtemisLibAttributes.ENTITY_WIDTH.getName(), new AttributeModifier(uuidWidth, "Player Width", MathHelper.clamp(size - 1, 0.4 - 1, Config.MAX_SIZE), 2));

			if(Config.SPEED_MODIFIER) attributes.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(uuidSpeed, "Player Speed", (size - 1) / 2, 2));
			if(Config.REACH_MODIFIER) removeableAttributes.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(uuidReach1, "Player Reach 1", size - 1, 2));
			if(Config.REACH_MODIFIER) removeableAttributes2.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(uuidReach2, "Player Reach 2", -MathHelper.clamp(size - 1, 0.33, Double.MAX_VALUE), 2));
			if(Config.STRENGTH_MODIFIER) attributes.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(uuidStrength, "Player Strength", size - 1, 0));
			if(Config.HEALTH_MODIFIER) attributes.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(uuidHealth, "Player Health", (size - 1) * Config.HEALTH_MULTIPLIER, 2));

			if(size > 1)
			{
				((EntityPlayer) sender).getAttributeMap().applyAttributeModifiers(removeableAttributes);
			}
			else
			{
				((EntityPlayer) sender).getAttributeMap().removeAttributeModifiers(removeableAttributes);
			}

			if(size < 1)
			{
				((EntityPlayer) sender).getAttributeMap().applyAttributeModifiers(removeableAttributes2);
			}
			else
			{
				((EntityPlayer) sender).getAttributeMap().removeAttributeModifiers(removeableAttributes2);
			}

			((EntityPlayer) sender).getAttributeMap().applyAttributeModifiers(attributes);
			((EntityPlayer) sender).setHealth(((EntityPlayer) sender).getMaxHealth());

			if(sender instanceof EntityPlayer) GulliverReborn.LOGGER.info(((EntityPlayer) sender).getDisplayNameString() + " set their size to " + size);
		}
	}



	// test
	public static void execute2(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if(args.length < 1) return;

		String s = args[0];
		float size;

		try
		{
			size = Float.parseFloat(s);
		}
		catch(NumberFormatException e)
		{
			sender.sendMessage(new TextComponentString(TextFormatting.RED + "Size Invalid myres"));
			sender.sendMessage(new TextComponentString(TextFormatting.RED + "Size Invalid"));
			return;
		}

		if(sender instanceof EntityPlayer)
		{
			if(((EntityPlayer) sender).getHealth()>=0){

				//TODO: viata se salveaza ultima si dupa inca o comanda o arata pe cea corecta
				//TODO: fullhp == 20.0 (important float)
				//TODO:PROBABIL ACI IF GETHEALTH==18 size == 0.9 etc etc
				sender.sendMessage(new TextComponentString(TextFormatting.RED + "Size Invalid myres"));
				sender.sendMessage(new TextComponentString(TextFormatting.RED + "Size Invalid"));
				float jb = ((EntityPlayer) sender).getHealth();
				String loll= " "+jb+" ";
				sender.sendMessage(new TextComponentString(TextFormatting.RED + loll));


			}

			size = MathHelper.clamp(size, 0.125F, Config.MAX_SIZE);
			Multimap<String, AttributeModifier> attributes = HashMultimap.create();
			Multimap<String, AttributeModifier> removeableAttributes = HashMultimap.create();
			Multimap<String, AttributeModifier> removeableAttributes2 = HashMultimap.create();

			attributes.put(ArtemisLibAttributes.ENTITY_HEIGHT.getName(), new AttributeModifier(uuidHeight, "Player Height", size - 1, 2));
			attributes.put(ArtemisLibAttributes.ENTITY_WIDTH.getName(), new AttributeModifier(uuidWidth, "Player Width", MathHelper.clamp(size - 1, 0.4 - 1, Config.MAX_SIZE), 2));

			if(Config.SPEED_MODIFIER) attributes.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(uuidSpeed, "Player Speed", (size - 1) / 2, 2));
			if(Config.REACH_MODIFIER) removeableAttributes.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(uuidReach1, "Player Reach 1", size - 1, 2));
			if(Config.REACH_MODIFIER) removeableAttributes2.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(uuidReach2, "Player Reach 2", -MathHelper.clamp(size - 1, 0.33, Double.MAX_VALUE), 2));
			if(Config.STRENGTH_MODIFIER) attributes.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(uuidStrength, "Player Strength", size - 1, 0));
			if(Config.HEALTH_MODIFIER) attributes.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(uuidHealth, "Player Health", (size - 1) * Config.HEALTH_MULTIPLIER, 2));

			if(size > 1)
			{
				((EntityPlayer) sender).getAttributeMap().applyAttributeModifiers(removeableAttributes);
			}
			else
			{
				((EntityPlayer) sender).getAttributeMap().removeAttributeModifiers(removeableAttributes);
			}

			if(size < 1)
			{
				((EntityPlayer) sender).getAttributeMap().applyAttributeModifiers(removeableAttributes2);
			}
			else
			{
				((EntityPlayer) sender).getAttributeMap().removeAttributeModifiers(removeableAttributes2);
			}

			((EntityPlayer) sender).getAttributeMap().applyAttributeModifiers(attributes);
			((EntityPlayer) sender).setHealth(((EntityPlayer) sender).getMaxHealth());

			if(sender instanceof EntityPlayer) GulliverReborn.LOGGER.info(((EntityPlayer) sender).getDisplayNameString() + " set their size to " + size);
		}
	}

	//test
	public static void lol(){

	}

}
