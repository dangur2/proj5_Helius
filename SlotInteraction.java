
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class SlotInteraction {

    private int hoverX, hoverY, hoverSlot, selectedSlot, prevSlot;
    private boolean displayHover, holdingItem;
    private Item selectedItem, heldItem;
    private Inventory inventory;
    private Chest chest;

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setChest(Chest chest) {
        this.chest = chest;
    }

    public void shiftClick(int mouseX, int mouseY, ArrayList<Rectangle> list, int hoverSlot) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(mouseX, mouseY)) {
                moveStack(i, getItem(hoverSlot));
            }
        }
    }

    public void LeftClick(int mouseX, int mouseY, ArrayList<Rectangle> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(mouseX, mouseY)) {
                selectedSlot = i;
                if (!holdingItem) { //plockar upp en item
                    prevSlot = selectedSlot;
                    if (getItem(selectedSlot) != null) {
                        selectedItem = getItem(selectedSlot);
                    }
                    removeItem(prevSlot);
                    holdingItem = true;
                    break;
                }
                if (holdingItem) {
                    if (getItem(selectedSlot) == null) { //sätter en tom inventoryslot till den item du håller i
                        setItem(selectedSlot, selectedItem);
                        selectedItem = null;
                        holdingItem = false;
                    } else if (getItem(selectedSlot) != null) { //adderar itemstacks, ifall du håller i en item och trycker på samma item i en annan stack
                        if (getItem(selectedSlot).getItemName().equals(selectedItem.getItemName())) {
                            getItem(selectedSlot).increaseQuantity(selectedItem.getQuantity());
                            holdingItem = false;
                            selectedItem = null;
                        } else { //byter items, om du håller i en item och trycker på en annan item
                            heldItem = getItem(selectedSlot);
                            setItem(selectedSlot, selectedItem);
                            selectedItem = heldItem;
                        }
                    }
                }
            }
        }
    }

    public void RightClick(int mouseX, int mouseY, ArrayList<Rectangle> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(mouseX, mouseY)) {
                selectedSlot = i;
                if (!holdingItem && getItem(selectedSlot) != null) {
                    prevSlot = selectedSlot;
                    if (getItem(selectedSlot)!= null) {
                        selectedItem = new Item(getItem(selectedSlot));
                    }
                    selectedItem.setOneQuantity();
                    getItem(prevSlot).decreaseQuantity();
                    if (getItem(prevSlot).getQuantity() < 1) {
                        removeItem(prevSlot);
                    }
                    holdingItem = true;
                } else {
                    if (getItem(selectedSlot) != null && getItem(selectedSlot).getItemName().equals(selectedItem.getItemName())) {
                        getItem(prevSlot).decreaseQuantity();
                        selectedItem.increaseQuantity(1);
                        if (getItem(prevSlot).getQuantity() < 1) {
                            removeItem(prevSlot);
                        }
                    }
                }
            }
        }
    }

    public void Hover(int mouseX, int mouseY, ArrayList<Rectangle> list) {
        hoverX = mouseX;
        hoverY = mouseY;
        displayHover = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(new Point(mouseX, mouseY))) {
                hoverSlot = i;
                displayHover = true;
            }
        }
    }

    private void moveStack(int slot, Item item) {
        if (item == null) {
            return;
        }
        if (this.chest != null) {
            if (slot < 36) {

                chest.getChestInventory().addItem(item);
                inventory.removeItem(slot);
            } else {
                inventory.addItem(item);
                chest.getChestInventory().removeItem(slot - 36);
            }
        } else {
            if (slot >= 9) {
                if (inventory.addItemShift(item, 0, 9)) {
                    inventory.removeItem(slot);
                }

            } else {
                if (inventory.addItemShift(item, 9, 36)) {
                    inventory.removeItem(slot);
                }
            }
        }
    }

    private void setItem(int slot, Item item) {
        if (slot < 36) {
            inventory.setItem(slot, item);
        }
        if (slot >= 36) {
            chest.getChestInventory().setItem(item, slot - 36);
        }
    }

    private Item getItem(int slot) {
        if (slot < 36) {
            return inventory.getItem(slot);
        }
        if (slot >= 36) {
            return chest.getChestInventory().getItem(slot - 36);
        }
        return null;
    }

    private void removeItem(int slot) {
        if (slot < 36) {
            inventory.removeItem(slot);
            return;
        }
        if (slot >= 36) {
            chest.getChestInventory().removeItem(slot - 36);
        }
    }

    public int getHoverX() {
        return hoverX;
    }

    public int getHoverY() {
        return hoverY;
    }

    public int getHoverSlot() {
        return hoverSlot;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public int getPrevSlot() {
        return prevSlot;
    }

    public boolean isDisplayHover() {
        return displayHover;
    }

    public boolean isHoldingItem() {
        return holdingItem;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public Item getHeldItem() {
        return heldItem;
    }

    public void setHoldingItem(boolean holdingItem) {
        this.holdingItem = holdingItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

}
