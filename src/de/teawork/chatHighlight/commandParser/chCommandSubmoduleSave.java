package de.teawork.chatHighlight.commandParser;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class chCommandSubmoduleSave extends chCommandSubmoduleBase {

	public chCommandSubmoduleSave(chCommandParser master,
			chCommandSubmoduleBase parent) {
		super("save", master, parent);
	}

	@Override
	public boolean parseSubmodule(CommandSender sender, String[] args) {
		if (!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED+"[ChatH] "+ChatColor.RESET+"Command must be performed by a player!");
			return true;
		}
		Player player = (Player)sender;
		master.plugin.saveData(player.getUniqueId().toString(), master.plugin.playerConfigData.get(player.getUniqueId().toString()));
		player.sendMessage(ChatColor.RED+"[ChatH] Config saved!");
		return true;
	}

	@Override
	public String helpMessage() {
		return "Save your settings (usualy not needed)";
	}

	@Override
	public void printSubmoduleHelp(CommandSender sender) {
		sender.sendMessage("Use this to save your settings manual, this is usualy not needed.");
	}

}
