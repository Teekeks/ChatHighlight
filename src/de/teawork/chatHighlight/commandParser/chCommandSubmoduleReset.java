package de.teawork.chatHighlight.commandParser;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.teawork.chatHighlight.chPlayerConfigData;


public class chCommandSubmoduleReset extends chCommandSubmoduleBase {

	public chCommandSubmoduleReset(chCommandParser master,
			chCommandSubmoduleBase parent) {
		super("reset", master, parent);
	}

	@Override
	public boolean parseSubmodule(CommandSender sender, String[] args) {
		if (!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED+"[ChatH] "+ChatColor.RESET+"Command must be performed by a player!");
			return true;
		}
		Player player = (Player)sender;
		chPlayerConfigData data = master.plugin.playerConfigData.get(player.getUniqueId().toString());
		data.polify(player.getName());
		sender.sendMessage(ChatColor.RED+"[ChatH] "+ChatColor.RESET+"All chath-settings reseted to default");
		
		return true;
	}

	@Override
	public String helpMessage() {
		return "Resets all your settings to default";
	}

	@Override
	public void printSubmoduleHelp(CommandSender sender) {
		sender.sendMessage("Use with caution, all your chath-settings will be reset to default, you will loose your aliases!");
		sender.sendMessage("/chath reset - resets your settings to default");
	}

}
