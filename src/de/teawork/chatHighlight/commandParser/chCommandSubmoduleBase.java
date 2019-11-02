package de.teawork.chatHighlight.commandParser;

import org.bukkit.command.CommandSender;

public abstract class chCommandSubmoduleBase {
	
	public String alias;
	public chCommandParser master;
	public chCommandSubmoduleBase parent;
	
	public chCommandSubmoduleBase(String alias, chCommandParser master, chCommandSubmoduleBase parent)
	{
		this.alias=alias;
		this.master=master;
		this.parent=parent;
	};
	
	public abstract boolean parseSubmodule(CommandSender sender, String[] args);
	
	public abstract String helpMessage();
	
	public abstract void printSubmoduleHelp(CommandSender sender);
}
