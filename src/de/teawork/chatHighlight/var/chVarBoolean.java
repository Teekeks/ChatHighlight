package de.teawork.chatHighlight.var;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import de.teawork.chatHighlight.chPlayerConfigData;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class chVarBoolean extends chVarBase {

	public Boolean value;
	
	public chVarBoolean(chPlayerConfigData data, String name, chVarBase owner, Boolean defaultVal)
	{
		super(data, name, owner);
		value=defaultVal;
	}
	
	public chVarBoolean(chPlayerConfigData data, String name, chVarBase owner) {
		this(data, name, owner, Boolean.TRUE);
	}

	@Override
	public Boolean getValue() {
		return value;
	}

	@Override
	public boolean setByString(String input) {
		value=Boolean.valueOf(input.toLowerCase().equals("true"));
		return true;
	}

	@Override
	public String toStringRaw() {
		return value.booleanValue()?"true":"false";
	}

	@Override
	public TextComponent toStringFormated() {
		return toStringFormated(false);
	}

	@Override
	public TextComponent toStringFormated(boolean shortName) {
		TextComponent tc = new TextComponent((shortName?name:getPath())+": "+(value.booleanValue()?ChatColor.GREEN+"true":ChatColor.RED+"false"));
		tc.setBold(false);
		tc.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/chath param "+getPathSpaced()+" "+(value.booleanValue()?"false":"true")));
		tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(chHelpText.get(getPath())), new TextComponent("\n"),
				new TextComponent("Click to toggle value")}));
		return tc;
	}

	
	@Override
	public void saveToConfig(FileConfiguration config, String base) {
		config.set(base+"."+name, value);	
	}

	@Override
	public void loadFromConfig(FileConfiguration config, String base) {
		if (config.contains(base+"."+name))
			value = config.getBoolean(base+"."+name);
	}

}
