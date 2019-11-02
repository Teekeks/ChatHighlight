package de.teawork.chatHighlight.var;

import java.util.HashMap;

public class chHelpText {
	private static HashMap<String, String> map;

	public static String get(String s) {
		if (map.containsKey(s))
			return map.get(s);
		return "No help available";
	}
	
	static {
		map = new HashMap<String, String>();
		map.put("chat", "This group contains all settings regarding the general chat");
		map.put("chat/enablePlainColor", "If this is true, all chat messages will have the color set by color");
		map.put("chat/color", "Color of all Chatmessages, ony used when enablePlainColor is set to true");
		map.put("chat/highlightOwnMessage", "If this is set to true, your own messages will have to color set by highlightOwnMessageColor");
		map.put("chat/highlightOwnMessageColor", "Color of your own Chatmessages, ony used when highlightOwnMessage is set to true");
		
		map.put("highlight", "This group contains all settings regarding the highlighting of chat messages");
		map.put("highlight/enable", "if this is set to true, highlighting will take place");
		map.put("highlight/enableForBuddy", "If this is set to true, highlighting is applied to messaged by buddys, this ignores the general enable setting");
		map.put("highlight/enableWhenAfk", "If this is set to true, highlighting will be applied even if you are afk");
		map.put("highlight/color", "This is the color, in which highlightet messages will appear");
		
		map.put("sound", "This group contains all settings regarding the notification sound");
		map.put("sound/enable", "If this is set to true, a sound is played when you get notified");
		map.put("sound/enableForBuddy", "If this is set to true, a sound is played when you get notified by a buddy, this setting ignores the general enable setting");
		map.put("sound/sound", "The sound that will be played on notification, list all sounds via /chath sounds");
		map.put("sound/loudness", "The volume of the notification sound");
		map.put("sound/pitch", "The pitch of the notification sound");
		map.put("sound/muteWhenAfk", "If set to true, so sound is played on a notification if you are afk");
		
		map.put("oddEven", "This group contains all settings regarding the Odd-Even Addon");
		map.put("oddEven/enable", "If set to true, this feature will be applied to all chat messages");
		map.put("oddEven/colorOdd", "The color of all odd chat messages");
		map.put("oddEven/colorEven", "The color of all even chat messages");

		map.put("joinNotification", "This group contains all settings regarding the join notification sound");
		map.put("joinNotification/enable", "If this is set to true, a sound is played when someone joins the game");
		map.put("joinNotification/enableForBuddy", "If this is set to true, a sound is played when a buddy joins the game, this setting ignores the general enable setting");
		map.put("joinNotification/sound", "The sound that will be played, list all sounds via /chath sounds");
		map.put("joinNotification/loudness", "The volume of the notification sound");
		map.put("joinNotification/pitch", "The pitch of the notification sound");

		map.put("leaveNotification", "This group contains all settings regarding the leave notification sound");
		map.put("leaveNotification/enable", "If this is set to true, a sound is played when someone leaves the game");
		map.put("leaveNotification/enableForBuddy", "If this is set to true, a sound is played when a buddy leaves the game, this setting ignores the general enable setting");
		map.put("leaveNotification/sound", "The sound that will be played, list all sounds via /chath sounds");
		map.put("leaveNotification/loudness", "The volume of the notification sound");
		map.put("leaveNotification/pitch", "The pitch of the notification sound");
	}
}
