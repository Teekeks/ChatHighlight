package de.teawork.chatHighlight.commandParser;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.teawork.chatHighlight.chPlayerConfigData;
import de.teawork.chatHighlight.var.chVarBase;
import de.teawork.chatHighlight.var.chVarGroup;
import net.md_5.bungee.api.chat.TextComponent;

public class chCommandSubmoduleParamModify extends chCommandSubmoduleBase {

	public chCommandSubmoduleParamModify(chCommandParser master,
			chCommandSubmoduleBase parent) {
		super("param", master, parent);
		
	}

	@Override
	public boolean parseSubmodule(CommandSender sender, String[] args) {
		//get Param
		if (!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED+"[ChatH] "+ChatColor.RESET+"Command must be performed by a player!");
			return true;
		}
		
		Player player = (Player)sender;
		chPlayerConfigData data = master.plugin.playerConfigData.get(player.getUniqueId().toString());
		
		if (args.length<1 || (data.get(args[0]) instanceof chVarGroup && args.length==1))
		{
			sender.sendMessage(ChatColor.GREEN+"=========================================");
			sender.sendMessage(ChatColor.GREEN+"Options for Chath:");
			TextComponent tc = data.get(args.length<1?"/":args[0]).toStringFormated(true, true);
			player.spigot().sendMessage(tc);
		} else
		{
			chVarBase var = data.get(Arrays.copyOfRange(args, 0, args.length-1));
			if (var==null)
				sender.sendMessage(ChatColor.RED+"Option \""+String.join("/", Arrays.asList(Arrays.copyOfRange(args, 0, args.length-1)))+"\" does not exist!");
			else
			{
				boolean done =var.setByString(args[args.length-1]);
				if (done)
					player.spigot().sendMessage(var.toStringFormated());
				else
					sender.sendMessage(ChatColor.RED+"'"+args[args.length-1]+"' is a invalid value!");
				parseSubmodule(sender, new String[]{args[0]});
			}
		}

		return true;
	}

	@Override
	public String helpMessage() {
		return "Allowes to display and modify your settings";
	}

	@Override
	public void printSubmoduleHelp(CommandSender sender) {
		sender.sendMessage("/chath param - Lists all settings");
		sender.sendMessage("/chath param <param> <value> - Sets <param> to <value>");
		sender.sendMessage("Example: if you want to set the highlight color to blue, do:");
		sender.sendMessage("/chath param highlight color blue");
	}

}
