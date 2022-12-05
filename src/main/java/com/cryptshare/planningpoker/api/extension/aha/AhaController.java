package com.cryptshare.planningpoker.api.extension.aha;

import com.cryptshare.planningpoker.api.exception.RoomNotFoundException;
import com.cryptshare.planningpoker.data.Room;
import com.cryptshare.planningpoker.data.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Comparator;
import java.util.List;

@RestController
@Profile("extension:aha")
class AhaController {
	private static final Logger logger = LoggerFactory.getLogger(AhaController.class);

	private final AhaService ahaService;
	private final RoomRepository roomRepository;

	AhaController(AhaService ahaService, RoomRepository roomRepository) {
		this.ahaService = ahaService;
		this.roomRepository = roomRepository;
	}

	// TODO: link to privileged role
	@PostMapping("/api/extensions/aha/score/")
	void putIdeaScore(@RequestParam("room-name") String roomName, @RequestParam("score-fact-name") String scoreFactName,
			@RequestParam("score-value") int value) {
		final Room room = roomRepository.findByName(roomName).orElseThrow(RoomNotFoundException::new);

		final String ideaId = room.getTopic();

		if (!ahaService.getScoreFactNames().contains(scoreFactName)) {
			throw new IllegalScoreFactNameException();
		}

		try {
			ahaService.putIdeaScore(ideaId, scoreFactName, value);
		} catch (HttpClientErrorException e) {
			// Slightly nicer feedback
			if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				throw new IdeaNotFoundException(e);
			}
			throw e;
		}
		logger.info("Setting idea '{}' score '{}' to '{}'.", ideaId, scoreFactName, value);
	}

	@GetMapping(value = "/api/extensions/aha/score-facts", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	List<String> getScoreFactNames() {
		return ahaService.getScoreFactNames().stream().sorted(Comparator.naturalOrder()).toList();
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal score fact name.")
	private static class IllegalScoreFactNameException extends RuntimeException {
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Aha! did not recognize the idea ID.")
	private static class IdeaNotFoundException extends RuntimeException {
		private IdeaNotFoundException(HttpClientErrorException e) {
			super(e);
		}
	}

}