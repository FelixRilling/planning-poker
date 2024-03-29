package com.cryptshare.planningpoker.api;

import com.cryptshare.planningpoker.data.Card;
import com.cryptshare.planningpoker.data.CardSet;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Model for {@link CardSet}.
 */
public record CardSetJson(@JsonProperty("name") String name, @JsonProperty("cards") List<CardJson> cards) {

	public static CardSetJson convert(CardSet cardSet) {
		return new CardSetJson(cardSet.getName(), cardSet.getCards().stream().sorted(Card.NATURAL_COMPARATOR).map(CardJson::convert).toList());
	}

}
