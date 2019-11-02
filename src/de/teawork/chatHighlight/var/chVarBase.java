package de.teawork.chatHighlight.var;

import org.bukkit.configuration.file.FileConfiguration;

import de.teawork.chatHighlight.chPlayerConfigData;
import net.md_5.bungee.api.chat.TextComponent;

public abstract class chVarBase {
	
	public String name;
	public chVarBase owner;
	public chPlayerConfigData data;
	
	public chVarBase(chPlayerConfigData data, String name, chVarBase owner)
	{
		this.name=name;
		this.owner=owner;
		this.data=data;
	}
	
	public abstract Object getValue();
	public abstract boolean setByString(String input);
	public abstract String toStringRaw();
	public abstract TextComponent toStringFormated();
	public TextComponent toStringFormated(boolean shortName, boolean entry) {
		return toStringFormated(shortName);
	};
	public abstract TextComponent toStringFormated(boolean shortName);
	public boolean canBeSet()
	{
		return true;
	}
	
	public String getPath()
	{
		String s=name;
		chVarBase b = this;
		while (b.owner!=null && b.owner.owner!=null)
		{
			s=b.owner.name+"/"+s;
			b = b.owner;
		}
		return s;
	}
	
	public String getPathSpaced() {
		String s=name;
		chVarBase b = this;
		while (b.owner!=null && b.owner.owner!=null)
		{
			s=b.owner.name+" "+s;
			b = b.owner;
		}
		return s;
	}
	
	public abstract void saveToConfig(FileConfiguration config, String base);
	public abstract void loadFromConfig(FileConfiguration config, String base);
}
