package org.alexdev.icarus.messages.incoming.catalogue;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PurchasePresentMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        /*int pageID = request.readInt();
        int itemID = request.readInt();
        String presentData = request.readString();
        String presentUser = request.readString();
        String presentMessage = request.readString();
        int spriteID = request.readInt();
        int ribbon = request.readInt();
        int colour = request.readInt();
        
        CataloguePage page = CatalogueManager.getPage(pageID);

        if (page.getMinRank() > player.getDetails().getRank()) {
            return;
        }

        CatalogueItem item = page.getItem(itemID);
        CatalogueBundledItem bundleItem = item.getItems().get(0);
        ItemDefinition definition = bundleItem.getItemDefinition();
        
        if (player.getDetails().getCredits() < item.getCostCredits()) {
            player.send(new PresentDeliverErrorMessageComposer(true, false));
            return;
        }
        

        // do duckets: false, true
        
        StringBuilder giftExtraData = new StringBuilder();
        giftExtraData.append(presentUser);
        giftExtraData.append(Character.toString((char)5));
        giftExtraData.append(presentMessage);
        giftExtraData.append(Character.toString((char)5));
        giftExtraData.append(player.getDetails().getID());
        giftExtraData.append(Character.toString((char)5));
        giftExtraData.append(definition.getID());
        giftExtraData.append(Character.toString((char)5));
        giftExtraData.append(definition.getSpriteID());
        giftExtraData.append(Character.toString((char)5));
        giftExtraData.append(ribbon);
        giftExtraData.append(Character.toString((char)5));
        giftExtraData.append(colour);
        
        Item inventoryItem = InventoryDao.newItem(bundleItem.getItemDefinition().getID(), player.getDetails().getID(), giftExtraData.toString());
        
        player.getInventory().addItem(inventoryItem);
        player.send(new PurchaseNotificationMessageComposer(bundleItem));*/
    }
}
