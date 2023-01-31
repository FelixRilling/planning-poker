import { FC } from "react";
import { ExtensionKey, Room, VoteSummary } from "../api";

export type SubmitComponent = FC<{ room: Room, voteSummary: VoteSummary }>;

export interface Extension {
	readonly key: ExtensionKey;
	readonly label: string;

	readonly SubmitComponent: SubmitComponent;

	loadSuggestion(newTopic: string): Promise<string | null>;
}