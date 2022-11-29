package com.cryptshare.planningpoker.api.projection;

import com.cryptshare.planningpoker.data.RoomMember;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Comparator;

public record RoomMemberJson(@JsonProperty("username") String name, @JsonProperty("role") int role, @JsonProperty("vote") CardJson vote) {
	public static final Comparator<RoomMember> MEMBER_COMPARATOR = Comparator.comparing(roomMember -> roomMember.getUser().getUsername());

	private static final CardJson HIDDEN_CARD = new CardJson("Voted", null);

	public static RoomMemberJson convertToBasic(RoomMember roomMember) {
		return new RoomMemberJson(roomMember.getUser().getUsername(), roomMember.getRole().ordinal(), null);
	}

	public static RoomMemberJson convertToDetailed(RoomMember roomMember, boolean hideVotes) {
		CardJson vote = null;
		if (roomMember.getVote() != null) {
			if (hideVotes) {
				vote = HIDDEN_CARD;
			} else {
				vote = CardJson.convert(roomMember.getVote().getCard());
			}
		}
		return new RoomMemberJson(roomMember.getUser().getUsername(), roomMember.getRole().ordinal(), vote);
	}

}