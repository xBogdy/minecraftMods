package draylar.identity.network;

import draylar.identity.Identity;
import draylar.identity.ability.AbilityRegistry;
import draylar.identity.registry.Components;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.registry.Registry;

import java.util.Date;
import java.util.Random;

public class ServerNetworking implements NetworkHandler {

    public static void init() {
        registerIdentityRequestPacketHandler();
        registerFavoritePacketHandler();
        registerUseAbilityPacketHandler();
    }

    private static void registerUseAbilityPacketHandler() {
        ServerPlayNetworking.registerGlobalReceiver(USE_ABILITY, (server, player, handler, buf, responseSender) -> {
            LivingEntity identity = Components.CURRENT_IDENTITY.get(player).getIdentity();

            server.execute(() -> {
                // Verify we should use ability for the player's current identity
                if(identity != null) {
                    EntityType<?> identityType = identity.getType();

                    if(AbilityRegistry.has(identityType)) {

                        // Check cooldown
                        if(Components.ABILITY.get(player).canUseAbility()) {
                            AbilityRegistry.get(identityType).onUse(player, identity, player.world);
                            Components.ABILITY.get(player).setCooldown(AbilityRegistry.get(identityType).getCooldown());
                        }
                    }
                }
            });
        });
    }

    private static void registerIdentityRequestPacketHandler() {
        ServerSidePacketRegistry.INSTANCE.register(IDENTITY_REQUEST, (context, packet) -> {
            EntityType<?> type = Registry.ENTITY_TYPE.get(packet.readIdentifier());
//            for (int j =0;j<10;j++){
//            System.out.println("intra");
//            Random random = new Random();
//            int i = random.nextInt(14);
//            System.out.println("random = "+i);
//            if(i==0) {
//                Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((GhastEntity) type.create(context.getPlayer().world));
//            }else if(i==1){
//                Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((EndermanEntity) type.create(context.getPlayer().world));
//            }else if(i==2){
//                Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((WolfEntity) type.create(context.getPlayer().world));
//            }else if(i==3){
//                Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((CatEntity) type.create(context.getPlayer().world));
//            }else if(i==4){
//                Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((FishEntity) type.create(context.getPlayer().world));
//            }else if(i==5){
//                Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((CreeperEntity) type.create(context.getPlayer().world));
//            }else if(i==6){
//                Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((ZombieEntity) type.create(context.getPlayer().world));
//            }else if(i==7){
//                Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((ZoglinEntity) type.create(context.getPlayer().world));
//            }else if(i==8){
//                Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((PigEntity) type.create(context.getPlayer().world));
//            }else if(i==9){
//                Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((VillagerEntity) type.create(context.getPlayer().world));
//            }else if(i==10){
//                Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((PlayerEntity) type.create(context.getPlayer().world));
//            }else if(i==11){
//                Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((DonkeyEntity) type.create(context.getPlayer().world));
//            }else if(i==12){
//                Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((DrownedEntity) type.create(context.getPlayer().world));
//            }else if(i==13){
//                Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((DolphinEntity) type.create(context.getPlayer().world));
//            }else if(i==14){
//                Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((EnderDragonEntity) type.create(context.getPlayer().world));
//            }
//            }

            context.getTaskQueue().execute(() -> {
                // Ensure player has permission to switch identities
                if (Identity.CONFIG.enableSwaps || context.getPlayer().hasPermissionLevel(3)) {
                    if (type.equals(EntityType.PLAYER)) {
                        Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity(null);
                    } else {
                            //TODO: intoarce cum a fost
//                        Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity(() type.create(context.getPlayer().world));
                        Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((LivingEntity) type.create(context.getPlayer().world));
                    }

                    // Refresh player dimensions
                    context.getPlayer().calculateDimensions();
                }
            });
        });
    }

    private static void registerFavoritePacketHandler() {
        ServerSidePacketRegistry.INSTANCE.register(FAVORITE_UPDATE, (context, packet) -> {
            EntityType<?> type = Registry.ENTITY_TYPE.get(packet.readIdentifier());
            boolean favorite = packet.readBoolean();
            PlayerEntity player = context.getPlayer();

            context.getTaskQueue().execute(() -> {
                if(favorite) {
                    Components.FAVORITE_IDENTITIES.get(player).favorite(type);
                } else {
                    Components.FAVORITE_IDENTITIES.get(player).unfavorite(type);
                }
            });
        });
    }

    private ServerNetworking() {
        // NO-OP
    }
}
