package entities;

public interface Interactable {
    public void onInteract(Player p);
    public void onHover();
    public boolean isInteractable();
}
