

class EventSet {
	private Event[] events = new Event[100];
	private int index = 0;
	private int next = 0;
	public void add(Event e) {
		if (index >= events.length)
			return;
		events[index++] = e;
		}
	public Event getNext() {
		boolean looped = false;
		int start = next;
		do {
			next = (next + 1) % events.length;
			if (start == next) looped = true;
			if ((next == (start + 1) % events.length) && looped)
				return null;
		} while (events[next] == null);
		return events[next];
	}
	public void removeCurrent() {
		events[next] = null;
	}
}
