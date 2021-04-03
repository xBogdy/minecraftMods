package draylar.identity.mixin;

import draylar.identity.Identity;
import draylar.identity.registry.Components;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FollowTargetGoal.class)
public abstract class FollowTargetGoalMixin extends TrackTargetGoalMixin {

    @Shadow protected LivingEntity targetEntity;

    @Inject(
            method = "start",
            at = @At("HEAD"),
            cancellable = true
    )
    private void ignoreMorphedPlayers(CallbackInfo ci) {
        if (Identity.CONFIG.hostilesIgnoreHostileIdentityPlayer && this.mob instanceof Monster && this.targetEntity instanceof PlayerEntity) {
            PlayerEntity targetPlayer = (PlayerEntity) this.targetEntity;
            LivingEntity identity = Components.CURRENT_IDENTITY.get(targetPlayer).getIdentity();

            if(identity != null) {
                boolean hasHostility = Components.HOSTILITY.get(targetPlayer).hasHostility();

                // only cancel if the player does not have hostility
                if (!hasHostility) {
                    // creepers should ignore cats
                    if (this.mob instanceof CreeperEntity && identity.getType().equals(EntityType.OCELOT)) {
                        this.stop();
                        ci.cancel();
                    }

                    // withers should ignore undead
                    else if (this.mob instanceof WitherEntity && identity.getGroup().equals(EntityGroup.UNDEAD)) {
                        this.stop();
                        ci.cancel();
                    }

                    // hostile mobs (besides wither) should not target players morphed as hostile mobs
                    else if (!(this.mob instanceof WitherEntity) && identity instanceof Monster) {
                        this.stop();
                        ci.cancel();
                    }
                }
            }
        }
    }

    @Override
    protected void identity_shouldContinue(CallbackInfoReturnable<Boolean> cir) {
        // check cancelling for hostiles
        if(Identity.CONFIG.hostilesIgnoreHostileIdentityPlayer && Identity.CONFIG.hostilesForgetNewHostileIdentityPlayer && this.mob instanceof Monster && this.targetEntity instanceof PlayerEntity) {
            PlayerEntity targetPlayer = (PlayerEntity) this.targetEntity;
            LivingEntity identity = Components.CURRENT_IDENTITY.get(targetPlayer).getIdentity();

            if (identity != null) {
                boolean hasHostility = Components.HOSTILITY.get(targetPlayer).hasHostility();

                // only cancel if the player does not have hostility
                if (!hasHostility) {
                    // creepers should ignore cats
                    if (this.mob instanceof CreeperEntity && identity.getType().equals(EntityType.OCELOT)) {
                        cir.setReturnValue(false);
                    }

                    // withers should ignore undead
                    else if (this.mob instanceof WitherEntity && identity.getGroup().equals(EntityGroup.UNDEAD)) {
                        cir.setReturnValue(false);
                    }

                    // hostile mobs (besides wither) should not target players morphed as hostile mobs
                    else if (!(this.mob instanceof WitherEntity) && identity instanceof Monster) {
                        cir.setReturnValue(false);
                    }
                }
            }
        }
    }
}
