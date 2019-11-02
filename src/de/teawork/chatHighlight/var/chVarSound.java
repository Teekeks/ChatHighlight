package de.teawork.chatHighlight.var;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import de.teawork.chatHighlight.chPlayerConfigData;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class chVarSound extends chVarBase {

	Sound value;
	
	public chVarSound(chPlayerConfigData data, String name, chVarBase owner, Sound defaultVal)
	{
		super(data, name, owner);
		value = defaultVal;
	}
	
	public chVarSound(chPlayerConfigData data, String name, chVarBase owner) {
		this(data, name, owner, Sound.BLOCK_ANVIL_USE);
	}

	@Override
	public Sound getValue() {
		return value;
	}

	@Override
	public boolean setByString(String input) {
		try
		{
			value=Sound.valueOf(input);
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
		TextComponent tc = new TextComponent((shortName?name:getPath())+": "+value.toString()+" ");
		tc.setBold(false);
		tc.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/chath param "+getPathSpaced()+" "));
		tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(chHelpText.get(getPath())), new TextComponent("\n"),
				new TextComponent("Click to set value")}));
		TextComponent tcPlay = new TextComponent("►");
		tcPlay.setBold(true);
		tcPlay.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/chath sounds play "+value.toString()+" "+((chVarFloat)data.get(owner.getPath()+"/loudness")).getValue()+" "+((chVarFloat)data.get(owner.getPath()+"/pitch")).getValue()));
		tcPlay.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("Click to hear sound")}));
		tc.addExtra(tcPlay);
		return tc;
	}

	@Override
	public void saveToConfig(FileConfiguration config, String base) {
		config.set(base+"."+name, toStringRaw());
		
	}

	@Override
	public void loadFromConfig(FileConfiguration config, String base) {
		if (config.contains(base+"."+name))
			if (!setByString(config.getString(base+"."+name))) {
				
			}
		
	}

	@Override
	public TextComponent toStringFormated() {
		return toStringFormated(false);
	}

}
