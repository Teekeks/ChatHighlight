package de.teawork.chatHighlight.commandParser;

import java.util.Map.Entry;

import org.bukkit.command.CommandSender;

import de.teawork.chatHighlight.var.chVarColor;
import net.md_5.bungee.api.ChatColor;

public class chCommandSubmoduleColors extends chCommandSubmoduleBase {

	public chCommandSubmoduleColors(chCommandParser master,
			chCommandSubmoduleBase parent) {
		super("colors", master, parent);
	}

	@Override
	public boolean parseSubmodule(CommandSender sender, String[] args) {
		sender.sendMessage(ChatColor.GREEN+"=========================================");
		sender.sendMessage(ChatColor.GREEN+"Available Colors:");
		for (Entry<ChatColor,String> e : chVarColor.colorMap.entrySet())
			sender.sendMessage(e.getValue()+" : "+e.getKey()+e.getValue());
		return true;
	}

	@Override
	public String helpMessage() {
		return "Lists all available colors used in param";
	}

	@Override
	public void printSubmoduleHelp(CommandSender sender) {
		sender.sendMessage("Returns a list of all available colors you can use for customization in param");
		sender.sendMessage("Usage: /chath colors");
	}

}
