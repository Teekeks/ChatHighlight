package de.teawork.chatHighlight.commandParser;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.teawork.chatHighlight.chPlayerConfigData;

public class chCommandSubmoduleCopy extends chCommandSubmoduleBase {

	public chCommandSubmoduleCopy(chCommandParser master,
			chCommandSubmoduleBase parent) {
		super("copy", master, parent);
		
	}

	@Override
	public boolean parseSubmodule(CommandSender sender, String[] args) {
		if (!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED+"[ChatH] "+ChatColor.RESET+"Command must be performed by a player!");
			return true;
		}
		Player player = (Player)sender;
		if (args.length>=1)
		{
			Player pc = Bukkit.getPlayerExact(args[0]);
			if (pc==null)
			{
				//player does not exist/is not online
				sender.sendMessage(ChatColor.RED+"[ChatH] Player \""+args[0]+"\" does not exist/is not online!");
				return false;
			}
			chPlayerConfigData data = master.plugin.playerConfigData.get(player.getUniqueId().toString());
			if (!data.copyConfirm)
			{
				player.sendMessage(ChatColor.RED+"[ChatH] Please confirm by retyping the same command.");
				player.sendMessage(ChatColor.RED+"[ChatH] Please note, that this will OVERRIDE ALL YOUR SETTINGS!");
				data.copyConfirm=true;
				return true;
			}
			data.copyConfirm=false;
			data.paramList= master.plugin.playerConfigData.get(pc.getUniqueId().toString()).paramList;
			player.sendMessage(ChatColor.RED+"[ChatH] You just copied the config from "+args[0]+"!");
			pc.sendMessage(ChatColor.RED+"[ChatH] "+player.getName()+" just copied your config!");
		}
		return true;
	}

	@Override
	public String helpMessage() {
		return "Copy the settings from a nother player!";
	}

	@Override
	public void printSubmoduleHelp(CommandSender sender) {
		sender.sendMessage("Copy the settings from another player.");
		sender.sendMessage("Does not copy alias and buddy list.");
		sender.sendMessage("Usage: /chath copy <name>");

	}

}
