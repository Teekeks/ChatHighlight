package de.teawork.chatHighlight.commandParser;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class chCommandSubmoduleMute extends chCommandSubmoduleBase {

	public chCommandSubmoduleMute(chCommandParser master,
			chCommandSubmoduleBase parent) {
		super("mute", master, parent);
	}

	@Override
	public boolean parseSubmodule(CommandSender sender, String[] args) {
		if (!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED+"[ChatH] "+ChatColor.RESET+"Command must be performed by a player!");
			return true;
		}
		Player player = (Player)sender;
		master.plugin.playerConfigData.get(player.getUniqueId().toString()).muted=true;
		player.sendMessage(ChatColor.RED+"[ChatH] You are now muted, use \"/chath unmute\" to unmute yourself!");
		return true;
	}

	@Override
	public String helpMessage() {
		return "Mute all audio notifications.";
	}

	@Override
	public void printSubmoduleHelp(CommandSender sender) {
		sender.sendMessage("Mutes all audio notifications.");
		sender.sendMessage("Usage: /chath mute");
		sender.sendMessage("Use \"/chath unmute\" to enable sound again.");
	}

}
