package de.teawork.chatHighlight.var;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;

import de.teawork.chatHighlight.chPlayerConfigData;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class chVarGroup extends chVarBase{

	Map<String, chVarBase> varMap;
	
	public chVarGroup(chPlayerConfigData data, String name, chVarBase owner) {
		super(data, name, owner);
		varMap = new HashMap<String, chVarBase>();
	}


	@Override
	public Map<String,chVarBase> getValue() {
		return varMap;
	}

	@Override
	public boolean setByString(String input) {
		return false;
	}

	@Override
	public String toStringRaw() {
		return name;
	}
	
	@Override
	public TextComponent toStringFormated() {
		return toStringFormated(false);
	}
	
	@Override
	public boolean canBeSet()
	{
		return false;
	}


	@Override
	public void saveToConfig(FileConfiguration config, String base) {
		for (Entry<String,chVarBase> e : varMap.entrySet())
			e.getValue().saveToConfig(config, base+"."+name);
	}


	@Override
	public void loadFromConfig(FileConfiguration config, String base) {
		for (Entry<String, chVarBase> e:varMap.entrySet())
			e.getValue().loadFromConfig(config, base+"."+name);
	}

	
	@Override
	public TextComponent toStringFormated(boolean shortName, boolean entry) {
		TextComponent tc = new TextComponent((shortName?name:getPath()));
		tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(chHelpText.get(getPath())), new TextComponent("\n"),
				new TextComponent("Click to show group settings")}));
		if (owner!=null)
			tc.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/chath param "+name));
		tc.setBold(true);
		if (owner==null || entry) {
			
			for (Entry<String,chVarBase> e:varMap.entrySet())
			{
				tc.addExtra("\n");
				tc.addExtra(e.getValue().toStringFormated(shortName));
			}
			if (owner!=null) {
				TextComponent tcBack = new TextComponent("\n\n< Back");
				tcBack.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/chath param"));
				tcBack.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("Click to return to menu")}));
				tc.addExtra(tcBack);
			}
		}
		return tc;
	}

	@Override
	public TextComponent toStringFormated(boolean shortName) {
		return  toStringFormated(shortName, false);
	}
}
