package me.error.tags.MenuSystem;

import me.error.tags.Utils.BasicUtils;
import me.error.tags.Utils.ItemUtils;
import org.bukkit.Material;

public abstract class PaginatedMenu extends Menu {

    protected int page = 0;
    protected int maxItemsPerPage = BasicUtils.getMaxSlots();
    protected int index = 0;

    public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }

    //Set the border and menu buttons for the menu
    public void addMenuBorder(){

        if(maxItemsPerPage == 27) {
            inventory.setItem(48, ItemUtils.getPreviousPageItem());
            inventory.setItem(49, ItemUtils.getClearTagItem());
            inventory.setItem(50, ItemUtils.getNextPageItem());

            inventory.setItem(17, super.FILLER_GLASS);
            inventory.setItem(18, super.FILLER_GLASS);
            inventory.setItem(26, super.FILLER_GLASS);
            inventory.setItem(27, super.FILLER_GLASS);
            inventory.setItem(35, super.FILLER_GLASS);
            inventory.setItem(36, super.FILLER_GLASS);

            for (int i = 44; i < 54; i++) {
                if (inventory.getItem(i) == null) inventory.setItem(i, super.FILLER_GLASS);
            }
            for (int i = 0; i < 10; i++) {
                if (inventory.getItem(i) == null) inventory.setItem(i, super.FILLER_GLASS);
            }
        } else if(maxItemsPerPage == 20) {
            inventory.setItem(39, ItemUtils.getPreviousPageItem());
            inventory.setItem(40, ItemUtils.getClearTagItem());
            inventory.setItem(41, ItemUtils.getNextPageItem());

            inventory.setItem(17, super.FILLER_GLASS);
            inventory.setItem(18, super.FILLER_GLASS);
            inventory.setItem(26, super.FILLER_GLASS);
            inventory.setItem(27, super.FILLER_GLASS);

            for (int i = 35; i < 45; i++) {
                if (inventory.getItem(i) == null) inventory.setItem(i, super.FILLER_GLASS);
            }
            for (int i = 0; i < 10; i++) {
                if (inventory.getItem(i) == null) inventory.setItem(i, super.FILLER_GLASS);
            }
        } else if(maxItemsPerPage == 13) {
            inventory.setItem(30, ItemUtils.getPreviousPageItem());
            inventory.setItem(31, ItemUtils.getClearTagItem());
            inventory.setItem(32, ItemUtils.getNextPageItem());

            inventory.setItem(17, super.FILLER_GLASS);
            inventory.setItem(18, super.FILLER_GLASS);

            for (int i = 26; i < 36; i++) {
                if (inventory.getItem(i) == null) inventory.setItem(i, super.FILLER_GLASS);
            }
            for (int i = 0; i < 10; i++) {
                if (inventory.getItem(i) == null) inventory.setItem(i, super.FILLER_GLASS);
            }
        } else if(maxItemsPerPage == 7) {
            inventory.setItem(21, ItemUtils.getPreviousPageItem());
            inventory.setItem(22, ItemUtils.getClearTagItem());
            inventory.setItem(23, ItemUtils.getNextPageItem());

            for (int i = 17; i < 27; i++) {
                if (inventory.getItem(i) == null) inventory.setItem(i, super.FILLER_GLASS);
            }
            for (int i = 0; i < 10; i++) {
                if (inventory.getItem(i) == null) inventory.setItem(i, super.FILLER_GLASS);
            }
        }
    }
}
