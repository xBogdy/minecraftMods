package draylar.identity.network;

import draylar.identity.Identity;
import draylar.identity.ability.AbilityRegistry;
import draylar.identity.registry.Components;
import draylar.identity.screen.widget.EntityWidget;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
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
    public static int ion = 0;
    private static void registerIdentityRequestPacketHandler() {
        ServerSidePacketRegistry.INSTANCE.register(IDENTITY_REQUEST, (context, packet) -> {
            EntityType<?> type = Registry.ENTITY_TYPE.get(packet.readIdentifier());

            context.getTaskQueue().execute(() -> {
                // Ensure player has permission to switch identities
                if (Identity.CONFIG.enableSwaps || context.getPlayer().hasPermissionLevel(3)) {
                    if (type.equals(EntityType.PLAYER)) {
                        Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity(null);
                    } else {
                            //TODO: intoarce cum a fost
//                        if (ion % 2 == 0){
//                            context.getPlayer().sendMessage(new TranslatableText("Random morphing ON"),false);
//                        }else {
//                            context.getPlayer().sendMessage(new TranslatableText("Random morphing OFF"),false);
//                        }
//                        Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity(() type.create(context.getPlayer().world));
                        Components.CURRENT_IDENTITY.get(context.getPlayer()).setIdentity((LivingEntity) type.create(context.getPlayer().world));
                        context.getPlayer().playSound(SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.PLAYERS,1F,1F);
                        context.getPlayer().addCritParticles(context.getPlayer());
                        context.getPlayer().addEnchantedHitParticles(context.getPlayer());
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
