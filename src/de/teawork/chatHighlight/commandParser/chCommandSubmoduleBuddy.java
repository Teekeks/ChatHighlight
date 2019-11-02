package de.teawork.chatHighlight.commandParser;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.teawork.chatHighlight.chPlayerConfigData;

public class chCommandSubmoduleBuddy extends chCommandSubmoduleBase {

	public chCommandSubmoduleBuddy(chCommandParser master,
			chCommandSubmoduleBase parent) {
		super("buddy", master, parent);
	}

	@Override
	public boolean parseSubmodule(CommandSender sender, String[] args) {
		if (!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED+"[ChatH] "+ChatColor.RESET+"Command must be performed by a player!");
			return true;
		}
		
		//Get Sender data
		chPlayerConfigData data = master.plugin.playerConfigData.get(((Player)sender).getUniqueId().toString());
		
		// without param: list all known aliases
		if ((args.length==0) || (args[0].equalsIgnoreCase("list")))
		{
			sender.sendMessage(ChatColor.GREEN+"=========================================");
			sender.sendMessage(ChatColor.GREEN+"Buddys:");
			for (String aUUID : data.buddy)
				sender.sendMessage(ChatColor.RED+Bukkit.getOfflinePlayer(UUID.fromString(aUUID)).getName());
			sender.sendMessage(ChatColor.GREEN+"=========================================");
		} else
		{
			//need arg 0..1
			if (args.length<2)
				return false;
			if (args[0].equalsIgnoreCase("add"))
			{
				OfflinePlayer p = Bukkit.getPlayerExact(args[1]);
				if (p == null)
					sender.sendMessage(ChatColor.RED+"[ChatH] Player \""+args[1]+"\" does not exist!");
				else
				{
					data.buddy.add(p.getUniqueId().toString());
					sender.sendMessage(ChatColor.RED+"[ChatH] "+ChatColor.RESET+"successfully add new buddy \""+args[1]+"\"");
				}
			} else if (args[0].equalsIgnoreCase("remove"))
			{
				@SuppressWarnings("deprecation")
				OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
				if (p == null)
					sender.sendMessage(ChatColor.RED+"[ChatH] Player \""+args[1]+"\" does not exist!");
				else
				{
					if (data.buddy.contains(p.getUniqueId().toString()))
					{
						sender.sendMessage(ChatColor.RED+"[ChatH] Player \""+args[1]+"\" is not your buddy!");
					} else
					{
						data.buddy.remove(p.getUniqueId().toString());
						sender.sendMessage(ChatColor.RED+"[ChatH] "+ChatColor.RESET+"successfully removed buddy \""+args[1]+"\"");
					}
				}
			}
		}		
		return true;
	}

	@Override
	public String helpMessage() {
		return "Add or Remove your Buddys";
	}

	@Override
	public void printSubmoduleHelp(CommandSender sender) {
		sender.sendMessage("/chath buddy list - Lists all known buddys");
		sender.sendMessage("/chath buddy add <name> - Adds <name> to the list of known buddys");
		sender.sendMessage("/chath buddy remove <name> - removes <name> from the list of known buddys");
		
	}


}
