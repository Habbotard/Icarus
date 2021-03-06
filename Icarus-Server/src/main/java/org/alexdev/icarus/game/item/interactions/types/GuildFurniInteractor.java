package org.alexdev.icarus.game.item.interactions.types;

import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraData;
import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.types.StringArrayExtraData;
import org.alexdev.icarus.game.item.extradata.types.StringExtraData;
import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.game.item.interactions.InteractionType;
import org.alexdev.icarus.game.room.user.RoomUser;

import java.util.ArrayList;
import java.util.List;

public class GuildFurniInteractor extends Interaction {

    @Override
    public void useItem(Item item, RoomUser roomUser) {
        if (item.getDefinition().getInteractionType() == InteractionType.GLD_GATE) {
            InteractionType.GATE.getInteractor().useItem(item, roomUser);
        } else {
            InteractionType.DEFAULT.getInteractor().useItem(item, roomUser);
        }
    }

    @Override
    public ExtraData createExtraData(Item item) {

        Group group = GroupManager.getInstance().getGroup(item.getGroupId());

        if (group == null) {
            return new StringExtraData(ExtraDataPerspective.FURNI, item.getExtraData());
        } else {
            List<String> tags = new ArrayList<>();
            tags.add(item.getExtraData());
            tags.add(String.valueOf(group.getId()));
            tags.add(group.getBadge());
            tags.add(GroupManager.getInstance().getColourCode(group.getColourA(), true));
            tags.add(GroupManager.getInstance().getColourCode(group.getColourB(), false));
            return new StringArrayExtraData(ExtraDataPerspective.FURNI, tags);
        }
    }
}
