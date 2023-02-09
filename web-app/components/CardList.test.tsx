import { fireEvent, render, screen } from "@testing-library/react";
import { vi } from "vitest";
import { createCard, createCardSet } from "../test/dataFactory";
import { CardList } from "./CardList";


describe("CardList", () => {
	it("shows cards", () => {
		const cardSet = createCardSet({cards: [createCard({name: "Card 1"}), createCard({name: "Card 2"})]});

		render(<CardList cardSet={cardSet} activeCard={null} disabled={true}/>);

		expect(screen.getByText("Card 1")).toBeInTheDocument();
		expect(screen.getByText("Card 2")).toBeInTheDocument();
	});


	it("disables buttons", () => {
		const cardSet = createCardSet({cards: [createCard({name: "Card 1"}), createCard({name: "Card 2"})]});

		render(<CardList cardSet={cardSet} activeCard={null} disabled={true}/>);

		expect(screen.getByText("Card 1")).toBeDisabled();
		expect(screen.getByText("Card 2")).toBeDisabled();
	});

	it("enables buttons", () => {
		const cardSet = createCardSet({cards: [createCard({name: "Card 1"}), createCard({name: "Card 2"})]});

		render(<CardList cardSet={cardSet} activeCard={null} disabled={false}/>);

		expect(screen.getByText("Card 1")).not.toBeDisabled();
		expect(screen.getByText("Card 2")).not.toBeDisabled();
	});

	it("sets active card", () => {
		const card1 = createCard({name: "Card 1"});
		const cardSet = createCardSet({cards: [card1, createCard({name: "Card 2"})]});


		render(<CardList cardSet={cardSet} activeCard={card1} disabled={false}/>);

		expect(screen.getByText("Card 1")).toHaveClass("active");
		expect(screen.getByText("Card 2")).not.toHaveClass("active");
	});


	it("invokes click handler", () => {
		const card1 = createCard({name: "Card 1"});
		const cardSet = createCardSet({cards: [card1, createCard({name: "Card 2"})]});

		const handleClick = vi.fn();

		render(<CardList cardSet={cardSet} activeCard={null} disabled={false} onClick={handleClick}/>);

		fireEvent.click(screen.getByText("Card 1"));

		expect(handleClick).toBeCalledWith(card1);
	});
});