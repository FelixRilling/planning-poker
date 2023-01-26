package com.cryptshare.planningpoker.api.projection;

import com.cryptshare.planningpoker.data.Room;
import com.cryptshare.planningpoker.data.RoomMember;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public record RoomJson(@JsonProperty("name") String name, @JsonProperty("topic") String topic, @JsonProperty("cardSetName") String cardSetName,
					   @JsonProperty("members") List<RoomMemberJson> members, @JsonProperty("votingClosed") boolean votingClosed,
					   @JsonProperty("extensions") List<String> extensions) {

	public static RoomJson convertToBasic(Room room) {
		return convert(room, RoomMemberJson::convertToBasic);
	}

	public static RoomJson convertToDetailed(Room room, Predicate<RoomMember> showVotes) {
		return convert(room, roomMember -> RoomMemberJson.convertToDetailed(roomMember, showVotes.test(roomMember)));
	}

	private static RoomJson convert(Room room, Function<RoomMember, RoomMemberJson> roomMemberMapper) {
		return new RoomJson(
				room.getName(),
				room.getTopic(),
				room.getCardSet().getName(),
				room.getMembers().stream().sorted(RoomMember.ALPHABETIC_COMPARATOR).map(roomMemberMapper).toList(),
				room.getVotingState() == Room.VotingState.CLOSED,
				room.getEnabledExtensionConfigs()
						.stream()
						.map(roomExtensionConfig -> roomExtensionConfig.getExtension().getKey())
						.sorted()
						.toList());
	}
}
