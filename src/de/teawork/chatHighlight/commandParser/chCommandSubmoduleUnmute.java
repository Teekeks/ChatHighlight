package de.teawork.chatHighlight.commandParser;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class chCommandSubmoduleUnmute extends chCommandSubmoduleBase {

	public chCommandSubmoduleUnmute(chCommandParser master,
			chCommandSubmoduleBase parent) {
		super("unmute", master, parent);
	}

	@Override
	public boolean parseSubmodule(CommandSender sender, String[] args) {
		if (!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED+"[ChatH] "+ChatColor.RESET+"Command must be performed by a player!");
			return true;
		}
		Player player = (Player)sender;
		master.plugin.playerConfigData.get(player.getUniqueId().toString()).muted=false;
		player.sendMessage(ChatColor.RED+"[ChatH] You are no longer muted!");
		return true;
	}

	@Override
	public String helpMessage() {
		return "Unmutes you after using /chath mute";
	}

	@Override
	public void printSubmoduleHelp(CommandSender sender) {
		sender.sendMessage("Unmutes you after /chath mute");
		sender.sendMessage("Usage: /chath unmute");
		sender.sendMessage("Use \"/chath mute\" to mute sound again.");
	}

}
