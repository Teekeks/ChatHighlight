package de.teawork.chatHighlight.var;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;

import de.teawork.chatHighlight.chPlayerConfigData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class chVarColor extends chVarBase {
	
	public static Map<ChatColor,String> colorMap;
	public ChatColor color;

	public chVarColor(chPlayerConfigData data, String name, chVarBase owner, ChatColor defaultVal)
	{
		super(data, name, owner);
		color=defaultVal;
	}
	
	public chVarColor(chPlayerConfigData data, String name, chVarBase owner) {
		this(data, name, owner, ChatColor.WHITE);
	}

	@Override
	public ChatColor getValue() {
		return color;
	}

	@Override
	public boolean setByString(String input) {
		for (Entry<ChatColor,String> c: colorMap.entrySet())
			if (input.equalsIgnoreCase(c.getValue()))
			{
				color = c.getKey();
				return true;
			}
		return false;
	}

	@Override
	public String toStringRaw() {
		return color+"";
	}

	@Override
	public TextComponent toStringFormated(boolean shortName) {
		TextComponent tc = new TextComponent((shortName?name:getPath())+": ");
		tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(chHelpText.get(getPath())), new TextComponent("\n"),
				new TextComponent("Click to set value")}));
		tc.setBold(false);
		TextComponent tcValue = new TextComponent(colorMap.get(color)+" ");
		tcValue.setColor(color);
		tc.addExtra(tcValue);
		int nextC, prevC;
		int ac = color.ordinal();
		prevC = (ac==0?ChatColor.values().length-1: ac-1);
		nextC = (ac==ChatColor.values().length-1?0: ac+1);
		tc.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/chath param "+getPathSpaced()+" "));
		TextComponent tcDown = new TextComponent("v");
		tcDown.setBold(true);
		tcDown.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/chath param "+getPathSpaced()+" "+colorMap.get(ChatColor.values()[nextC])));
		tcDown.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("Next color")}));
		tc.addExtra(tcDown);
		TextComponent tcUp = new TextComponent("^");
		tcUp.setBold(true);
		tcUp.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/chath param "+getPathSpaced()+" "+colorMap.get(ChatColor.values()[prevC])));
		tcUp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("Previous color")}));
		tc.addExtra(tcUp);
		return tc;
		//return new String[]{(shortName?name:getPath())+": "+color.toString()+colorMap.get(color)};
	}

	static
	{
		colorMap = new HashMap<ChatColor, String>();
		colorMap.put(ChatColor.AQUA, "aqua");
		colorMap.put(ChatColor.BLACK, "black");
		colorMap.put(ChatColor.BLUE, "blue");
		colorMap.put(ChatColor.BOLD, "bold");
		colorMap.put(ChatColor.DARK_AQUA, "dark_aqua");
		colorMap.put(ChatColor.DARK_BLUE, "dark_blue");
		colorMap.put(ChatColor.DARK_GRAY, "dark_gray");
		colorMap.put(ChatColor.DARK_GREEN, "dark_green");
		colorMap.put(ChatColor.DARK_PURPLE, "dark_purple");
		colorMap.put(ChatColor.DARK_RED, "dark_red");
		colorMap.put(ChatColor.GOLD, "gold");
		colorMap.put(ChatColor.GRAY, "gray");
		colorMap.put(ChatColor.GREEN, "green");
		colorMap.put(ChatColor.ITALIC, "italic");
		colorMap.put(ChatColor.LIGHT_PURPLE, "light_purple");
		colorMap.put(ChatColor.MAGIC, "magic");
		colorMap.put(ChatColor.RED, "red");
		colorMap.put(ChatColor.STRIKETHROUGH, "strikethrough");
		colorMap.put(ChatColor.UNDERLINE, "underline");
		colorMap.put(ChatColor.WHITE, "white");
		colorMap.put(ChatColor.YELLOW, "yellow");
		colorMap.put(ChatColor.RESET, "reset");
	}

	@Override
	public void saveToConfig(FileConfiguration config, String base) {
		config.set(base+"."+name, colorMap.get(color));
	}

	@Override
	public void loadFromConfig(FileConfiguration config, String base) {
		if (config.contains(base+"."+name))
			setByString(config.getString(base+"."+name));
		
	}

	@Override
	public TextComponent toStringFormated() {
		return toStringFormated(false);
	}
	
}
