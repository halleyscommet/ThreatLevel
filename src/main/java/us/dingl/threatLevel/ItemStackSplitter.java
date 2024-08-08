package us.dingl.threatLevel;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemStackSplitter {

    public static List<ItemStack> splitItemStack(Material material, int quantity) {
        List<ItemStack> itemStacks = new ArrayList<>();

        int fullStacks = quantity / 64;
        int remainingItems = quantity % 64;

        for (int i = 0; i < fullStacks; i++) {
            itemStacks.add(new ItemStack(material, 64));
        }

        if (remainingItems > 0) {
            itemStacks.add(new ItemStack(material, remainingItems));
        }

        return itemStacks;
    }
}