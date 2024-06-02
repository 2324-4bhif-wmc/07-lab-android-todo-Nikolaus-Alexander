package at.htl.todo.model;

public class UiState {
    /** the type of Tab - Bars in our main view */
    public enum Tab {
        home(0),
        create(1),
        card(2);

        public int index() {
            return index;
        }
        private int index;
        Tab(int index) {
            this.index = index;
        }
    }
    /** we define our own enum to have the model independent of the view technology */
    public enum Orientation {
        undefined,
        portrait,
        landscape
    }
    /** the currently selected tab */
    public Tab selectedTab = Tab.home;

    /** the current orientation of the device. */
    public Orientation orientation = Orientation.undefined;
}
