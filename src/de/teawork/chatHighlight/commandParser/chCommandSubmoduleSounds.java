package de.teawork.chatHighlight.commandParser;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class chCommandSubmoduleSounds extends chCommandSubmoduleBase {

	static final int SOUNDS_PER_PAGE = 10; 
	
	public chCommandSubmoduleSounds(chCommandParser master,
			chCommandSubmoduleBase parent) {
		super("sounds", master, parent);
	}

	@Override
	public boolean parseSubmodule(CommandSender sender, String[] args) {
		if (args.length>=2 && args[0].equals("play") && sender instanceof Player) {
			Sound snd = Sound.valueOf(args[1]);
			Player p = (Player)sender;
			float loudness=1, pitch=1;
			try {
			if (args.length>=3) {
				loudness = Float.valueOf(args[2]).floatValue();
			}
			if (args.length>=4) {
				pitch = Float.valueOf(args[3]).floatValue();
			}
			} catch (Exception e) {
				
			}
			p.playSound(p.getLocation(), snd, loudness, pitch);
			return true;
		}
		sender.sendMessage(ChatColor.GREEN+"=========================================");
		sender.sendMessage(ChatColor.GREEN+"Available Sounds:");
		int i=0;
		int page=0;
		int totalPages = Sound.values().length/SOUNDS_PER_PAGE;
		if (args.length>0)
			try {
				page = Integer.valueOf(args[0]);
				if (page<1) page = 1;
				if (page>totalPages) page = totalPages;
				page--;
			} catch (Exception e) {
			}
		TextComponent msg = new TextComponent("Page "+(page+1)+"/"+totalPages+"\n");
		TextComponent c;
		for (Sound e : Sound.values()) {
			i++;
			if (i<page*SOUNDS_PER_PAGE) continue;
			if (i>page*SOUNDS_PER_PAGE+SOUNDS_PER_PAGE) break;
			c = new TextComponent(e.toString());
			c.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/chath sounds play "+e.toString()));
			TextComponent[] ct = new TextComponent[1];
			ct[0] = new TextComponent("Play Effect "+e.toString());
			c.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, ct));
			msg.addExtra(c);
			msg.addExtra("\n");
			
		}
		
		int iprev = (page==0?totalPages:page);
		int inext = (page==totalPages-1?1:page+2);
		TextComponent tcPrev = new TextComponent("< Previous Page");
		tcPrev.setBold(true);
		tcPrev.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/chath sounds "+iprev));
		tcPrev.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("Display previous page")}));
		TextComponent tcNext = new TextComponent("Next Page >");
		tcNext.setBold(true);
		tcNext.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/chath sounds "+inext));
		tcNext.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("Display next page")}));
		msg.addExtra(tcPrev);
		msg.addExtra("    ");
		msg.addExtra(tcNext);
		
		((Player)sender).spigot().sendMessage(msg);
		return true;
	}

	@Override
	public String helpMessage() {
		return "Lists all available sounds used in param";
	}

	@Override
	public void printSubmoduleHelp(CommandSender sender) {
		sender.sendMessage("Returns a list of all available sounds you can use for customization in param");
		sender.sendMessage("Usage: /chath sounds <page>");
		sender.sendMessage("If you want to preview a sound with your current pitch and volume, use");
		sender.sendMessage("/chath sounds play <sound> [<loudness> [<pitch>]]");
	}

}
