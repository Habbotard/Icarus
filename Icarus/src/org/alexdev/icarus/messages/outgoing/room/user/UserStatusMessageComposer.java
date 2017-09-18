package org.alexdev.icarus.messages.outgoing.room.user;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.util.Util;

import com.google.common.collect.Lists;

public class UserStatusMessageComposer extends MessageComposer {

    private List<Entity> users;

    public UserStatusMessageComposer(Entity entity) {
        this(Arrays.asList(new Entity[] { entity }));
    }


    public UserStatusMessageComposer(List<Entity> users) {
        this.users = Lists.newArrayList(users);
    }

    @Override
    public void write() {

        this.response.init(Outgoing.UserStatusMessageComposer);
        this.response.writeInt(this.users.size());

        for (Entity entity : this.users) {

            RoomUser roomUser = entity.getRoomUser();
            this.response.writeInt(roomUser.getVirtualId());
            this.response.writeInt(roomUser.getPosition().getX());
            this.response.writeInt(roomUser.getPosition().getY());
            this.response.writeString(Util.format(roomUser.getPosition().getZ()));
            this.response.writeInt(roomUser.getPosition().getHeadRotation());
            this.response.writeInt(roomUser.getPosition().getBodyRotation());

            String statusString = "/";

            for (Entry<EntityStatus, String> status : roomUser.getStatuses().entrySet()) {

                statusString += status.getKey().getStatusCode();

                if (status.getValue().length() > 0) {
                    statusString += " ";
                    statusString += status.getValue();
                }

                statusString += "/";
            }

            statusString += "/";
            
            this.response.writeString(statusString);
        }
    }
}
