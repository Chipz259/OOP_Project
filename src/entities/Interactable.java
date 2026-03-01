package entities;

public interface Interactable {
    public void onInteract(Player player);
    public void onHover();
    public boolean isInteractable();
}
