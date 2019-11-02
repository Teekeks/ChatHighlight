package de.teawork.chatHighlight;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import java.util.UUID;

public class chHelper {

	
	public static boolean isAfk(Plugin p, UUID name)
	{
		//Use reflection magic!
		Plugin afk = p.getServer().getPluginManager().getPlugin("Zw2Afk");
		Class<?> objClass = afk.getClass();
		try {
			Field field = objClass.getField("AFK_LIST");
			@SuppressWarnings("rawtypes")
			ArrayList afkList = (ArrayList)field.get(afk);
			return afkList.contains(name);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}	
		return false;
	}
	
	public static boolean sendToDiscordSRV(Plugin p, Player player, String message) {
		Plugin discord = p.getServer().getPluginManager().getPlugin("DiscordSRV");
		Class<?> objClass = discord.getClass();
		try {
			Method method = objClass.getMethod("processChatMessage", Player.class, String.class, String.class, boolean.class);
			method.invoke(discord, player,  message, "global", false);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
