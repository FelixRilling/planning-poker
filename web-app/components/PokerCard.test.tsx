import { render, screen } from "@testing-library/react";
import { Card } from "../model.ts";
import { PokerCard } from "./PokerCard.tsx";
import userEvent from "@testing-library/user-event";
import { describe, expect, it } from "vitest";

describe("PokerCard", () => {
  it("shows name", () => {
    const card: Card = { name: "Foo", value: 1, description: "" };

    render(<PokerCard card={card} />);

    expect(screen.getByText("Foo")).toBeInTheDocument();
  });

  it("shows description", async () => {
    const card: Card = { name: "Foo", value: 1, description: "Foo Bar!" };

    render(<PokerCard card={card} />);

    expect(screen.queryByText("Foo Bar!")).not.toBeInTheDocument();

    await userEvent.hover(screen.getByText("Foo"));

    expect(screen.queryByText("Foo Bar!")).toBeVisible();
  });
});
