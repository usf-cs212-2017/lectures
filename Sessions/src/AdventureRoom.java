/**
 * Demonstrates how to use session tracking and enum types to create a simple
 * adventure game.
 *
 * @see AdventureServer
 * @see AdventureServlet
 * @see AdventureRoom
 * @see Direction
 */
public enum AdventureRoom {

	/*
	 *     +-----------+-----------+-----------+
	 *     | START (0) | CHAIR (1) | GROWL (2) |
	 *     +-----------+-----------+-----------+
	 *     | BLACK (3) | CHILL (4) | TIGER (5) |
	 *     +-----------+-----------+-----------+
	 *     | YUMMY (6) | SMELL (7) | NOISE (8) |
	 *     +-----------+-----------+-----------+
	 */

	START_ROOM("You are starving, and know food is in here somewhere.", false),
	CHAIR_ROOM("You find a comfortable chair.", false),
	GROWL_ROOM("You hear a faint growling noise.", false),
	BLACK_ROOM("Oh no! You have fallen through a black hole!", true),
	CHILL_ROOM("You feel a slight chill.", false),
	TIGER_ROOM("Oh no! You have been eaten by a tiger.", true),
	YUMMY_ROOM("Congratulations! You found the meal, and it looks delicious!", true),
	SMELL_ROOM("You smell something pleasant.", false),
	NOISE_ROOM("You hear a faint noise, almost like purring.", false);

	private String clue;
	private boolean done;

	private AdventureRoom(String clue, boolean done) {
		this.clue = clue;
		this.done = done;
	}

	@Override
	public String toString() {
		return clue;
	}

	public boolean done() {
		return done;
	}

	/**
	 * Tests whether we can move in the specified direction from the current
	 * room, based on the room's ordinal value.
	 *
	 * @param direction
	 * @return
	 */
	public boolean canMove(Direction direction) {
		boolean move = false;

		switch (direction) {
			case WEST:
				move = this.ordinal() % 3 != 0;
				break;
			case EAST:
				move = this.ordinal() % 3 != 2;
				break;
			case NORTH:
				move = this.ordinal() > 2;
				break;
			case SOUTH:
				move = this.ordinal() < 6;
		}

		return move;
	}

	/**
	 * Will move to the room in the specified direction if possible.
	 *
	 * @param direction
	 * @return
	 */
	public AdventureRoom moveRoom(Direction direction) {
		AdventureRoom room = this;

		if (this.canMove(direction)) {
			switch (direction) {
				case WEST:
					room = AdventureRoom.values()[this.ordinal() - 1];
					break;
				case EAST:
					room = AdventureRoom.values()[this.ordinal() + 1];
					break;
				case NORTH:
					room = AdventureRoom.values()[this.ordinal() - 3];
					break;
				case SOUTH:
					room = AdventureRoom.values()[this.ordinal() + 3];
			}
		}

		return room;
	}
}
