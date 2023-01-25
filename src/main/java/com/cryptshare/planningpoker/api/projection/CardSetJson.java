package com.cryptshare.planningpoker.api.projection;

import com.cryptshare.planningpoker.data.Card;
import com.cryptshare.planningpoker.data.CardSet;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CardSetJson(@JsonProperty("name") String name, @JsonProperty("cards") List<CardJson> cards,
						  @JsonProperty("relevantFractionDigits") int relevantFractionDigits) {

	public static CardSetJson convert(CardSet cardSet) {
		return new CardSetJson(
				cardSet.getName(),
				cardSet.getCards().stream().sorted(Card.NATURAL_COMPARATOR).map(CardJson::convert).toList(),
				cardSet.getRelevantFractionDigits());
	}

}
