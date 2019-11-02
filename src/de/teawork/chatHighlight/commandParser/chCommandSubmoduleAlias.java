package de.teawork.chatHighlight.commandParser;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.teawork.chatHighlight.chPlayerConfigData;
import net.md_5.bungee.api.ChatColor;

public class chCommandSubmoduleAlias extends chCommandSubmoduleBase {

	public chCommandSubmoduleAlias(chCommandParser master,
			chCommandSubmoduleBase parent) {
		super("alias", master, parent);
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
			if (args.length<=1)
			{
				sender.sendMessage(ChatColor.GREEN+"=========================================");
				sender.sendMessage(ChatColor.GREEN+"Known Aliases:");
				for (String a : data.alias)
					sender.sendMessage(ChatColor.RED+a);
				sender.sendMessage(ChatColor.GREEN+"=========================================");
			} else
			{
				//List Alias of other player
				@SuppressWarnings("deprecation")
				OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
				if (p==null)
				{
					//Player never joined the Server
					sender.sendMessage(ChatColor.RED+"[ChatH] Player \""+args[1]+"\" does not exist!");
				} else
				{
					chPlayerConfigData otherData = master.plugin.loadData(p.getUniqueId().toString(), p.getName());
					sender.sendMessage(ChatColor.GREEN+"=========================================");
					sender.sendMessage(ChatColor.GREEN+"Known Aliases of Player "+p.getName()+":");
					for (String a : otherData.alias)
						sender.sendMessage(ChatColor.RED+a);
					sender.sendMessage(ChatColor.GREEN+"=========================================");
				}
			}
		} else
		{
			//need arg 0..1
			if (args.length<2)
				return false;
			if (args[0].equalsIgnoreCase("add"))
			{
				data.alias.add(args[1]);
				sender.sendMessage(ChatColor.RED+"[ChatH] "+ChatColor.RESET+"successfully add new alias \""+args[1]+"\"");
			} else if (args[0].equalsIgnoreCase("remove"))
			{
				data.alias.remove(args[1]);
				sender.sendMessage(ChatColor.RED+"[ChatH] "+ChatColor.RESET+"successfully removed alias \""+args[1]+"\"");
			}
		}		
		return true;
	}

	@Override
	public String helpMessage() {
		return "Add or Remove your Aliases";
	}

	@Override
	public void printSubmoduleHelp(CommandSender sender) {
		sender.sendMessage("/chath alias list - Lists all known aliases");
		sender.sendMessage("/chath alias list <name> - Lists all known aliases of player <name>");
		sender.sendMessage("/chath alias add <name> - Adds <name> to the list of known aliases");
		sender.sendMessage("/chath alias remove <name> - removes <name> from the list of known aliases");
		
	}


}
