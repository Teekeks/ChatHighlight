package de.teawork.chatHighlight.commandParser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.teawork.chatHighlight.ChatHighlight;

public class chCommandParser {
	
	public ChatHighlight plugin;
	public Set<chCommandSubmoduleBase> submodules = new HashSet<chCommandSubmoduleBase>();
	
	public chCommandParser(ChatHighlight ch)
	{
		plugin = ch;
		submodules.add(new chCommandSubmoduleHelp(this, null));
		submodules.add(new chCommandSubmoduleParamModify(this, null));
		submodules.add(new chCommandSubmoduleAlias(this, null));
		submodules.add(new chCommandSubmoduleBuddy(this, null));
		submodules.add(new chCommandSubmoduleReset(this, null));
		submodules.add(new chCommandSubmoduleColors(this, null));
		submodules.add(new chCommandSubmoduleSounds(this, null));
		submodules.add(new chCommandSubmoduleSave(this, null));
		submodules.add(new chCommandSubmoduleCopy(this, null));
		submodules.add(new chCommandSubmoduleMute(this, null));
		submodules.add(new chCommandSubmoduleUnmute(this, null));
	}
	
	public boolean parseCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("chath"))
		{
			if (args.length<1)
			{
				sender.sendMessage("Chat Highlight Version "+plugin.getDescription().getVersion()+" by Teekeks");
				return false;
			}
			//Look for Submodule alias match
			for (chCommandSubmoduleBase sub : submodules)
				if (sub.alias.equalsIgnoreCase(args[0]))
					return sub.parseSubmodule(sender, Arrays.copyOfRange(args, 1, args.length));
		}
		return false;
		
	}
}
