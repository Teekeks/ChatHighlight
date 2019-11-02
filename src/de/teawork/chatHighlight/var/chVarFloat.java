package de.teawork.chatHighlight.var;

import org.bukkit.configuration.file.FileConfiguration;

import de.teawork.chatHighlight.chPlayerConfigData;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class chVarFloat extends chVarBase {

	Float value;
	
	public chVarFloat(chPlayerConfigData data, String name, chVarBase owner, Float defaultVal)
	{
		super(data, name, owner);
		value=defaultVal;
	}
	
	public chVarFloat(chPlayerConfigData data, String name, chVarBase owner) {
		this(data, name, owner, 1.0f);
	}

	@Override
	public Float getValue() {
		return value;
	}

	@Override
	public boolean setByString(String input) {
		try
		{
			value = Float.valueOf(input);
		} catch (Exception e)
		{
			return false;
		}
		return true;
	}

	@Override
	public String toStringRaw() {
		return value.toString();
	}

	@Override
	public TextComponent toStringFormated(boolean shortName) {
		TextComponent tc = new TextComponent((shortName?name:getPath())+": "+String.format("%.2f ", value));
		tc.setBold(false);
		tc.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/chath param "+getPathSpaced()+" "));
		tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(chHelpText.get(getPath())), new TextComponent("\n"),
				new TextComponent("Click to set value")}));
		TextComponent tcDown = new TextComponent("v");
		tcDown.setBold(true);
		tcDown.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/chath param "+getPathSpaced()+" "+(value.floatValue()-0.1f)));
		tcDown.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("Reduce value by 0.1")}));
		tc.addExtra(tcDown);
		TextComponent tcUp = new TextComponent("^");
		tcUp.setBold(true);
		tcUp.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/chath param "+getPathSpaced()+" "+(value.floatValue()+0.1f)));
		tcUp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("Increase value by 0.1")}));
		tc.addExtra(tcUp);
		return tc;
	}

	@Override
	public void saveToConfig(FileConfiguration config, String base) {
		config.set(base+"."+name, value);
	}

	@Override
	public void loadFromConfig(FileConfiguration config, String base) {
		if (config.contains(base+"."+name))
			value=Float.valueOf((float)config.getDouble(base+"."+name));
		
	}

	@Override
	public TextComponent toStringFormated() {
		return toStringFormated(false);
	}

}
