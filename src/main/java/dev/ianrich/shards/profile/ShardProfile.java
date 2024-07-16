package dev.ianrich.shards.profile;

// Context: I would usually call this profile but decided not to since this plugin is meant to be run with more plugins to resolve the confusion. - Ian

import dev.ianrich.shards.Shards;
import dev.ianrich.shards.redis.packet.UpdatePacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @AllArgsConstructor
public class ShardProfile {

    public ShardProfile(UUID uuid){
        this.uuid = uuid;
        this.shards = ((Shards.instance.getRedisHandler().hermes.ifExists("shards-profile-" + uuid.toString())) ? Integer.valueOf(Shards.instance.getRedisHandler().hermes.getData("shards-profile-" + uuid)) : 0);
        Shards.instance.getProfileManager().getProfiles().add(this);
    }

    UUID uuid;
    Integer shards;

    public void setShards(Integer shards){
        this.shards = shards;
        Shards.instance.getRedisHandler().hermes.sendPacket(new UpdatePacket(uuid, shards));
    }

    public void saveProfile(){
        Shards.instance.getRedisHandler().hermes.setData("shards-profile-" + uuid.toString(), shards.toString());
    }

}