package de.teawork.chatHighlight.commandParser;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class chCommandSubmoduleHelp extends chCommandSubmoduleBase {

	public chCommandSubmoduleHelp(chCommandParser master,
			chCommandSubmoduleBase parent) {
		super("?", master, parent);
	}

	@Override
	public boolean parseSubmodule(CommandSender sender, String[] args) {
		if (!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED+"[ChatH] "+ChatColor.RESET+"Command must be performed by a player!");
			return true;
		}

		if (args.length==0)
		{
			Player player = (Player)sender;
			sender.sendMessage(ChatColor.GREEN+"=========================================");
			sender.sendMessage(ChatColor.GREEN+"Help of ChatHighlight!");
			this.printSubmoduleHelp(sender);
			sender.sendMessage(ChatColor.GREEN+"Options:");
			for (chCommandSubmoduleBase sub:master.submodules) {
				TextComponent tc = new  TextComponent(sub.alias);
				tc.setColor(ChatColor.RED);
				tc.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/chath "+sub.alias));
				tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("Click to perform command")}));
				tc.addExtra(new TextComponent(": "+ChatColor.RESET+sub.helpMessage()));
				player.spigot().sendMessage(tc);
			}
			sender.sendMessage(ChatColor.GREEN+"=========================================");
		} else
		{
			if (args.length<1)
				return false;
			sender.sendMessage(ChatColor.GREEN+"=========================================");
			sender.sendMessage(ChatColor.GREEN+"Help for "+args[0]+":");
			for (chCommandSubmoduleBase sub : master.submodules)
				if (sub.alias.equalsIgnoreCase(args[0]))
					sub.printSubmoduleHelp(sender);
			sender.sendMessage(ChatColor.GREEN+"=========================================");
		}
		return true;
	}

	@Override
	public String helpMessage() {
		return "Displays this Help.";
	}

	@Override
	public void printSubmoduleHelp(CommandSender sender) {
		sender.sendMessage("/chath ? <option> - Displays detailed usage information about <option>");	
	}

}
