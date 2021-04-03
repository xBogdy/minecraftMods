package draylar.identity.screen.widget;

import draylar.identity.Identity;
import draylar.identity.network.ClientNetworking;
import draylar.identity.screen.IdentityScreen;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.AbstractPressableButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.util.registry.Registry;

public class PlayerWidget extends AbstractPressableButtonWidget {

    private final IdentityScreen parent;

    public PlayerWidget(float x, float y, float width, float height, IdentityScreen parent) {
        super((int) x, (int) y, (int) width, (int) height, new LiteralText("")); // int x, int y, int width, int height, message
        this.parent = parent;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
//        float x = MouseUtilities.mouseX;
//        float y = MouseUtilities.mouseY;
//
//        if(getX() <= x && getX() + getWidth() >= x) {
//            if(getY() <= y && getY() + getHeight() >= y) {
//                drawTooltip(matrices, provider);
//            }
//        }
//

        MinecraftClient.getInstance().getTextureManager().bindTexture(Identity.id("textures/gui/player.png"));
        DrawableHelper.drawTexture(matrices, x, y, 16, 16, 0, 0, 8, 8, 8, 8);

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {

    }

    @Override
    public void onPress() {
        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
        packet.writeIdentifier(Registry.ENTITY_TYPE.getId(EntityType.PLAYER));
        ClientSidePacketRegistry.INSTANCE.sendToServer(ClientNetworking.IDENTITY_REQUEST, packet);
        parent.disableAll();
    }
}
