package draylar.identity.screen.widget;

import draylar.identity.Identity;
import draylar.identity.network.ClientNetworking;
import draylar.identity.screen.IdentityScreen;
import draylar.identity.screen.ScreenUtils;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractPressableButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class EntityWidget extends AbstractPressableButtonWidget {

    private final LivingEntity entity;
    private final int size;
    private boolean active;
    private boolean starred;
    private final IdentityScreen parent;

    public EntityWidget(float x, float y, float width, float height, LivingEntity entity, IdentityScreen parent, boolean starred, boolean current) {
        super((int) x, (int) y, (int) width, (int) height, new LiteralText("")); // int x, int y, int width, int height, message
        this.entity = entity;
        size = (int) (25 * (1 / (Math.max(entity.getHeight(), entity.getWidth()))));
        entity.setGlowing(true);
        this.parent = parent;
        this.starred = starred;
        this.active = current;
    }

        static int tick=0;
        static int checker =0;
    public static void poo(){
        tick++;
        if(tick >= 1200){
            System.out.println("tick="+tick);
            tick=0;

        }
    }
    //TODO: functie statica care o pot pune in tick
    public static void pooo(){
//        System.out.println("cristofer trimete stanga");
        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
        if(tick == 1199 && checker % 2 == 0){
            //TODO fa ca nextint sa fie -1 daca alege ceva special
            Random random = new Random();
            int jk = random.nextInt(69);
            if(jk==0) {
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.ARMOR_STAND));
            }else if(jk == 1){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.BAT));
            }else if(jk==2){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.BEE));
            }else if(jk==3){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.BLAZE));
            }else if(jk==4){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.CAT));
            }else if(jk==5){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.CAVE_SPIDER));
            }else if(jk==6){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.CHICKEN));
            }else if(jk==7){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.COD));
            }else if(jk==8){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.COW));
            }else if(jk==9){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.CREEPER));
            }else if(jk==10){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.DOLPHIN));
            }else if(jk == 11){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.DONKEY));
            }else if(jk==12){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.DROWNED));
            }else if(jk==13){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.ELDER_GUARDIAN));
            }else if(jk==14){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.ENDER_DRAGON));
            }else if(jk==15){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.ENDERMAN));
            }else if(jk==16){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.ENDERMITE));
            }else if(jk==17){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.EVOKER));
            }else if(jk==18){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.FOX));
            }else if(jk==19){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.GHAST));
            }else if(jk==20){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.ZOMBIE));
            }else if(jk == 21){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.GUARDIAN));
            }else if(jk==22){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.HOGLIN));
            }else if(jk==23){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.HORSE));
            }else if(jk==24){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.HUSK));
            }else if(jk==25){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.ILLUSIONER));
            }else if(jk==26){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.IRON_GOLEM));
            }else if(jk==27){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.LLAMA));
            }else if(jk==28){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.MAGMA_CUBE));
            }else if(jk==29){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.MULE));
            }else if(jk==30){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.MOOSHROOM));
            }else if(jk == 31){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.OCELOT));
            }else if(jk==32){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.PANDA));
            }else if(jk==33){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.PARROT));
            }else if(jk==34){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.PHANTOM));
            }else if(jk==35){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.PIG));
            }else if(jk==36){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.PIGLIN));
            }else if(jk==37){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.PIGLIN_BRUTE));
            }else if(jk==38){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.PILLAGER));
            }else if(jk==39){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.POLAR_BEAR));
            }else if(jk==40){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.PUFFERFISH));
            }else if(jk == 41){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.RABBIT));
            }else if(jk==42){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.RAVAGER));
            }else if(jk==43){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.SHEEP));
            }else if(jk==44){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.SHULKER));
            }else if(jk==45){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.SILVERFISH));
            }else if(jk==46){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.SKELETON));
            }else if(jk==47){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.SKELETON_HORSE));
            }else if(jk==48){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.SLIME));
            }else if(jk==49){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.SNOW_GOLEM));
            }else if(jk==50){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.SPIDER));
            }else if(jk == 51){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.SQUID));
            }else if(jk==52){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.STRAY));
            }else if(jk==53){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.STRIDER));
            }else if(jk==54){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.TRADER_LLAMA));
            }else if(jk==55){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.TROPICAL_FISH));
            }else if(jk==56){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.TURTLE));
            }else if(jk==57){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.VEX));
            }else if(jk==58){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.VILLAGER));
            }else if(jk==59){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.VINDICATOR));
            }else if(jk==60){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.WANDERING_TRADER));
            }else if(jk == 61){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.WITCH));
            }else if(jk==62){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.WITHER));
            }else if(jk==63){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.WITHER_SKELETON));
            }else if(jk==64){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.WOLF));
            }else if(jk==65){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.ZOGLIN));
            }else if(jk==66){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.ZOMBIE));
            }else if(jk==67){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.ZOMBIE_HORSE));
            }else if(jk==68){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.ZOMBIE_VILLAGER));
            }else if(jk==69){
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.ZOMBIFIED_PIGLIN));
            }
            ClientSidePacketRegistry.INSTANCE.sendToServer(ClientNetworking.IDENTITY_REQUEST, packet);
        }
        }





    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean bl = mouseX >= (double)this.x && mouseX < (double)(this.x + this.width) && mouseY >= (double)this.y && mouseY < (double)(this.y + this.height);

        if(bl) {
            // Update current Identity
            if(button == 0) {
                PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(entity.getType()));
                ClientSidePacketRegistry.INSTANCE.sendToServer(ClientNetworking.IDENTITY_REQUEST, packet);
                parent.disableAll();
                active = true;
            }

            // Add to favorites
            else if (button == 1) {
                boolean favorite = false;

                if(starred) {
                    starred = false;
                } else {
                    starred = true;
                    favorite = true;
                }

                // Update server with information on favorite
                PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
                packet.writeIdentifier(Registry.ENTITY_TYPE.getId(entity.getType()));
                packet.writeBoolean(favorite);
                ClientSidePacketRegistry.INSTANCE.sendToServer(ClientNetworking.FAVORITE_UPDATE, packet);

                // TODO: re-sort screen?
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        ScreenUtils.drawEntity(x + this.getWidth() / 2, (int) (y + this.getHeight() * .75f), size, -10, -10, entity, 15728880);

        // Render selected outline
        if(active) {
            MinecraftClient.getInstance().getTextureManager().bindTexture(Identity.id("textures/gui/selected.png"));
            DrawableHelper.drawTexture(matrices, x, y, getWidth(), getHeight(), 0, 0, 48, 32, 48, 32);
        }

        // Render favorite star
        if(starred) {
            MinecraftClient.getInstance().getTextureManager().bindTexture(Identity.id("textures/gui/star.png"));
            DrawableHelper.drawTexture(matrices, x, y, 0, 0, 15, 15, 15, 15);
        }

        // Draw tooltip
//        float x = MouseUtilities.mouseX;
//        float y = MouseUtilities.mouseY;
//
//        if(getX() <= x && getX() + getWidth() >= x) {
//            if(getY() <= y && getY() + getHeight() >= y) {
//                drawTooltip(matrices, provider);
//                renderToolTip();
//            }
//        }
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {

    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void onPress() {

    }

    @Override
    public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
        Screen currentScreen = MinecraftClient.getInstance().currentScreen;

        if(currentScreen != null) {
            currentScreen.renderTooltip(matrices, Collections.singletonList(new TranslatableText(entity.getType().getTranslationKey())), mouseX, mouseY);
        }
    }
}
